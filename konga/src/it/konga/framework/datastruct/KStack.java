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
	
	public void push(T item)					{_list.append(item);}
	public void pop()							{_list.removeLast();}
	
	// ----------------- accesso ----------------- \\
	
	public T top()								{return _list.last();}
	public boolean isEmpty()					{return _list.isEmpty();}
	public int size()							{return _list.size();}	
	
}
