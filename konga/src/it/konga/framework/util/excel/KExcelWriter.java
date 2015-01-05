package it.konga.framework.util.excel;

import it.konga.framework.interfaces.KFileWriter;
import it.konga.framework.kObjects.DTO_InformazioniColonna;

import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 * Questo oggetto permette di scrivere su un outputStream un file excel composto da più sheet, in ognuno dei quali sono presenti una intestazione ed una tabella<br>
 * Nel costruttore indicare il numero massimo di sheet che si vorranno creare<br>
 * usare uno dei metodi appendSheetData per inserire le informazioni su ogni sheet che si vuole creare <br>
 * Ereditare da questa se si vogliono cambiare gli stili o la formattazione del foglio<br>
 * Scrive file *.xlsx
 *  
 * @author Giampaolo Saporito
 * @Date - 02.10.2014
 */
public class KExcelWriter extends KMultiSheet_ExcelWriter implements KFileWriter {

	private int maxLunghezzaIntestazione;
	private final static int fixedColumnWidth = 20;
	/** le intestazioni sono inserite in alto a sinistra, prima della tabella */
	protected ArrayList<ArrayList<String>> _arrayIntestazioni;
	/** nomi da dare ai tab degli sheet */
	protected String[] _sheetNames;
	/** align center */
	protected CellStyle _styleDatiCodice = null;
	/** align left */
	protected CellStyle _styleDatiDescrizione = null;
	/** align right */
	protected CellStyle _styleDatiImporto = null;

	/**
	 * costruisce l'oggetto capace di scrivere un file excel con un certo nhumero di sheets<br>
	 * Usare uno dei metodi appendSheetData per inserire le informazioni su ogni sheet che si vuole creare
	 * @param maxNumberOfSheets massimo numero di sheet
	 */
	public KExcelWriter(int maxNumberOfSheets)
	{
		super(maxNumberOfSheets);

		_arrayIntestazioni = new ArrayList<ArrayList<String>>(maxNumberOfSheets);
		_sheetNames = new String[maxNumberOfSheets];
		for(int i=0; i < maxNumberOfSheets; i++)
		{
			_arrayIntestazioni.add(null);
			_sheetNames[i] = "sheet "+i;
		}
	}

	// **************************************************************************** METODI PUBLICI **************************************************************************** \\

