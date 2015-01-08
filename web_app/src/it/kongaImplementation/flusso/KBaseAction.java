package it.kongaImplementation.flusso;

import it.konga.framework.flusso.KAbstractAction;
import it.konga.framework.kObjects.KAbstract_User;
import it.kongaImplementation.dto.KUser;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

@Results({
	@Result(name="k_error", type="tiles", location="konga.tiles.error"),
	@Result(name="k_no_session", type="tiles", location="konga.tiles.forbidden")
})
@ParentPackage(value="Kongadefault")
@InterceptorRef(value="kongaStack")
public abstract class KBaseAction extends KAbstractAction
{
	private static final long serialVersionUID = -4364274118192007158L;

	public KUser getUser()		{return (KUser) this.getSession().get(KAbstract_User.getSessionID());}
}
