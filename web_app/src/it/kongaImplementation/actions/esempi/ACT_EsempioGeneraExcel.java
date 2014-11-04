package it.kongaImplementation.actions.esempi;

import org.apache.struts2.convention.annotation.Action;

import it.konga.framework.flusso.KACT_FileDownload;
import it.konga.framework.kObjects.MimeType;
import it.konga.framework.util.excel.KBasic_ExcelWriter;

public class ACT_EsempioGeneraExcel extends KACT_FileDownload
{
	private static final long serialVersionUID = -1279456937127645656L;

	@Override
	protected void initFileWriter()
	{
		final String[][] matrice = {{"a","b","c"},{"1","2","3"}};
		_fileWriter = new KBasic_ExcelWriter(matrice);
	}
	
	@Action(value="/getExcel_esempio")
	public String getExcel() throws Exception
	{
		return super.execute();
	}

	@Override
	protected void initMimeType() {
		_mimeType = MimeType.XLS;
	}

	@Override
	protected void initFileName() {
		setFileName("ciao.xls");
	}
	
} //EO ACT_GeneraExcel
