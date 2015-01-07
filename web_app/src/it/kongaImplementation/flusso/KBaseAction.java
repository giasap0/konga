package it.kongaImplementation.flusso;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import it.konga.framework.flusso.KAbstractAction;
import it.konga.framework.kObjects.KAbstract_User;
import it.kongaImplementation.dto.KUser;

@InterceptorRef(value="kongaStack")
@Results({
	@Result(name="k_error", type="tiles", location="konga.tiles.error"),
	@Result(name="k_no_session", type="tiles", location="konga.tiles.forbidden")
})
public abstract class KBaseAction extends KAbstractAction
{
	private static final long serialVersionUID = -4364274118192007158L;

	public KUser getUser()		{return (KUser) this.getSession().get(KAbstract_User.getSessionID());}
}
