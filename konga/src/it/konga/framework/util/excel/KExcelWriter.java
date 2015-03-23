package it.konga.framework.util.excel;

import it.konga.framework.interfaces.KFileWriter;
import it.konga.framework.kObjects.excel.DTO_ExcelSheet;
import it.konga.framework.kObjects.excel.DTO_InformazioniColonna;
import it.konga.framework.kObjects.excel.DTO_TabellaExcel;

import java.io.OutputStream;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

/**
 * Questo oggetto permette di scrivere su un outputStream un file excel composto da più sheet, in ognuno dei quali sono presenti intestazioni e tabelle in numero variabile<br>
 * Usare uno dei metodi appendSheetData per inserire le informazioni su ogni sheet che si vuole creare <br>
 * Ereditare da questa se si vogliono cambiare gli stili o la formattazione del foglio<br>
 * 
 * Scrive file *.xlsx
 *  
 * @author Giampaolo Saporito
 * @Date - 23.03.2015
 */
public class KExcelWriter implements KFileWriter
{
	/** limitazione a priori del numero massimo di righe (poi cominciano problemi di performance) */
	protected static final int MAX_BUFFER_SIZE = 65001;
	
	private final static int fixedColumnWidth = 20;
	protected SXSSFWorkbook _workBook;
	protected CellStyle  _styleIntestazioni;
	protected CellStyle _styleTesto;
	
	/** align center */
	protected CellStyle _styleDatiCodice = null;
	/** align left */
	protected CellStyle _styleDatiDescrizione = null;
	/** align right */
	protected CellStyle _styleDatiImporto = null;
	
	/** una lista che contiene le liste di strutture excel, ognuna delle quali rappresenta una tabella. Ogni sheet può contenere più tabelle*/
	protected ArrayList< DTO_ExcelSheet > _sheets;
	protected int _bufferSize;
	private int minimumColumnWidth;

	/**
	 * costruisce l'oggetto capace di scrivere un file excel con un certo numero di sheets<br>
	 * Usare uno dei metodi appendSheetData per inserire le informazioni su ogni sheet che si vuole creare
	 */
	public KExcelWriter()
	{
		_sheets = new ArrayList<DTO_ExcelSheet>(1);
		minimumColumnWidth = fixedColumnWidth;
	}
	
	// **************************************************************************** METODI PUBLICI **************************************************************************** \\
	
	/** di default è 20 caratteri, usare questo metodo per modificare la minima larghezza da assegnare ad una colonna */
	public void setMinimumColumnWidth(int numChars)
	{
		if(numChars> 0)
			minimumColumnWidth = numChars;
	}
	
	/** scrivi il file sullo stream passato in input */
	@Override
	public void writeFile(OutputStream out) throws Exception
	{
		if( _sheets == null)
			throw new Exception("ExcelWriter::writeFile() - non esistono sheets!" );
		if( _sheets.size() <= 0)
			throw new Exception("ExcelWriter::writeFile() - invalid number of sheets = " + _sheets.size());

		creaWorkBook();
		_workBook.write(out);
		_workBook = null;

	}
	
	/**
	 * Aggiungi informazioni sul prossimo sheet
	 * @param tabellaValori tabella dei valori da scrivere sul file
	 */
	public void appendSheetData(String[][] tabellaValori)
	{
		appendSheetData(tabellaValori,null, null,null,null);
	}
	/**
	 * Aggiungi informazioni sul prossimo sheet
	 * @param tabellaValori tabella dei valori da scrivere sul file
	 * @param informazioniColonne nomi da assegnare alle colonne, utilizzando lo stile definito per le intestazioni
	 */
	public void appendSheetData(String[][] tabellaValori, ArrayList<DTO_InformazioniColonna> informazioniColonne)
	{
		this.appendSheetData(tabellaValori,"sheet", null,informazioniColonne,null);
	}
	
