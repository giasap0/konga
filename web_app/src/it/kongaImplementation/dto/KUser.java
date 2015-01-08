package it.kongaImplementation.dto;

import it.konga.framework.kObjects.KAbstract_User;
import it.kongaImplementation.enumerations.KAccessLevel;

public class KUser extends KAbstract_User
{
	private static final long serialVersionUID = 7671049049655851428L;
	
	public KUser()
	{
		this._accessLevel = KAccessLevel.INVALID;
	}
	
	public KAccessLevel getAccessLevel() 				{return (KAccessLevel) this._accessLevel;}
	public void setAccessLevel(KAccessLevel level)		{this._accessLevel = level;}
	
} //EO KUser
