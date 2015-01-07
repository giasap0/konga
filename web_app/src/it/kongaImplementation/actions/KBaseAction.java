package it.kongaImplementation.actions;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import it.konga.framework.flusso.KAbstractAction;

@InterceptorRef(value="kongaStack")
@Results({
	@Result(name="k_error", type="tiles", location="konga.tiles.error")	
})
public abstract class KBaseAction extends KAbstractAction
{
	private static final long serialVersionUID = -4364274118192007158L;

}
