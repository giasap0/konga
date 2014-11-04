package it.konga.framework.datastruct;

import it.konga.framework.interfaces.Ptr_Function_Compare;

import java.util.ArrayList;


// ATTENZIONE: non utilizzo l'indice 0 \\

/**
 * Coda Prioritaria
 * @author Giampaolo Saporito
 */
public class KPriorityQueue<T>
{
	// ------------------------------------------------------------------------- fields ------------------------------------------------------------------------- \\
	protected ArrayList<T> _v;
	protected Ptr_Function_Compare<T> _compare;
	
	// ------------------------------------------------------------------------- costruttori ------------------------------------------------------------------------- \\
	/**
	 * costruisci una coda prioritaria
	 * @param compare funzione di comparazione per il tipo specificato
	 */
	public KPriorityQueue(Ptr_Function_Compare<T> compare)							{_v = new ArrayList<T>(2); _v.add(null); _compare = compare;}
	/**
	 * costruisci una coda prioritaria con una capacità iniziale-<br>(size==0)
	 * @param compare funzione di comparazione per il tipo specificato
	 */
	public KPriorityQueue(Ptr_Function_Compare<T> compare, int initialCapacity)		{_v = new ArrayList<T>(initialCapacity); _v.add(null); _compare = compare;}
	public KPriorityQueue(KPriorityQueue<T> other)									{_v = new ArrayList<T>(other._v); _compare = other._compare; }

	// ------------------------------------------------------------------------- metodi publici ------------------------------------------------------------------------- \\
		
	public int size() 											{return _v.size()-1;}
	public T first()											{return _v.get(1);}
	public boolean isEmpty()									{if(_v.size()>1) return false; return true;}
	/** clear elements, not function pointer */
	public void clear()											{_v = new ArrayList<T>(1); _v.add(null);}
	/** clear elements and change function pointer */
	public void clear( Ptr_Function_Compare<T> compare)			{_v = new ArrayList<T>(1); _v.add(null); _compare = compare;}
	/** enqueue element and return this */
	public KPriorityQueue<T> append(T x)						{enqueue(x);return this;}
	/** insert into queue */
	public void enqueue(T data)									{_v.add(data); walkUp( _v.size()-1);}
	/** removing the root node of the heap */
	public T dequeue()
	{
		int count = _v.size()-1;
		T temp = null;
		if(count >= 1)
		{
			temp = _v.get(1);			//save for return
			_v.set(1, _v.get(count));	//root == last
			walkDown( 1 );				//re-order
			_v.remove(count);			//remove last
		}
		return temp;
	}
	
	// ------------------------------------------------------------------------- metodi privati ------------------------------------------------------------------------- \\
	private void walkUp(int i)
	{
		int parent = i/2;
		int child = i;
		T temp = _v.get(child);
		while(parent>0)
		{
			if( _compare.compare(temp, _v.get(parent)) > 0)
			{
				_v.set(child,  _v.get(parent) );
				child = parent;
				parent /= 2;
			}
			else break;
		}//EO while (parent valid)
		_v.set(child, temp);
	}
	private void walkDown(int i)
	{
		int parent = i;
		int child = i*2;
		T temp = _v.get(parent);
		int count = _v.size()-1;
		while(child<count)
		{
			if(child < count-1)
			{
				if ( _compare.compare(_v.get(child), _v.get(child+1)) < 0)
					++child;
			}
			if( _compare.compare(temp, _v.get(child)) < 0)
			{
				_v.set(parent, _v.get(child));
				parent = child;
				child *= 2;
			}
			else 
				break;
		}
		_v.set(parent, temp);
	}
	
	// ------------------------------------------------------------------------- overrides di Object ------------------------------------------------------------------------- \\
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = prime * 1 + ((_compare == null) ? 0 : _compare.hashCode());
		result = prime * result + ((_v == null) ? 0 : _v.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof KPriorityQueue)) {
			return false;
		}
		KPriorityQueue<?> other = (KPriorityQueue<?>) obj;
		if (_compare == null) {
			if (other._compare != null) {
				return false;
			}
		} else if (!_compare.equals(other._compare)) {
			return false;
		}
		if (_v == null) {
			if (other._v != null) {
				return false;
			}
		} else if (!_v.equals(other._v)) {
			return false;
		}
		return true;
	}
	
	
} //EO class KPriorityQueue
