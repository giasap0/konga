package it.kongaImplementation.enumerations;

import it.konga.framework.interfaces.IAccessLevel;

public enum KAccessLevel implements IAccessLevel
{
	INVALID("",-1),
	NORMALE("normale",1),
	ADMIN("admin",2)
	;

	private final String description;
	private final int id;
	
	KAccessLevel(String descriptio, int id)
	{
		this.description = descriptio;
		this.id = id;
	}
	
	@Override
	public String getDescription() 				{ return description; }
	@Override
	public int getId() 							{ return id; }
	@Override
	public boolean isValid()					{ return id != -1; }
	
	@Override
	public boolean includes(IAccessLevel other) {
		if(id>= other.getId())
			return true;
		return false;
	}

}
