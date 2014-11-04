package it.konga.framework.util.excel;

import it.konga.framework.interfaces.KFileWriter;

import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
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
	 * @param nomiColonne nomi da assegnare alle colonne, utilizzando lo stile definito per le intestazioni
	 */
	@Override
	public void appendSheetData(String[][] tabellaValori, ArrayList<String> nomiColonne)
	{
		appendSheetData(tabellaValori, null, null, nomiColonne,null);
	}
	/**
	 * aggiungi informazioni sul prossimo sheet
	 * @param tabellaValori tabella dei valori da scrivere sul file
	 * @param nomeSheet nome da assegnare al tab riguardante questo sheet
	 * @param intestazione Ogni stringa dell'array apparirà in righe successive, in alto a sinistra nel foglio, prima della tabella
	 * @param nomiColonne nomi da assegnare alle colonne, utilizzando lo stile definito per le intestazioni
	 */
	public void appendSheetData(String[][] tabellaValori, String nomeSheet, ArrayList<String> intestazione, ArrayList<String> nomiColonne)
	{
		appendSheetData(tabellaValori, nomeSheet, intestazione, nomiColonne, null);
	}
	/**
	 * aggiungi informazioni sul prossimo sheet
	 * @param tabellaValori tabella dei valori da scriveere sul file
	 * @param nomeSheet nome da assegnare al tab riguardante questo sheet
	 * @param intestazione ogni stringa dell'arrayu apparirà in righe successive, in alto  a sinistra nel foglio, prima della tabella
	 * @param nomiColonne nomi da assegnare alle colonne, utilizzando lo stile definito per le intestazioni
	 * @param nomiRighe nomi da assegnare alle righe, (colonna di sinistra) , utilizzando lo stile definito per le intestazioni
	 */
	public void appendSheetData(String[][] tabellaValori, String nomeSheet, ArrayList<String> intestazione, ArrayList<String> nomiColonne, ArrayList<String> nomiRighe)
	{
		if( _matriciValori == null || _matriciValori.length<= 0 )
			_matriciValori = new String[maxNumberOfSheets][][];
		if(currentNumberOfSheets >= maxNumberOfSheets)
			return;
		int sheetIndex = currentNumberOfSheets++;
		if(nomeSheet != null)
			_sheetNames[sheetIndex] = nomeSheet;
		_matriciValori[sheetIndex] = tabellaValori;
		setNomiColonne(sheetIndex, nomiColonne);
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
			createSheet(i, sheet, _matriciValori[i], _arrayIntestazioni.get(i), _arrayNomiColonne.get(i), _arrayNomiRighe.get(i));
		}
		return _workBook;
	}

	/** ereditare questo metodo se si vuole cambiare il modo in cui i dati vengono scritti sul file */
	protected void createSheet(int sheetIndex, Sheet sheet, String[][] body, ArrayList<String> intestazione, ArrayList<String> nomi_colonne , ArrayList<String> nomi_righe )
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
			int width = 256;
			if(c==0 && flagIntestazioniRighe == false )
				width = 256;
			else if(c==1)
				width = maxLunghezzaIntestazione*256;
			else
				width = fixedColumnWidth*256;
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
}//EO KExcelWriter