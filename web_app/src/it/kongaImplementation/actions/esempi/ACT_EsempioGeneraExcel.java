package it.kongaImplementation.actions.esempi;

import it.konga.framework.interfaces.KFileWriter;
import it.konga.framework.kObjects.MimeType;
import it.konga.framework.util.excel.KBasic_ExcelWriter;
import it.kongaImplementation.flusso.KACT_FileDownload;

import org.apache.struts2.convention.annotation.Action;

public class ACT_EsempioGeneraExcel extends KACT_FileDownload
{
	private static final long serialVersionUID = -1279456937127645656L;

	//questo serve solo per mappare la action
	@Action(value="/getExcel_esempio")
	public String getExcel() throws Exception
	{
		return super.execute();
	}
	
	@Override
	protected MimeType initMimeType() 		{ return MimeType.XLS; }
	@Override
	protected String initFileName() 		{ return "ciao.xls"; }
	@Override
	protected KFileWriter initFileWriter()
	{
		final String[][] matrice = {{"a","b","c"},{"1","2","3"}};
		return new KBasic_ExcelWriter(matrice);
	}	
	
} //EO ACT_GeneraExcel
