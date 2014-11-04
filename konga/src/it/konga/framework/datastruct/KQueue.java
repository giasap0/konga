package it.konga.framework.datastruct;

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
	
	/** metti in fondo alla coda un nuovo item */
	public void enqueue(T item)						{_list.append(item);}
	/** toglie dalla coda il prossimo oggetto e lo restituisce */
	public T dequeue()								{T x = _list.first(); _list.removeFirst(); return x;}
	
	// ----------------- accesso ----------------- \\
	
	/** accedi al prossimo oggetto in coda */
	public T first()								{return _list.first();}
	@Override
	public KListIterator<T> iterator() 				{return _list.iterator();}
	
	public boolean isEmpty()						{return _list.isEmpty();}
	/** numero di elementi in coda */
	public int size()								{return _list.size();}
	
}//EO KQueue