	/**
	 * aggiungi informazioni sul prossimo sheet
	 * @param tabellaValori tabella dei valori da scrivere sul file
	 * @param nomeSheet nome da assegnare al tab riguardante questo sheet
	 * @param intestazione Ogni stringa dell'array apparirà in righe successive, in alto a sinistra nel foglio, prima della tabella
	 * @param infoColonne informazioni da assegnare alle colonne
	 */
	public void appendSheetData(String[][] tabellaValori, String nomeSheet, ArrayList<String> intestazione, ArrayList<DTO_InformazioniColonna> infoColonne)
	{
		appendSheetData(tabellaValori, nomeSheet, intestazione, infoColonne, null);
	}
	
	/**
	 * aggiungi informazioni sul prossimo sheet
	 * @param tabellaValori tabella dei valori da scriveere sul file
	 * @param nomeSheet nome da assegnare al tab riguardante questo sheet
	 * @param intestazione ogni stringa dell'arrayu apparirà in righe successive, in alto  a sinistra nel foglio, prima della tabella
	 * @param infoColonne informazioni da assegnare alle colonne
	 * @param nomiRighe nomi da assegnare alle righe, (colonna di sinistra) , utilizzando lo stile definito per le intestazioni
	 */
	public void appendSheetData(String[][] tabellaValori, String nomeSheet, ArrayList<String> intestazione, ArrayList<DTO_InformazioniColonna> infoColonne, ArrayList<String> nomiRighe)
	{
		DTO_TabellaExcel tab = new DTO_TabellaExcel();
		tab.setDati(tabellaValori);
		tab.setStringheIntestazione(intestazione);
		tab.setInformazioniColonne(infoColonne);
		tab.setNomiRighe(nomiRighe);
		
		this.appendSheetData(nomeSheet, tab );
	}
	
	/**
	 * aggiungi informazioni sul prossiumo sheet
	 * @param nomeSheet nome da assegnare al tab riguardante questo sheet
	 * @param strutturaExcel struttura che rappresenta il foglio corrente. Contiene i dati, le informazioni riguardanti le colonne e l'intestazione
	 */
	public void appendSheetData(String nomeSheet, IStrutturaExcel strutturaExcel)
	{
		ArrayList<IStrutturaExcel> list = new ArrayList<IStrutturaExcel>(1);
		list.add(strutturaExcel);
		appendSheetData(nomeSheet, list );	
	}	
	
	/**
	 * aggiungi informazioni sul prossimo sheet<br>
	 * Con questo metodo potrai aggiungere più tabelle in un solo sheet
	 * @param nomeSheet nome da assegnare al tab riguardante questo sheet
	 * @param tabelle informazioni riguardanti tutte le tabelle che si vogliono inserire. Ogni elemento contiene i dati, le informazioni riguardanti le colonne e l'intestazione
	 */
	public void appendSheetData(String nomeSheet, ArrayList<IStrutturaExcel> tabelle)
	{
		//appendSheetData(strutturaExcel.getDati(), nomeSheet,strutturaExcel.getStringheIntestazione(), strutturaExcel.getInformazioniColonne(), strutturaExcel.getNomiRighe() );
		if(tabelle == null || tabelle.size() == 0)
		{
			System.out.println("ExcelWriter::appendSheetData() - non sono presenti dati riguardanti le tabelle da inserire");
			return;
		}
		
		DTO_ExcelSheet sheet = new DTO_ExcelSheet();
		if(nomeSheet!= null && nomeSheet.trim().length() > 0)
			sheet.setNomeSheet(nomeSheet);
		DTO_TabellaExcel tabella = null;
		
		for(IStrutturaExcel tab : tabelle)
		{
			if(tab == null)
				continue;
			tabella = new DTO_TabellaExcel( tab );
			
			if(tabella.getDati() == null || tabella.getDati().length == 0)
			{
				System.out.println("ExcelWriter::appendSheetData() - tabella valori null, verrà ignorata");
				tabella.setDati( new String[0][0] );
			}
			
			sheet.add(tabella);
			int localBuffer = tabella.getDati().length +3;
			if( tab.getStringheIntestazione() != null )
				localBuffer += tab.getStringheIntestazione().size()+2;
			localBuffer += 5; //di sicurezza
			sheet.updateBufferSize( localBuffer ); //2 sono la prima riga vuota e la riga di intestazione
			
			if(sheet.getBufferSize() > _bufferSize )
				_bufferSize = sheet.getBufferSize();
		}
		
		_sheets.add(sheet);
	}
	
	
	// **************************************************************************** SETTERS **************************************************************************** \\
	
