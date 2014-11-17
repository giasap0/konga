package it.konga.framework.interfaces;

/** Rappresenta in modo astratto i livelli di accesso per la profilazione. */
public interface IAccessLevel
{
	public String getDescription();
	public int getId();
	/** true se questo livello di accesso 'contiene' tutti i permessi di other */
	public boolean includes(IAccessLevel other); 
	public boolean isValid();
}
