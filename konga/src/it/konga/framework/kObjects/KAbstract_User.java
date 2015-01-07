package it.konga.framework.kObjects;

import it.konga.framework.interfaces.IAccessLevel;

public class KAbstract_User extends KAbstract_Dto
{
	private static final long serialVersionUID = 4792214461438742785L;

	protected IAccessLevel _accessLevel;

	public static String getSessionID()
	{
		return "k_user_session";
	}
	
}//EO KAbstract_User