	/** fai un set dell'intestazione. Ogni stringa dell'array apparirà in righe successive, in alto a sinistra nel foglio, prima della tabella */
	public void setIntestazione(int sheetIndex ,ArrayList<String> intestazione)
	{
		if(sheetIndex<0 || sheetIndex>= currentNumberOfSheets)
			throw new IndexOutOfBoundsException("ExcelWriter::setIntestazione() - indice non valido. indx == "+sheetIndex+" , numero sheets = "+currentNumberOfSheets);
		_arrayIntestazioni.set(sheetIndex, intestazione);
	}
	/** fai un set del nome da dare al tab corrispondente a questo sheet */
	public void setSheetName(int sheetIndex, String nomeSheet)
	{
		if(sheetIndex<0 || sheetIndex>= currentNumberOfSheets)
			throw new IndexOutOfBoundsException("ExcelWriter::setSheetName() - indice non valido. indx == "+sheetIndex+" , numero sheets = "+currentNumberOfSheets);
		if(nomeSheet != null)
			_sheetNames[sheetIndex] = nomeSheet;
	}
	/**
	 * aggiungi informazioni sul prossimo sheet
	 * @param tabellaValori tabella dei valori da scrivere sul file
	 */
	@Override
	public void appendSheetData(String[][] tabellaValori)
	{
		appendSheetData(tabellaValori,null, null,null,null);
	}
	/**
	 * aggiungi informazioni sul prossimo sheet
	 * @param tabellaValori tabella dei valori da scrivere sul file
	 * @param infoColonne informazioni da assegnare alle colonne
	 */
	@Override
	public void appendSheetData(String[][] tabellaValori, ArrayList<DTO_InformazioniColonna> infoColonne)
	{
		appendSheetData(tabellaValori, null, null, infoColonne,null);
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
	 * Aggiungi informazioni sul prossiumo sheet
	 * @param nomeSheet nome da assegnare al tab riguardante questo sheet
	 * @param strutturaExcel struttura che rappresenta il foglio corrente. Contiene i dati, le informazioni riguardanti le colonne e l'intestazione
	 */
	public void appendSheetData(String nomeSheet, IStrutturaExcel strutturaExcel)
	{
		appendSheetData(strutturaExcel.getDati(), nomeSheet,strutturaExcel.getStringheIntestazione(), strutturaExcel.getInformazioniColonne(), null);
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
		if( _matriciValori == null || _matriciValori.length<= 0 )
			_matriciValori = new String[maxNumberOfSheets][][];
		if(currentNumberOfSheets >= maxNumberOfSheets)
			return;
		int sheetIndex = currentNumberOfSheets++;
		if(nomeSheet != null)
			_sheetNames[sheetIndex] = nomeSheet;
		_matriciValori[sheetIndex] = tabellaValori;
		setInformazioniColonne(sheetIndex, infoColonne);
		setNomiRighe(sheetIndex, nomiRighe);
		setIntestazione(sheetIndex, intestazione);
		if(tabellaValori.length > MAX_BUFFER_SIZE)
			throw new IndexOutOfBoundsException("ExcelWriter::appendSheetData() - tabella valori troppo ampia. Numero righe = "+tabellaValori.length + ". Massimo supportato = "+MAX_BUFFER_SIZE);
		if(intestazione != null )
		{
			if(bufferSize < intestazione.size()+2 + tabellaValori.length+3)
				bufferSize =  intestazione.size()+2 + tabellaValori.length+3;
		}
		else if(bufferSize<tabellaValori.length+3)
		{
			bufferSize = tabellaValori.length+3;//2 sono la prime righe vuote,1 è la riga con le intestazioni di colonna
		}
	}	

	// **************************************************************************** METODI PROTECTED **************************************************************************** \\

	@Override
	protected SXSSFWorkbook creaWorkBook()
	{
		_workBook = new SXSSFWorkbook(bufferSize);
		initStili();
		for(int i=0; i < currentNumberOfSheets; i++)
		{
			Sheet sheet = _workBook.createSheet();
			_workBook.setSheetName(i, _sheetNames[i]);
			createSheet(i, sheet, _matriciValori[i], _arrayIntestazioni.get(i), _arrayColonne.get(i), _arrayNomiRighe.get(i));
		}
		return _workBook;
	}

	/** ereditare questo metodo se si vuole cambiare il modo in cui i dati vengono scritti sul file */
	protected void createSheet(int sheetIndex, Sheet sheet, String[][] body, ArrayList<String> intestazione, ArrayList<DTO_InformazioniColonna> info_colonne , ArrayList<String> nomi_righe )
	{	
		int numRighe = body.length +3 ;
		if(intestazione != null)
			numRighe += intestazione.size() +2;
		creaRighe(sheet, numRighe);
		int startColonna = 1;
		int startRiga = 2;
		boolean flagIntestazioniRighe = false;

		if(intestazione != null && intestazione.size() > 0)
		{
			scriviIntestazione(sheet, sheetIndex);
			startRiga = intestazione.size() +4;
		}
		if(nomi_righe != null && nomi_righe.size() > 0)
		{
			flagIntestazioniRighe = true;
			startColonna = 2;
			scriviColonnaSinistra(sheet, sheetIndex,startRiga);
		}
		if(info_colonne != null && info_colonne.size() > 0)
		{
			scriviIntestazioneColonne(sheet, sheetIndex, startColonna,startRiga);
			++startRiga;
		}
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
					break;
				case IMPORTO:
					cell.setCellStyle(_styleDatiImporto);
					break;
				case CODICE:
				default:
					cell.setCellStyle(_styleDatiCodice);
					break;
				}//EO switch
				cell.setCellValue( (body[r][c]));
				if(c+startColonna > numeroColonne)
					numeroColonne = c+startColonna;
			}
		}
		//autosize su tutto
		autosizeColonne(numeroColonne, sheet, info_colonne, flagIntestazioniRighe);
	}

	protected void autosizeColonne(int numeroColonne, Sheet sheet,  ArrayList<DTO_InformazioniColonna> info_colonne, boolean flagIntestazioniRighe)
	{
		if(info_colonne != null && numeroColonne < info_colonne.size() +1 )
			numeroColonne = info_colonne.size() +1;
		for(int c=0; c <= numeroColonne; c++)
		{
			int width = 256;
			if(c==0 && flagIntestazioniRighe == false )
				width = 256;
			else if(c==1)
				width = maxLunghezzaIntestazione<fixedColumnWidth ? fixedColumnWidth*256 : maxLunghezzaIntestazione*256;
			else
			{
				width = fixedColumnWidth;
				//confronto con il titolo della colonna (se esiste)
				if(info_colonne != null && c <= info_colonne.size() )			
					width = width < (info_colonne.get(c-1).getNomeColonna().length() +2) ? (info_colonne.get(c-1).getNomeColonna().length() + 2) : width;
					width = width*256;
			}
			sheet.setColumnWidth(c, width);
		}
	}

	protected void scriviIntestazione(Sheet sheet, int sheetIndex)
	{
		maxLunghezzaIntestazione = fixedColumnWidth;
		int startRiga = 1;
		for(int r=0; r <_arrayIntestazioni.get(sheetIndex).size(); r++)
		{
			Cell cell = sheet.getRow(r+startRiga).createCell(1);
			cell.setCellStyle(_styleTesto);
			cell.setCellValue( _arrayIntestazioni.get(sheetIndex).get(r));
			if(_arrayIntestazioni.get(sheetIndex).get(r).length() > maxLunghezzaIntestazione)
				maxLunghezzaIntestazione = _arrayIntestazioni.get(sheetIndex).get(r).length();
		}
	}

	@Override
	protected void initStili()
	{
		super.initStili();
		// creo i font \\
		Font fontDati = _workBook.createFont();
		fontDati.setBoldweight(Font.BOLDWEIGHT_NORMAL);
		fontDati.setFontHeightInPoints((short) 10);

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