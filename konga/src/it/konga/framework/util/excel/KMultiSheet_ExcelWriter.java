package it.konga.framework.util.excel;

import java.io.OutputStream;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import it.konga.framework.interfaces.KFileWriter;

/**
 * Questo oggetto permette di scrivere su un outputStream un file excel composto da più sheet<br>
 * Nel costruttore indicare il numero massimo di sheet che si vorranno creare<br>
 * usare uno dei metodi appendSheetData per inserire le informazioni su ogni sheet che si vuole creare <br>
 * Ereditare da questa se si vogliono cambiare gli stili o la formattazione del foglio<br>
 * Scrive file *.xlsx
 *  
 * @author Giampaolo Saporito
 * @Date - 02.10.2014
 */
public class KMultiSheet_ExcelWriter implements KFileWriter {

	/** limitazione a priori del numero massimo di righe (poi cominciano problemi di performance) */
	protected static final int MAX_BUFFER_SIZE = 65001;
	/** numero di righe del file */
	protected int bufferSize = 100;
	/** una lista che contiene le liste dei nomi delle colonne per ogni sheet. Alcune o tutte possono essere null*/
	protected ArrayList< ArrayList<String>> _arrayNomiColonne;
	/** una lista che contiene le liste dei nomi delle righe per ogni sheet. Alcune o tutte possono essere null*/
	protected ArrayList<ArrayList<String>> _arrayNomiRighe;
	/** array di matrici contenenti i dati di ogni sheet */
	protected String[][][] _matriciValori;
	protected SXSSFWorkbook _workBook;
	protected CellStyle  _styleIntestazioni;
	protected CellStyle _styleTesto;
	/** numero massimo di sheet che possono essere creati */
	protected int maxNumberOfSheets = 0;
	/** numero di sheet per i quali son stati settati dei dati da stampare */
	protected int currentNumberOfSheets = 0; 

	/**
	 * costruisce l'oggetto capace di scrivere un file excel con un certo nhumero di sheets<br>
	 * Usare uno dei metodi appendSheetData per inserire le informazioni su ogni sheet che si vuole creare
	 * @param maxNumberOfSheets massimo numero di sheet
	 */
	public KMultiSheet_ExcelWriter(int maxNumberOfSheets)
	{
		if(maxNumberOfSheets<=0)
			return;
		this.maxNumberOfSheets = maxNumberOfSheets;
		_arrayNomiColonne = new ArrayList<ArrayList<String>>(maxNumberOfSheets);
		_arrayNomiRighe = new ArrayList<ArrayList<String>>(maxNumberOfSheets);
		for(int i=0; i < maxNumberOfSheets; i++)
		{
			_arrayNomiColonne.add(null);
			_arrayNomiRighe.add(null);
		}
	}
	// **************************************************************************** METODI PUBLICI **************************************************************************** \\

	/**
	 * aggiungi informazioni sul prossimo sheet
	 * @param tabellaValori tabella dei valori da scrivere sul file
	 */
	public void appendSheetData(String[][] tabellaValori)
	{
		appendSheetData(tabellaValori, null);
	}
	/**
	 * aggiungi informazioni sul prossimo sheet
	 * @param tabellaValori tabella dei valori da scrivere sul file
	 * @param nomiColonne nomi da assegnare alle colonne, utilizzando lo stile definito per le intestazioni
	 */
	public void appendSheetData(String[][] tabellaValori, ArrayList<String> nomiColonne)
	{
		if( _matriciValori == null || _matriciValori.length<= 0 )
			_matriciValori = new String[maxNumberOfSheets][][];
		if(currentNumberOfSheets >= maxNumberOfSheets)
			return;
		int sheetIndex = currentNumberOfSheets++;
		_matriciValori[sheetIndex] = tabellaValori;
		setNomiColonne(sheetIndex, nomiColonne);
		if(tabellaValori.length > MAX_BUFFER_SIZE)
			throw new IndexOutOfBoundsException("MultiSheet_ExcelWriter::appendSheetData() - tabella valori troppo ampia. Numero righe = "+tabellaValori.length + ". Massimo supportato = "+MAX_BUFFER_SIZE);
		if(bufferSize<tabellaValori.length+2)
			bufferSize = tabellaValori.length+2;//2 sono la prima riga vuota e la riga di intestazione	
	}

	/** fai un set dei nomi da assegnare alle colonne, utilizzando lo stile definito per le intestazioni */
	public void setNomiColonne(int sheetIndex ,ArrayList<String> nomiColonne)
	{
		if(sheetIndex<0 || sheetIndex>= currentNumberOfSheets)
			throw new IndexOutOfBoundsException("MultiSheet_ExcelWriter::setNomiColonne() - indice non valido. indx == "+sheetIndex+" , numero sheets = "+currentNumberOfSheets);
		_arrayNomiColonne.set(sheetIndex, nomiColonne);
	}
	/** fai un set dei nomi da assegnare alle righe, (colonna di sinistra) , utilizzando lo stile definito per le intestazioni */
	public void setNomiRighe(int sheetIndex, ArrayList<String> nomiRighe)
	{
		if(sheetIndex<0 || sheetIndex>= currentNumberOfSheets)
			throw new IndexOutOfBoundsException("MultiSheet_ExcelWriter::addNomiRighe() - indice non valido. indx == "+sheetIndex+" , numero sheets = "+currentNumberOfSheets);
		_arrayNomiRighe.set(sheetIndex, nomiRighe);
	}	