	/** fai un set delle informazioni da assegnare alle colonne */
	public void setInformazioniColonne(int sheetIndex ,ArrayList<DTO_InformazioniColonna> infoColonne)
	{
		this.setInformazioniColonne(sheetIndex, 0, infoColonne);
		return;
	}
	/** fai un set delle informazioni da assegnare alle colonne di una tabella*/
	public void setInformazioniColonne(int sheetIndex, int tableIndex ,ArrayList<DTO_InformazioniColonna> infoColonne)
	{
		if(sheetIndex<0 || sheetIndex>= _sheets.size() )
			throw new IndexOutOfBoundsException("MultiTable_ExcelWriter::setNomiColonne() - indice non valido. indx == "+sheetIndex+" , numero sheets = "+_sheets.size());

		_sheets.get(sheetIndex).get(tableIndex).setInformazioniColonne( infoColonne );
	}
	
	/** fai un set dei nomi da assegnare alle righe, (colonna di sinistra) , utilizzando lo stile definito per le intestazioni */
	public void setNomiRighe(int sheetIndex, ArrayList<String> nomiRighe)
	{
		this.setNomiRighe(sheetIndex, 0, nomiRighe);
		return;
	}	
	
	public void setNomiRighe(int sheetIndex, int tableIndex, ArrayList<String> nomiRighe)
	{
		if(sheetIndex<0 || sheetIndex>= _sheets.size())
			throw new IndexOutOfBoundsException("ExcelWriter::addNomiRighe() - indice non valido. indx == "+sheetIndex+" , numero sheets = "+_sheets.size());
		
		_sheets.get(sheetIndex).get(tableIndex).setNomiRighe(nomiRighe);
	}
	
	/** fai un set dell'intestazione. Ogni stringa dell'array apparirà in righe successive, in alto a sinistra nel foglio, prima della tabella */
	public void setIntestazione(int sheetIndex,  ArrayList<String> intestazione)
	{
		this.setIntestazione(sheetIndex, 0, intestazione);
	}
	/** fai un set dell'intestazione. Ogni stringa dell'array apparirà in righe successive, in alto a sinistra nel foglio, prima della tabella */
	public void setIntestazione(int sheetIndex, int tableIndex, ArrayList<String> intestazione)
	{
		if(sheetIndex<0 || sheetIndex>= _sheets.size())
			throw new IndexOutOfBoundsException("ExcelWriter::addNomiRighe() - indice non valido. indx == "+sheetIndex+" , numero sheets = "+_sheets.size());
		
		_sheets.get(sheetIndex).get(tableIndex).setStringheIntestazione(intestazione);
	}
	
	/** fai un set del nome da dare al tab corrispondente a questo sheet */
	public void setSheetName(int sheetIndex, String nomeSheet)
	{
		if(sheetIndex<0 || sheetIndex>= _sheets.size())
			throw new IndexOutOfBoundsException("ExcelWriter::addNomiRighe() - indice non valido. indx == "+sheetIndex+" , numero sheets = "+_sheets.size());

		if(nomeSheet != null)
			_sheets.get(sheetIndex).setNomeSheet(nomeSheet);
	}
	
	// **************************************************************************** METODI PROTECTED **************************************************************************** \\

