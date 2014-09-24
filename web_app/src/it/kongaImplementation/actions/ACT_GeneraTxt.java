package it.kongaImplementation.actions;

import it.konga.framework.flusso.KBaseAction;
import it.konga.framework.interfaces.KFileWriter;
import it.konga.framework.util.excel.KBasic_ExcelWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

@Results({
	@Result(name="success", type="stream",
			params = {"contentType","application/vnd.ms-excel;charset=utf-8",   
	        "inputName", "file",  
	        "contentDisposition", "attachment;filename=${fileName}"} )
})
public class ACT_GeneraTxt extends KBaseAction
{
	private static final long serialVersionUID = 7943170669760353965L;
	private InputStream file;
	private String fileName;
	protected KFileWriter _fileWriter;

	@Override
	@Action(value="/getTxt")
	public String execute() throws Exception
	{   
		final String[][] matrice = {{"a","b","c"},{"1","2","3"}};
		_fileWriter = new KBasic_ExcelWriter(matrice);
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		_fileWriter.writeFile(output);
		file = new ByteArrayInputStream(output.toByteArray());
		output.close();
        return "success"; 
	}

	public InputStream getFile() {
		return file;
	}

	public void setFile(InputStream file) {
		this.file = file;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}//EO ACT_GeneraTxt
