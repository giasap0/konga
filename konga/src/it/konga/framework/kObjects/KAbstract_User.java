package it.konga.framework.kObjects;

import java.io.InvalidObjectException;

import it.konga.framework.interfaces.IAccessLevel;

public class KAbstract_User extends KAbstract_Dto
{
	private static final long serialVersionUID = 4792214461438742785L;

	protected IAccessLevel _accessLevel;
	
	@Override
	public void validateObject() throws InvalidObjectException
	{
		if( getId() < 0) throw new InvalidObjectException("L'id deve essere positivo");	
		if( !_accessLevel.isValid() ) throw new InvalidObjectException("Livello di accesso non valido");	
	}
	
}//EO KAbstract_User