	protected SXSSFWorkbook creaWorkBook()
	{
		_workBook = new SXSSFWorkbook(_bufferSize);
		initStili();
		
		for(int i=0; i < _sheets.size(); i++)
		{
			Sheet sheet = _workBook.createSheet();
			_workBook.setSheetName(i, _sheets.get(i).getNomeSheet() );
			createSheet(i, sheet, _sheets.get(i) );
		}
		
		return _workBook;
	}
	
	/** ereditare questo metodo se si vuole cambiare il modo in cui i dati vengono scritti sul file */
	protected void createSheet(int sheetIndex, Sheet sheet, DTO_ExcelSheet dtoSheet)
	{
		int startRiga = 0;
		for(int t = 0; t < dtoSheet.getTablesNumber(); t++ )
		{
			startRiga += printTable(sheetIndex, sheet, startRiga, dtoSheet.get(t) );
			//++startRiga;
		}
		autosizeColonne( sheet, dtoSheet);
	}

	protected int printTable(int sheetIndex, Sheet sheet, int startRiga, DTO_TabellaExcel tabella )
	{
		String[][] body = tabella.getDati();
		ArrayList<String> nomi_righe = tabella.getNomiRighe();
		ArrayList<DTO_InformazioniColonna> info_colonne = tabella.getInformazioniColonne();
		ArrayList<String> intestazione = tabella.getStringheIntestazione();
		
		int numRighe = body.length +3 ;
		if(intestazione != null)
			numRighe += intestazione.size() +2;
		creaRighe(sheet, numRighe );
		int startColonna = 1;
		boolean flagIntestazioniRighe = false;

		if(intestazione != null && intestazione.size() > 0)
		{
			scriviIntestazione(sheetIndex, startRiga, sheet, intestazione);
			startRiga += intestazione.size() +2;
		}
		if(nomi_righe != null && nomi_righe.size() > 0)
		{
			flagIntestazioniRighe = true;
			startColonna = 2;
			scriviColonnaSinistra(sheet, startRiga, nomi_righe);
		}
		if(info_colonne != null && info_colonne.size() > 0)
		{
			scriviIntestazioneColonne(sheet, startColonna,startRiga, info_colonne);
			++startRiga;
		}
		
		if( !_sheets.get(sheetIndex).isFlagIntestazioniRighe() && flagIntestazioniRighe == true )
			_sheets.get(sheetIndex).setFlagIntestazioniRighe(flagIntestazioniRighe);
		
		int numeroColonne = 0;
		//metto i valori
		for(int r=0; r < body.length ; r++)
		{
			for(int c = 0 ; c < body[r].length ; c++)
			{
				Cell cell = sheet.getRow(r+startRiga).createCell(c + startColonna);
				switch (info_colonne.get(c).getTipoDato() )
				{
				case DESCRIZIONE:
					cell.setCellStyle(_styleDatiDescrizione);
					cell.setCellType(Cell.CELL_TYPE_STRING);
					cell.setCellValue( (body[r][c]));
					break;
				case IMPORTO:
					cell.setCellStyle(_styleDatiImporto);
					cell.setCellType(Cell.CELL_TYPE_NUMERIC);
					// Gestisco la parsificazione dei dati numerici.
					try{
						cell.setCellValue(Double.parseDouble(body[r][c]));
					}catch(Exception ex){
						// Ho gestito l'eventuale eccezione cosi' da non creare problemi per la generazione del file excel
						cell.setCellValue(body[r][c]);
					}
					break;
				case CODICE:
					cell.setCellValue( (body[r][c]));
				default:
					cell.setCellStyle(_styleDatiCodice);
					cell.setCellValue( (body[r][c]));
					break;
				}//EO switch
				if(c+startColonna > numeroColonne)
					numeroColonne = c+startColonna;
			}
		}

		if(info_colonne != null && numeroColonne < info_colonne.size() +1 )
			numeroColonne = info_colonne.size() +1;
		
		_sheets.get(sheetIndex).setNumColonne_checkMax(numeroColonne);
		
		return numRighe;
	}//EO printTable

