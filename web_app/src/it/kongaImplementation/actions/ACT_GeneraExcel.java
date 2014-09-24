package it.kongaImplementation.actions;

import org.apache.struts2.convention.annotation.Action;

import it.konga.framework.flusso.KACT_FileDownload;
import it.konga.framework.util.excel.KBasic_ExcelWriter;

public class ACT_GeneraExcel extends KACT_FileDownload
{

	private static final long serialVersionUID = -1279456937127645656L;

	@Override
	protected void initFileWriter()
	{
		final String[][] matrice = {{"a","b","c"},{"1","2","3"}};
		_fileWriter = new KBasic_ExcelWriter(matrice);
	}
	
	@Action(value="/getExcel")
	public String getExcel() throws Exception
	{
		return super.execute();
	}

} //EO ACT_GeneraExcel
