package it.konga.framework.flusso;


import it.konga.framework.datastruct.KLinkedList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;

/**
 * Oggetto che si occupa di gestire la sessione. Offre vari metodi di utility
 *  @author Giampaolo Saporito
 * @Date 05/09/2014
 */

public class KSessionManager
{
	private ActionContext context;
	
	public KSessionManager( ActionContext context )
	{
		this.context = context;
	}
	
	/**
	 * inserisci nuovo attributo in sessione
	 * @param key chiave da assegnare all'oggetto
	 * @param obj oggetto da mettere in sessione
	 */
	public void putAttribute(String key, Object obj)
	{
		context.getSession().put(key, obj);
	}
	
	/**
	 * è in sessione un attributo con questa chiave?
	 * @param key chiave da cercare in sessione
	 * @return vero / falso
	 */
	public boolean isAttributeInSession(String key)
	{
		return context.getSession().containsKey(key);
	}
	
	/**
	 * lista di tutte le chiavi degli attributi in sessione
	 */
	public ArrayList<String> getSessionAttributesKeys()
	{
		ArrayList<String> ret = new ArrayList<String>( context.getSession().keySet() );
		return ret;
	}
	
	/**
	 * pulisce la sessione eliminando tutti gli attributi
	 */
	public void cleanSession()
	{
		context.getSession().clear();
	}
	
	/**
	 * pulisce la sessione, ignorando però gli attributi con le chiavi passate in input
	 * @param exceptions chiavi degli oggetti che si vogliono lasciare in sessione
	 */
	public void cleanSession(String... exceptions)
	{
		Map<String, Object> session = context.getSession();
		if(session == null || session.isEmpty() )
			return;
		Iterator<String> itr = session.keySet().iterator();	

		KLinkedList<String> daLasciare = new KLinkedList<String>( exceptions  );
		
		while( itr.hasNext() )
		{
			boolean delete = true;
			String check = itr.next();
			Iterator<String> listItr = daLasciare.iterator();
			while(listItr.hasNext())
			{
				if( check.equals(listItr.next()) )
				{
					delete = false;
					listItr.remove();
					break;
				}
			}
			if(delete)
				itr.remove();
		}
	}
}//EO SessionManager

