package it.kongaImplementation.actions;

import it.konga.framework.flusso.KBaseAction;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;


@Results({
	@Result(name="go_login", type="tiles", location="konga.tiles.login"),
	@Result(name="go_home", type="tiles", location="konga.tiles.home")
})
public class ACT_Login extends KBaseAction
{
	private static final long serialVersionUID = 7091051632682919876L;

	@Override
	@Action(value="/login")
	public String execute() throws Exception
	{
		return "go_login";
	}
	
	@Action(value="/home")
	public String home() throws Exception
	{
		return "go_home";
	}
	
}//EO ACT_Login