	/** scrivi il file sullo stream passato in input */
	@Override
	public void writeFile(OutputStream out) throws Exception
	{
		if(currentNumberOfSheets <= 0)
			throw new Exception("MultiSheet_ExcelWriter::writeFile() - invalid number of sheets = " + currentNumberOfSheets);
		if(_matriciValori == null || _matriciValori.length <=0)
			throw new NullPointerException("MultiSheet_ExcelWriter::writeFile() - impossibile creare il file, array di valori interno nullo");
		if(_matriciValori.length > maxNumberOfSheets)
			throw new Exception("MultiSheet_ExcelWriter::writeFile() - impossibile creare il file, array di valori interno di lunghezza incompatibile = "+_matriciValori.length+". Numero Sheet desiderato = "+ maxNumberOfSheets);
		for(int i=0; i < currentNumberOfSheets; i++)
		{
			if( _matriciValori[i] == null)
				throw new NullPointerException("MultiSheet_ExcelWriter::writeFile() - impossibile creare il file, array di valori interno nullo per lo sheet con indice "+i);
		}

		creaWorkBook();
		_workBook.write(out);
		_workBook = null;

	}

	// **************************************************************************** METODI PROTECTED **************************************************************************** \\

	protected SXSSFWorkbook creaWorkBook()
	{
		_workBook = new SXSSFWorkbook(bufferSize);
		initStili();
		for(int i=0; i < currentNumberOfSheets; i++)
			createSheet(i, _workBook.createSheet(), _matriciValori[i], _arrayNomiRighe.get(i), _arrayNomiColonne.get(i));
		return _workBook;
	}

	/** ereditare questo metodo se si vuole cambiare il modo in cui i dati vengono scritti sul file */
	protected void createSheet(int sheetIndex, Sheet sheet, String[][] body, ArrayList<String> nomi_righe, ArrayList<String> nomi_colonne)
	{	
		creaRighe(sheet, body.length +3);
		int startColonna = 1;
		int startRiga = 2;
		boolean flagIntestazioniRighe = false;

		if(nomi_righe != null && nomi_righe.size() > 0)
		{
			flagIntestazioniRighe = true;
			startColonna = 2;
			scriviColonnaSinistra(sheet, sheetIndex,startRiga);
		}
		if(nomi_colonne != null && nomi_colonne.size() > 0)
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
				cell.setCellStyle(_styleTesto);
				cell.setCellValue( (body[r][c]));
				if(c+startColonna > numeroColonne)
					numeroColonne = c+startColonna;
			}
		}
		//autosize su tutto
		if(nomi_colonne != null && numeroColonne < nomi_colonne.size() +1 )
			numeroColonne = nomi_colonne.size() +1;
		for(int c=0; c <= numeroColonne; c++)
		{
			if(c==0 && flagIntestazioniRighe == false)
			{
				sheet.setColumnWidth(c, 256);
			}
			sheet.setColumnWidth(c, 20*256);
			//  sheet.autoSizeColumn(c); //pourtroppo a causa di un bug (noto) della libreria , lascia le colonne a dimensione ridotta
		}
	}

	protected void scriviColonnaSinistra(Sheet sheet, int sheetIndex, int startRiga)
	{
		for(int r=0; r < _arrayNomiRighe.get(sheetIndex).size(); r++)
		{
			Cell cell = sheet.getRow(r+startRiga+1).createCell( 1);
			cell.setCellStyle(_styleIntestazioni);
			cell.setCellValue( _arrayNomiRighe.get(sheetIndex).get(r));
		}
	}
	protected void scriviIntestazioneColonne(Sheet sheet, int sheetIndex, int startColonna, int startRiga) 
	{		
		for(int c=0; c < _arrayNomiColonne.get(sheetIndex).size(); c++)
		{
			Cell cell = sheet.getRow(startRiga).createCell(c + startColonna);
			cell.setCellStyle(_styleIntestazioni);
			cell.setCellValue( _arrayNomiColonne.get(sheetIndex).get(c));
		}
	}

	protected void creaRighe(Sheet sheet, int numeroTotaleRighe)
	{
		for(int i = 0; i <= numeroTotaleRighe; i++)
		{
			sheet.createRow(i);
		}
	}

	/** ereditare questo metodo per modificare gli stili utilizzati */
	protected void initStili()
	{
		Font fontGrassetto = _workBook.createFont();
		fontGrassetto.setBoldweight(Font.BOLDWEIGHT_BOLD);
		fontGrassetto.setFontHeightInPoints((short) 10);

		Font fontTesto = _workBook.createFont();
		fontTesto.setBoldweight(Font.BOLDWEIGHT_NORMAL );
		fontTesto.setFontHeightInPoints((short) 10);

		_styleIntestazioni = _workBook.createCellStyle();
		_styleIntestazioni.setFont(fontGrassetto);
		_styleIntestazioni.setBorderTop(CellStyle.BORDER_MEDIUM);
		_styleIntestazioni.setBorderRight(CellStyle.BORDER_MEDIUM);
		_styleIntestazioni.setBorderBottom(CellStyle.BORDER_MEDIUM);
		_styleIntestazioni.setBorderLeft(CellStyle.BORDER_MEDIUM);
		_styleIntestazioni.setAlignment(CellStyle.ALIGN_CENTER);
		_styleIntestazioni.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		_styleTesto = _workBook.createCellStyle();
		_styleTesto.setFont(fontTesto);
	}
}
