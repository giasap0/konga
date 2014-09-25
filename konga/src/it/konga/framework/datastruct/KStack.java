package it.konga.framework.datastruct;


/**
 * Rappresenta uno stack (LIFO) : puoi fare push di un elemento in cima, e pop dell'ultimo elemento inserito
 * @author Giampaolo Saporito
 */
public class KStack<T>
{
	private KLinkedList<T> _list;
	
	public KStack() 					{_list = new KLinkedList<T>();}
	
	// ----------------- stack ----------------- \\
	
	/**
	 * inserisci nuovo elemento sulla pila
	 */
	public void push(T item)					{_list.append(item);}
	/**
	 * toglie l'elemento dalla pila e lo restituisce
	 * @return
	 */
	public T pop()								{T x = _list.last(); _list.removeLast(); return x;}
	
	// ----------------- accesso ----------------- \\
	
	/**
	 * accedi all'elemento in cima alla pila
	 */
	public T top()								{return _list.last();}
	public boolean isEmpty()					{return _list.isEmpty();}
	/**
	 * numero di elementi presenti
	 */
	public int size()							{return _list.size();}	
	
}