	protected void autosizeColonne(Sheet sheet, DTO_ExcelSheet dtoSheet )
	{
		//  sheet.autoSizeColumn(c); //pourtroppo a causa di un bug (noto) della libreria , lascia le colonne a dimensione ridotta

		boolean flagIntestazioniRighe = dtoSheet.isFlagIntestazioniRighe();
		int maxLung = dtoSheet.getMaxLunghezzaIntestazione();

		for(int c=0; c <= dtoSheet.getNumColonne(); c++)
		{
			int width = 256;
			if(c==0 )
				width = 256;
			else if(c==1)
			{
				width = maxLung<minimumColumnWidth ? minimumColumnWidth : maxLung;
				for(int t=  0; t < dtoSheet.getTablesNumber() && flagIntestazioniRighe; t++)
				{
					try{
						width = width < ( dtoSheet.get(t).getNomiRighe().get(0).length() +2 ) ? ( dtoSheet.get(t).getNomiRighe().get(0).length() +2 ) : width;
					} catch (Exception e){
						// niente
					}
				}
				width = width*256;
			}			
			else
			{		
				width = minimumColumnWidth;
				//confronto con i titoli delle colonne (se esistono)
				for(int t=  0; t < dtoSheet.getTablesNumber(); t++)
				{
					try{
						width = width < (dtoSheet.get(t).getDati()[0][c].length() +2 ) ? (dtoSheet.get(t).getDati()[0][c].length() +2 ) : width;
						ArrayList<DTO_InformazioniColonna> info_colonne = dtoSheet.get(t).getInformazioniColonne();
						width = width < (info_colonne.get(c-1).getNomeColonna().length() +2) ? (info_colonne.get(c-1).getNomeColonna().length() + 2) : width;
					} catch (Exception e){
						// niente
					}				
				}	
				width = width*256;
			}
			if(width > 256*250)			//max numero caratteri == 250
				width = 256*250;
			sheet.setColumnWidth(c, width);
		}
	}
	
	protected void scriviColonnaSinistra(Sheet sheet, int startRiga, ArrayList<String> nomiRighe)
	{
		if(nomiRighe == null || nomiRighe.size() == 0)
			return;
		for(int r=0; r < nomiRighe.size(); r++)
		{
			Cell cell = sheet.getRow(r+startRiga+1).createCell(1);
			cell.setCellStyle(_styleIntestazioni);
			cell.setCellValue( nomiRighe.get(r) );
		}
	}
	
	protected void scriviIntestazioneColonne(Sheet sheet, int startColonna, int startRiga, ArrayList<DTO_InformazioniColonna> info_colonne ) 
	{
		if(info_colonne == null || info_colonne.size() == 0)
			return;
		for(int c=0; c < info_colonne.size(); c++)
		{
			Cell cell = sheet.getRow(startRiga).createCell(c + startColonna);
			cell.setCellStyle(_styleIntestazioni);
			cell.setCellValue( info_colonne.get(c).getNomeColonna() );
		}
	}
	
	protected void scriviIntestazione(int sheetIndx, int startRiga, Sheet sheet, ArrayList<String> intestazione)
	{
		if(intestazione == null)
			return;
		int maxLunghezzaIntestazione = minimumColumnWidth;		//una variabile che serve poi per l'autosize

		for(int r=0; r < intestazione.size(); r++)
		{
			Cell cell = sheet.getRow(r+startRiga).createCell(1);
			cell.setCellStyle(_styleTesto);
			cell.setCellValue( intestazione.get(r));
			if( intestazione.get(r).length() > maxLunghezzaIntestazione)
				maxLunghezzaIntestazione = intestazione.get(r).length();
		}
		if(_sheets.get(sheetIndx).getMaxLunghezzaIntestazione() < maxLunghezzaIntestazione)
			_sheets.get(sheetIndx).setMaxLunghezzaIntestazione(maxLunghezzaIntestazione);
	}

