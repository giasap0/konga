package it.kongaImplementation.actions;

import it.kongaImplementation.dto.KUser;
import it.kongaImplementation.enumerations.KAccessLevel;
import it.kongaImplementation.flusso.KBaseAction_NoCheckAccess;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;


@Results({
	@Result(name="go_login", type="tiles", location="konga.tiles.login")
})
public class ACT_Login extends KBaseAction_NoCheckAccess
{
	private static final long serialVersionUID = 7091051632682919876L;

	@Override
	@Action(value="/login")
	public String execute() throws Exception
	{
		//in teoria qui arrivano dati da un form 
		
		//recuperare in base ai dati in input l'utente dal database
		KUser user = new KUser();
		user.setAccessLevel(KAccessLevel.NORMALE);
		getSession().put(KUser.getSessionID(), user);
		return "go_login";
	}
	
}//EO ACT_Login
