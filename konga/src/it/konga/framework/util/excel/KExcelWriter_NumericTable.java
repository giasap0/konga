package it.konga.framework.util.excel;

import it.konga.framework.interfaces.KFileWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Questa imploementazione utilizza una matrice di double per inserire i dati<br>
 * Setta tutte le celle su excel di tipo "numeric"<br>
 * Sarà quindi poi possibile effettuare su di esse calcoli di vario genere<br>
 * Scrive file *.xls
 * @author Giampaolo Saporito
 * @Date 02.09.2014
 */
public class KExcelWriter_NumericTable extends KBasic_ExcelWriter implements KFileWriter
{
	// ----------------------------------------------------------------------------------- fields ----------------------------------------------------------------------------------- \\
    protected Double[][] _valoriNumerici;

    // ----------------------------------------------------------------------------------- costruttori ----------------------------------------------------------------------------------- \\
    public KExcelWriter_NumericTable(Double[][] valoriTabella) {
    	this(valoriTabella,null,null);
    }

    public KExcelWriter_NumericTable(Double[][] valoriTabella, ArrayList<String> nomiRighe, ArrayList<String> nomiColonne) {
		super(null, nomiRighe, nomiColonne);
		_valoriNumerici = valoriTabella;
    }
    
    // ----------------------------------------------------------------------------------- metodi publici ----------------------------------------------------------------------------------- \\
    @Override
    public void writeFile(OutputStream out) throws IOException, NullPointerException {
		if(_valoriNumerici == null || _valoriNumerici.length <= 0)
		    throw new NullPointerException("NumericExcelTableWriter::writeFile() - impossibile creare il file, array di valori interno nullo");
		
		this.creaWorkBook();
		_workBook.write(out);
		_workBook = null;
    }
    
    // ----------------------------------------------------------------------------------- overrides ----------------------------------------------------------------------------------- \\
    @Override
    protected HSSFWorkbook creaWorkBook()
    {
		_workBook = new HSSFWorkbook();
		HSSFSheet sheet  = _workBook.createSheet();
		
		initStili();
		creaRighe(sheet, _valoriNumerici.length+2);
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
		for(int r=0; r < _valoriNumerici.length ; r++)
		{
		    for(int c = 0 ; c < _valoriNumerici[r].length ; c++)
		    {
			HSSFCell cell = sheet.getRow(r+startRiga).createCell(c + startColonna);
			cell.setCellStyle(_styleTesto);
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell.setCellValue( _valoriNumerici[r][c]);
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
}