	protected void creaRighe(Sheet sheet, int numeroTotaleRighe)
	{
		int start = 0;
		try
		{
			start = sheet.getLastRowNum();
		} catch (Exception e)
		{
			start = 0;
		}
		if(start <0)
			start = 0;
		
		for(int i = 0; i <= numeroTotaleRighe; i++)
		{
			sheet.createRow(start+i);
		}

	}

	/** ereditare questo metodo per modificare gli stili utilizzati  */
	protected void initStili()
	{
		// creo i font \\
		Font fontGrassetto = _workBook.createFont();
		fontGrassetto.setBoldweight(Font.BOLDWEIGHT_BOLD);
		fontGrassetto.setFontHeightInPoints((short) 10);

		Font fontTesto = _workBook.createFont();
		fontTesto.setBoldweight(Font.BOLDWEIGHT_NORMAL );
		fontTesto.setFontHeightInPoints((short) 10);
		
		Font fontDati = _workBook.createFont();
		fontDati.setBoldweight(Font.BOLDWEIGHT_NORMAL);
		fontDati.setFontHeightInPoints((short) 10);

		// creo gli stili \\
		
		_styleIntestazioni = _workBook.createCellStyle();
		_styleIntestazioni.setAlignment(CellStyle.ALIGN_CENTER);
		_styleIntestazioni.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		_styleIntestazioni.setBorderTop(CellStyle.BORDER_THIN);
		_styleIntestazioni.setBorderRight(CellStyle.BORDER_THIN);
		_styleIntestazioni.setBorderBottom(CellStyle.BORDER_THIN);
		_styleIntestazioni.setBorderLeft(CellStyle.BORDER_THIN);
		_styleIntestazioni.setFillPattern(XSSFCellStyle.FINE_DOTS);
		_styleIntestazioni.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.index);
		_styleIntestazioni.setFont(fontGrassetto);

		_styleTesto = _workBook.createCellStyle();
		_styleTesto.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		_styleTesto.setFont(fontTesto);
		
		_styleDatiCodice = _workBook.createCellStyle();
		_styleDatiCodice.setAlignment(CellStyle.ALIGN_CENTER);
		_styleDatiCodice.setBorderTop(CellStyle.BORDER_THIN);
		_styleDatiCodice.setBorderRight(CellStyle.BORDER_THIN);
		_styleDatiCodice.setBorderBottom(CellStyle.BORDER_THIN);
		_styleDatiCodice.setBorderLeft(CellStyle.BORDER_THIN);
		_styleDatiCodice.setFont(fontDati);
		
		_styleDatiDescrizione = _workBook.createCellStyle();
		_styleDatiDescrizione.setAlignment(CellStyle.ALIGN_LEFT);
		_styleDatiDescrizione.setBorderTop(CellStyle.BORDER_THIN);
		_styleDatiDescrizione.setBorderRight(CellStyle.BORDER_THIN);
		_styleDatiDescrizione.setBorderBottom(CellStyle.BORDER_THIN);
		_styleDatiDescrizione.setBorderLeft(CellStyle.BORDER_THIN);
		_styleDatiDescrizione.setFont(fontDati);
		
		_styleDatiImporto = _workBook.createCellStyle();
		_styleDatiImporto.setAlignment(CellStyle.ALIGN_RIGHT);
		_styleDatiImporto.setBorderTop(CellStyle.BORDER_THIN);
		_styleDatiImporto.setBorderRight(CellStyle.BORDER_THIN);
		_styleDatiImporto.setBorderBottom(CellStyle.BORDER_THIN);
		_styleDatiImporto.setBorderLeft(CellStyle.BORDER_THIN);
		_styleDatiImporto.setFont(fontDati);
	}
}//EO KExcelWriter