package it.kongaImplementation.flusso;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import it.konga.framework.flusso.KAbstractACT_FileDownload;

@InterceptorRef(value="kongaStack")
@Results({
	@Result(name="k_error", type="tiles", location="konga.tiles.error"),
	@Result(name="k_no_session", type="tiles", location="konga.tiles.forbidden")
})
public abstract class KACT_FileDownload extends KAbstractACT_FileDownload
{
	private static final long serialVersionUID = 5233760598323085671L;

}
