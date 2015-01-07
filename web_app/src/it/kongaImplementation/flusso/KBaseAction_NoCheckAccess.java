package it.kongaImplementation.flusso;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import it.konga.framework.flusso.KAbstractAction;

@InterceptorRef(value="kongaStackNoCheckAccess")
@Results({
	@Result(name="k_error", type="tiles", location="konga.tiles.error")
})
public class KBaseAction_NoCheckAccess extends KAbstractAction
{
	private static final long serialVersionUID = -430448344718798544L;

}
