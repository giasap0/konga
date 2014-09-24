package it.konga.framework.datastruct;

import java.util.Iterator;

/**
 * rappresenta uno stack (FIFO) : puoi accodare un elemento e recuperare il primo inserito<br>
 * First In - First Out
 * @author Giampaolo Saporito
 * @Date 24.09.20014
 * @param <T>
 */
public class KQueue<T> implements Iterable<T>
{
	private KLinkedList<T> _list;

	public KQueue(){_list = new KLinkedList<T>();}
	
	// ----------------- coda ----------------- \\
	
	public void enqueue(T item)						{_list.append(item);}
	public void dequeue()							{_list.removeFirst();}
	
	// ----------------- accesso ----------------- \\
	
	public T first()								{return _list.first();}
	public KListIterator<T> begin()					{return _list.begin();}
	@Override
	public Iterator<T> iterator() 					{return _list.iterator();}
	
	public boolean isEmpty()						{return _list.isEmpty();}
	public int size()								{return _list.size();}
	
}//EO KQueue
