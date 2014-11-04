package it.konga.framework.datastruct;

import java.util.List;


/**
 * Container ottimizzato per contenere stringhe, con metodi di utility quali 'join' e 'containsIgnoreCase'
 * @author Giampaolo Saporito
 * @date 25.09.2014
 */
public class KStringList extends KLinkedList<String>
{
	private static final long serialVersionUID = -5588691931408501885L;



	// ----------------------------------------------------------------------------------- costruttori ----------------------------------------------------------------------------------- \\
	
	public KStringList() 					{super();}
	public KStringList(String[] array)		{super(array);}
	
	// ----------------------------------------------------------------------------------- metodi di factory ----------------------------------------------------------------------------------- \\
	
	/**
	 * costruisce una nuova StringList a partire da una qualsiasi lista di stringhe.<br>
	 * veloce a causa del fatto che in java le stringhe non uniche
	 * @param list lista dalla quale prendere gli elementi
	 * @return nuova StringList
	 */
	public static KStringList fromList(List<String> list)
	{
		KStringList ret = new KStringList();
		for (String string : list) {
			ret.append(string);
		}
		return ret;
	}
	
	// ----------------------------------------------------------------------------------- metodi publici ----------------------------------------------------------------------------------- \\
	
	/**
	 * una stringa concatenazione di tutte le stringhe in lista, separate da uno spazio
	 */
	public String join()
	{
		return join(" ");
	}
	/**
	 * una stringa concatenazione di tutte le stringhe in lista, separate da un separatore
	 */
	public String join(String separator)
	{
		if(separator == null)
			separator = "";
		StringBuilder str = new StringBuilder();
		int size = size();
		if(size <= 0)
			return "";
		for (String s : this) {
			str.append(s);
			--size;
			if(size>0)
				str.append(separator);
		}
		return str.toString();
	}
	
	/**
	 * true se contiene una stringa uguale a quella passata in input. Case Sensitive.<br>
	 * Se input == null torna false
	 */
	@Override
	public boolean contains(String item)
	{
		return super.contains(item);
	}
	
	/**
	 * true se contiene una stringa uguale a quella passata in input, ignorando il case<br>
	 * Se input == null torna false;
	 */
	public boolean containsIgnoreCase(String item)
	{
		if(item==null)
			return false;
		if(size() <= 0)
			return false;
		for (String x : this)
		{
			if( x == null)
				continue;
			if(x.equalsIgnoreCase(item) )
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString()
	{
		if(size() == 0)
			return "";
		StringBuilder str = new StringBuilder();
		for (String e : this) {
			str.append(e).append(" ");
		}
		return str.toString();
	}
	
	// ----------------------------------------------------------------------------------- publici statici ----------------------------------------------------------------------------------- \\
	
	/**
	 * Separa la stringa in input ove incontra 'separator' e inserisce i risultati in una lista
	 * @param str stringa che si vuole separare
	 * @param separator usato per separare le stringhe. Nel caso in cui il separatore sia stringa vuota o null torna una Lista che contiene solo la stringa di input
	 * @return lista contenente le stringhe ottenute dall'input
	 */
	public static KStringList split(String str, String separator)
	{
		if(str == null)
			return null;
		KStringList list = new KStringList();
		if(separator == null || separator.equals("") || str.trim().equals(""))
		{
			list.append(str);
			return list;
		}
		int begin = 0;
		int end = 1;
		int delta = separator.length();
		while(end >= 0 && begin < str.length())
		{
			end = str.indexOf(separator, begin);
			if(end==-1)
				list.append(str.substring(begin));
			else
				list.append( str.substring(begin, end) );
			begin = end + delta;
		}		
		
		return list;
	}
	
}//EO StringList
