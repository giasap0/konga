package it.konga.framework.flusso;


import java.util.ArrayList;

import javax.servlet.http.HttpSession;

/**
 * Oggetto che si occupa di gestire la sessione. Offre vari metodi di utility
 * @author Giampaolo Saporito
 */
@SuppressWarnings("unchecked")
public class KSessionManager
{
	private static String nomeListaChiavi = "listaChiaviSessione";
	private HttpSession session;
	
	public KSessionManager(HttpSession session)
	{
		this.session = session;
	}
	
	/**
	 * inserisci nuovo attributo in sessione
	 * @param key chiave da assegnare all'oggetto
	 * @param obj oggetto da mettere in sessione
	 */
	public void setAttribute(String key, Object obj)
	{
		ArrayList<String> elementiInSessione = (ArrayList<String>) session.getAttribute(nomeListaChiavi);
		if(elementiInSessione == null)
		{
			elementiInSessione = new ArrayList<String>();
			elementiInSessione.add(key);
		}
		else if(!elementiInSessione.contains(key))
			elementiInSessione.add(key);
		session.setAttribute(nomeListaChiavi, elementiInSessione);
		session.setAttribute(key, obj);
	}
	
	/**
	 * è in sessione un attributo con questa chiave?
	 * @param key chiave da cercare in sessione
	 * @return vero / falso
	 */
	public boolean isAttributeInSession(String key)
	{
		ArrayList<String> elementiInSessione = (ArrayList<String>) session.getAttribute(nomeListaChiavi);
		if(elementiInSessione == null)
			return false;
		return elementiInSessione.contains(key);
	}
	
	/**
	 * lista di tutte le chiavi degli attributi in sessione
	 */
	public ArrayList<String> getSessionAttributesKeys()
	{
		ArrayList<String> elementiInSessione =  (ArrayList<String>) session.getAttribute(nomeListaChiavi);
		if(elementiInSessione == null)
			return new ArrayList<String>();
		else
			return elementiInSessione;
	}
	
	/**
	 * pulisce la sessione eliminando tutti gli attributi
	 */
	public void cleanSession()
	{
		ArrayList<String> elementiInSessione =  (ArrayList<String>) session.getAttribute(nomeListaChiavi);
		if (elementiInSessione == null || elementiInSessione.size() <= 0)
			return;
		for (String key : elementiInSessione)
		{
			session.setAttribute(key, null);
		}
	}
	
	/**
	 * pulisce la sessione, ignorando però gli attributi con le chiavi passate in input
	 * @param exceptions chiavi degli oggetti che si vogliono lasciare in sessione
	 */
	public void cleanSession(String... exceptions)
	{
		ArrayList<String> elementiInSessione =  (ArrayList<String>) session.getAttribute(nomeListaChiavi);
		if (elementiInSessione == null || elementiInSessione.size() <= 0)
			return;
		boolean deleteKey = true;
		for (String key : elementiInSessione)
		{
			for(String exception : exceptions)
			{
				if(exception.equals(key))
				{
					deleteKey = false;
					break;
				}
			}
			if(deleteKey)
				session.setAttribute(key, null);
		}
	}
}//EO SessionManager

