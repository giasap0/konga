package it.konga.framework.util.excel;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;


public abstract class KAbstract_ExcelWriter
{

	// ------------------------------------------------- METODI STATICI DI UTILITY ------------------------------------------------- \\
	
	/**
	    * Crea una merged region fra le celle nella colonna, imposta a tutte lo stesso stile, e poi inserisce il contenuto content<br>
	    * @param sheetIndx indice dello sheet
	    * @param columnIndx colonna nella quale si vuole operare
	    * @param columnNumber numero di colonne occupate da questa cella (ci si aspetta un numero >=1)
	    * @param firstRow riga di partenza (inclusa)
	    * @param lastRow riga di arrivo (inclusa)
	    * @param style stile da applicare
	    * @param content contenuto che si vuole visualizzare
	    */
	   protected static void creaCellaVerticale(Sheet sheet, int columnIndx, int columnNumber, int firstRow, int lastRow, CellStyle style, String content)
	   {
	       sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, columnIndx, columnIndx+columnNumber-1)); 
	       for( int r= firstRow; r <= lastRow; r++)
	       {
	    	   KAbstract_ExcelWriter.creaRiga(sheet, r);
	       }     
	       for(int c = columnIndx; c<columnIndx+columnNumber; c++)
	       {
	    	   KAbstract_ExcelWriter.impostaStileAlleRighe(sheet, firstRow, lastRow, c, style);
	       }
	       KAbstract_ExcelWriter.creaCella(sheet, firstRow, columnIndx, style, content);
	   }
	   
	   /**
	     * crea una riga all'indice rowIndex<br>
	     * Se la riga già esiste gli imposta solo l'altezza a 300
	     * @param shee sheet nel quale creare la riga
	     * @param rowIndex indice della riga da creare
	     * @return la riga interessata
	     */
	    protected static Row creaRiga(Sheet sheet, int rowIndex)
	    {
			Row row = sheet.getRow(rowIndex);
			if( row == null)
			    row = sheet.createRow(rowIndex);
			return row;
	    }
	    
	    /**
	     * crea una riga all'indice rowIndex di altezza fixedHeight<br>
	     * Se la riga già esiste gli imposta solo l'altezza a 300
	     * @param sheet sheet nel quale creare la riga
	     * @param rowIndex indice della riga da creare
	     * @param fixedHeight altezza fissa della riga
	     * @return la riga interessata
	     */
	    protected static Row creaRiga(Sheet sheet, int rowIndex, int fixedHeight)
	    {
			Row row = sheet.getRow(rowIndex);
			if( row == null)
			    row = sheet.createRow(rowIndex);
			row.setHeight((short) fixedHeight);
			return row;
	    }
	   
	   /**
	     * imposta a tutte le celle in questa colonna, nell'intervallo di righe desiderato, lo stile style<br>
	     * se tutte o alcune righe nel range non esistono vengono create<br>
	     * Il contenuto di qualsiasi cella viene impostato a ""<br>
	     * @param sheet sheet in cui compiere l'operazione
	     * @param firstRow prima riga (inclusa) 
	     * @param lastRow ultima riga (inclusa)
	     * @param column colonna alla quale applicare lo stile
	     * @param style stile della cella
	     */
	    protected static void impostaStileAlleRighe(Sheet sheet, int firstRow, int lastRow, int column, CellStyle style)
	    {
			for(int rowNum = firstRow; rowNum <= lastRow; rowNum++)
			{
			    creaCella(sheet, rowNum, column, style, "");
			}
	    }
	    /**
	     * Opera nella riga passata in input:<br> Imposta a tutte le celle nel range passato, lo stile voluto ed il content == ""<br>
	     * <h3>prima ed ultima cella INCLUSE</h3>
	     * @param sheet indice dello sheet
	     * @param row indice di riga nella quale operare
	     * @param firstCell prima cella alla quale applicare lo stile (inclusa)
	     * @param lastCell ultima cella alla quale applicare lo stile (inclusa)
	     * @param style stile da applicare
	     */
	    protected static void impostaStileAlleColonne(Sheet sheet, int row, int firstCell, int lastCell, CellStyle style)
	    {
			for(int cell = firstCell; cell <= lastCell; cell++)
			{
			    creaCella(sheet, row, cell, style, "");
			}
	    }
	    
	    /**
	     * Metodo di utility per creare una cella nella posizione indicata, che contenga una determinata stringa , con un determinato stile.<br>
	     * Se la riga a quell'indice non esiste, viene creata. Lo sheet invece deve esistere.<br>
	     * <h3>WARNING: se la cella già esiste viene sovrascritta</h3>
	     * @param sheet sheet nel quale si vuole creare la cella
	     * @param row indice di riga (nota: parte da 0)
	     * @param colonna indice di colonna (nota: parte da 0)
	     * @param stile stile da applicare alla cella
	     * @param contenuto Stringa da inserire nella cella
	     */
	    protected static void creaCella(Sheet sheet ,int row, int colonna, CellStyle stile, String contenuto)
	    {
			if( sheet.getRow(row) == null)
			{
				sheet.createRow(row);
			}
			//creo cella 'bianca', e poi le setto lo stile ed il contenuto. Se la cella già esisteva sovrascrivo stile e contenuto
			Cell cella = KAbstract_ExcelWriter.createBlankCell(sheet, row, colonna);
			cella.setCellStyle(stile);
			cella.setCellValue(contenuto);
	    }
	    
	    /**
	     * fa autosize su tutte le colonne dello sheet
	     */
	    protected static void autoSizeColonne(Sheet sheet,int numColonne)
	    {
			for(int cell=0; cell <= numColonne; cell++)
			{
				sheet.autoSizeColumn(cell);
			}
	    }
	    
	    /**
	     * Metodo di utility : restituisce l'oggetto cella nella posizione desiderata.<br>
	     * Sia la riga che la cella devono già esistere.
	     * @param sheet sheet di riferimento
	     * @param row indice della riga nella quale risiede la cella (Nota: parte da 0)
	     * @param column indice della colonna della cella. La cella deve essere già creata. (l'indice parte da 0).
	     * @return la cella desiderata alle coordinate (row, column).
	     */
	    protected static Cell getCell(Sheet sheet, int row, int column)
	    {
	    	return sheet.getRow(row).getCell(column);
	    }
	    /**
	     * Metodo di utility: crea una cella nella posizione desiderata, senza stili applicati. <br>
	     * <h3>Se la cella esiste già ritorna la cella già esistente</h3><br>
	     * <h3>WARNING: la riga deve esistere</h3>
	     * @param sheet sheet di riferimento
	     * @param row indice della riga di appartenenza della cella
	     * @param column indice della colonna nella quale creare la cella
	     * @return cella creata
	     */
	    protected static Cell createBlankCell(Sheet sheet, int row, int column)
	    {
			if(sheet.getRow(row).getCell(column) != null)
			    return sheet.getRow(row).getCell(column);
			return sheet.getRow(row).createCell(column);
	    }
	    
	    /**
	     * Metodo di utility, apre un file e lo converte in un array di Byte.<br>
	     * L'array di byte è utilizzato da POI per inserire le immagini nel workBook
	     * @param fileName path/nome del file da convertire
	     * @return 'versione binaria' del file
	     * @throws IOException Lancia IOException se il file non esiste o ci sono altri problemi in lettura/scrittura
	     */
	    protected static byte[] convertFileToBytes(InputStream image_asStream) throws IOException
	    {
			byte [] array = IOUtils.toByteArray(image_asStream);
			image_asStream.close();
			return array;
	    }
}//EO KAbstract_ExcelWriter
