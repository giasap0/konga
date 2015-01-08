package it.konga.framework.kObjects;

import it.konga.framework.interfaces.IAccessLevel;

public abstract class KAbstract_User extends KAbstract_Dto
{
	private static final long serialVersionUID = 4792214461438742785L;

	private static long counter = 0;
	private long uniqueId;
	
	protected IAccessLevel _accessLevel;

	public KAbstract_User()
	{
		uniqueId = counter++;
	}
	
	public static String getSessionID()					{ return "k_user_session"; }
	public long getUniqueId()							{return uniqueId;}
	
}//EO KAbstract_User
