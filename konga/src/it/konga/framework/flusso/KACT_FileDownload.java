package it.konga.framework.flusso;

import it.konga.framework.interfaces.KFileWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

@Results({
	@Result(name="success", type="stream",
			params = {"contentType","application/vnd.ms-excel;charset=utf-8",   
	        "inputName", "file",  
	        "contentDisposition", "attachment;filename=${fileName}"} )
})
public abstract class KACT_FileDownload extends KBaseAction
{
	private static final long serialVersionUID = -174852288845066L;
	
	private InputStream file;
	private String fileName;
	protected KFileWriter _fileWriter;
	
	/**
	 * ereditare questo metodo e scrivere un body che assegna una classe concreta e valorizzata al campo _fileWriter
	 */
	protected abstract void initFileWriter();

	@Override
	public String execute() throws Exception
	{   
		initFileWriter();
		
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
}
