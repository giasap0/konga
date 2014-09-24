package it.kongaImplementation.actions;

import it.konga.framework.flusso.KBaseAction;

import java.io.InputStream;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

@Results({
	@Result(name="success", type="stream")
})
public class ACT_GeneraTxt extends KBaseAction
{
	private static final long serialVersionUID = 7943170669760353965L;
	private InputStream fileInputStream;
	
	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	@Override
	@Action(value="/getExcel")
	public String execute() throws Exception
	{
		//in realtà scrivere il file di interesse
		return "success";
	}
}
