package it.konga.framework.util.excel;


import it.konga.framework.interfaces.KFileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

	/* ****************************************************************************************************
	 *** ESEMPIO D'USO
	  public static void main(String[] args) throws NullPointerException, IOException
	  {
		String[][] matrice = {
			{"prontoProva","test1","cane","gatto","8.9","25/11/1999","ciao amico"},
			{"prontoProva","test1","cane","gatto","8.9","25/11/1999","ciao amico"},
			{"prontoProva","test1","cane","gatto","8.9","25/11/1999","ciao amico"}
		};
		ArrayList<String> intestazione = new ArrayList<String>();
		intestazione.add("colonna 1");
		intestazione.add("colonna test");
		intestazione.add("animale");
		intestazione.add("felino");
		intestazione.add("double");
		intestazione.add("data");
		intestazione.add("colonna test molto lunga lunga luuuuunga");
		
		ArrayList<String> righe = new ArrayList<String>();
		righe.add("riga 1");
		righe.add("riga 2");
		righe.add("riga 3");
		
		KBasic_ExcelWriter writer = new KBasic_ExcelWriter(matrice,righe,intestazione);
		FileOutputStream out = new FileOutputStream("file.xls");
		writer.writeFile(out);
		out.close();
		System.out.println("FILE CREATO");
	  }
	******************************************************************************************************/

	/**
	 * Questo oggetto permette di scrivere su un outputStream un file excel<br>
	 * A partire da una matrice di stringhe va a scrivere una semplice tabella<br>
	 * Ereditare da questa se si vogliono cambiare gli stili o la formattazione del foglio<br>
	 * Passare gli array nomiRighe , nomiColonne se si desidera inserirli. O tramite costruttore o tramite setters<br>
	 * Scrive file *.xls
	 *  
	 * @author Giampaolo Saporito
	 * @Date - 02.09.2014
	 */
	public class KBasic_ExcelWriter implements KFileWriter
	{
		// ----------------------------------------------------------------------------------- fields ----------------------------------------------------------------------------------- \\
	    protected ArrayList<String> nomiColonne;
	    protected ArrayList<String> nomiRighe;
	    protected String[][] _matriceValori;
	    protected HSSFWorkbook _workBook;
	    protected HSSFCellStyle  _styleIntestazioni;
	    protected HSSFCellStyle _styleTesto;
	    
	    // ----------------------------------------------------------------------------------- costruttori ----------------------------------------------------------------------------------- \\
	    /**
	     * A partire da una matrice di stringhe va a scrivere una semplice tabella
	     * @param valoriTabella tutti i valori da inserire in tabella [righe][colonne]
	     */
	    public KBasic_ExcelWriter(String[][] valoriTabella)
	    {
	    	this(valoriTabella, null, null);
	    }
	    
	    /**
	     * A partire da una matrice di stringhe va a scrivere una semplice tabella<br>
	     * Mette a sinistra una colonna con i 'nomiRighe'<br>
	     * Inserisce una intestazione in alto con le stringhe 'nomiColonne
	     * @param valoriTabella tutti i valori da inserire in tabella [righe][colonne]
	     * @param nomiRighe nomi delle righe: colonna a sinistra
	     * @param nomiColonne nomi delle colonne: intestazione in alto
	     */
	    public KBasic_ExcelWriter(String[][] valoriTabella, ArrayList<String> nomiRighe, ArrayList<String> nomiColonne)
	    {	
			_matriceValori = valoriTabella;
			this.setNomiRighe(nomiRighe);
			this.setNomiColonne(nomiColonne);
	    }
	    
	    // ----------------------------------------------------------------------------------- metodi publici ----------------------------------------------------------------------------------- \\
	    
	    /**
	     * Scrive il file sullo stream.<br>
	     * Dopo aver invocato questo metodo ricordarsi di chiudere lo stream
	     * @param out stream sul quale scrivere
	     * @throws IOException problemi nella scrittura del file
	     * @throws NullPointerException parametri interni di dati non validi
	     */
	    @Override
	    public void writeFile(OutputStream out) throws IOException , NullPointerException
	    {
			if(_matriceValori == null || _matriceValori.length <= 0)
			    throw new NullPointerException("SimpleExcelTableWriter::writeFile() - impossibile creare il file, array di valori interno nullo");
			
			creaWorkBook();
			_workBook.write(out);
			_workBook = null;
	    }
	  
	    // ----------------------------------------------------------------------------------- getters e setters ----------------------------------------------------------------------------------- \\
	    public ArrayList<String> getNomiColonne() {
	    	return nomiColonne;
	    }

	    public void setNomiColonne(ArrayList<String> nomiColonne) {
	    	this.nomiColonne = nomiColonne;
	    }

	    public ArrayList<String> getNomiRighe() {
	    	return nomiRighe;
	    }

	    public void setNomiRighe(ArrayList<String> nomiRighe) {
	    	this.nomiRighe = nomiRighe;
	    }
	    
	    /// ----------------------------------------------------------------------------------- metodi protected ----------------------------------------------------------------------------------- \\
	    
	    protected HSSFWorkbook creaWorkBook()
	    {
			_workBook = new HSSFWorkbook();
			HSSFSheet sheet  = _workBook.createSheet();
			
			initStili();
			creaRighe(sheet, _matriceValori.length +2);
			int startColonna = 1;
			final int startRiga = 2;
			
			if(nomiRighe != null && nomiRighe.size() > 0)
			{
			    startColonna = 2;
			    scriviColonnaSinistra(sheet);
			}
			if(nomiColonne != null && nomiColonne.size() > 0)
			{
			    scriviIntestazione(sheet, startColonna);
			}
			int numeroColonne = 0;
			//metto i valori
			for(int r=0; r < _matriceValori.length ; r++)
			{
			    for(int c = 0 ; c < _matriceValori[r].length ; c++)
			    {
				HSSFCell cell = sheet.getRow(r+startRiga).createCell(c + startColonna);
				cell.setCellStyle(_styleTesto);
				cell.setCellValue( new HSSFRichTextString(_matriceValori[r][c]));
				if(c+startColonna > numeroColonne)
				    numeroColonne = c+startColonna;
			    }
			    
			}
			//autosize su tutto
			for(int c = 0 ; c <= numeroColonne ; c++)
			{
			    sheet.autoSizeColumn(c);
			}
			return _workBook;
	    }
	    
	    protected void scriviColonnaSinistra(HSSFSheet sheet)
	    {
			for(int r=0; r < nomiRighe.size(); r++)
			{
			    HSSFCell cell = sheet.getRow(r+2).createCell( 1);
			    cell.setCellStyle(_styleIntestazioni);
			    cell.setCellValue( new HSSFRichTextString(nomiRighe.get(r)));
			}
	    }
	    protected void scriviIntestazione(HSSFSheet sheet, int startColonna)
	    {
			for(int c=0; c < nomiColonne.size(); c++)
			{
			    HSSFCell cell = sheet.getRow(1).createCell(c + startColonna);
			    cell.setCellStyle(_styleIntestazioni);
			    cell.setCellValue( new HSSFRichTextString(nomiColonne.get(c)));
			}
	    }
	    
	    protected void creaRighe(HSSFSheet sheet, int numeroTotaleRighe)
	    {
			for(int i = 0; i <= numeroTotaleRighe; i++)
			{
			    sheet.createRow(i);
			}
	    }
	    
	    /**
	     * ereditare questo metodo per modificare gli stili utilizzati
	     */
	    protected void initStili()
	    {
			HSSFFont fontGrassetto = _workBook.createFont();
			fontGrassetto.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			fontGrassetto.setFontHeightInPoints((short) 10);
			
			HSSFFont fontTesto = _workBook.createFont();
			fontTesto.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL );
			fontTesto.setFontHeightInPoints((short) 10);
			
			_styleIntestazioni = _workBook.createCellStyle();
			_styleIntestazioni.setFont(fontGrassetto);
			_styleIntestazioni.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
			_styleIntestazioni.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
			_styleIntestazioni.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
			_styleIntestazioni.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
			_styleIntestazioni.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			_styleIntestazioni.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			
			_styleTesto = _workBook.createCellStyle();
			_styleTesto.setFont(fontTesto);
	    }
	    
	}//EO class SimpleExcelTableWriter
