package it.konga.framework.flusso;

import it.konga.framework.interfaces.KFileWriter;
import it.konga.framework.kObjects.MimeType;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.servlet.http.Cookie;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

/**
 * Istruzioni per l'uso:<br>
 * <ul>
 * <li>Estendere questa classe</li>
 * <li>inintFileWriter() valorizzare _fileWriter</li>
 * <li>initMimeType() valorizzare _mimeType</li>
 * <li>initFileName() - usare setFileName() se si vuole cambiare il nome del file</li>
 * <li>creare un metodo che chiami semplicemente super.execute() ed abbia l'annotation per mappare la action. Es: @Action(value="/getExcel_esempio")</li>
 * </ul>
 * @author Giampaolo Saporito
 */
@Results({
	@Result(name="success", type="stream", params = {"contentType","${_mimeType.mediaType}",   
											        "inputName", "file",  
											        "contentDisposition", "attachment;filename=${fileName}"}
			)
	})
public abstract class KACT_FileDownload extends KBaseAction
{
	private static final long serialVersionUID = -174852288845066L;
	
	private InputStream file;
	private String fileName;
	protected KFileWriter _fileWriter;
	protected MimeType _mimeType;
	
	/**  ereditare questo metodo e scrivere un body che assegna una classe concreta e valorizzata al campo _fileWriter */
	protected abstract void initFileWriter();
	/** ereditare questo metodo per assegnare il campo protected _mimeType */
	protected abstract void initMimeType();
	/** ereditare questo metodo per assegnare il nome del file. Accedere attraverso il setter setFileNmae(String) */
	protected abstract void initFileName();

	@Override
	public String execute() throws Exception
	{   
		initFileWriter();
		initMimeType();
		initFileName();
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		_fileWriter.writeFile(output);
		file = new ByteArrayInputStream(output.toByteArray());
		output.close();
		
		// Salvo il cookie che serve al plugin javascript per dare il messaggio durante il download
		Cookie fileDownload = new Cookie("fileDownload", "true");
		fileDownload.setPath("/");
		servletResponse.addCookie(fileDownload);
		
        return "success"; 
	}

	//questo serve a struts per scrivere sulla response
	public InputStream getFile() {
		return file;
	}
	//questo serve a struts per scrivere sulla response
	public void setFile(InputStream file) {
		this.file = file;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}//EO KACT_FileDownload
