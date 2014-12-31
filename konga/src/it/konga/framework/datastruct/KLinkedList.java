package it.konga.framework.datastruct;

import it.konga.framework.interfaces.KList;
import it.konga.framework.interfaces.Ptr_Function_Compare;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

/**
 * Lista semplicemente linkata. Usare KListIterator per iterarla.
 * @author Giampaolo Saporito
 * @Date 05/09/2014
 */
public class KLinkedList<T> implements Serializable,  KList<T>, Cloneable
{
	private static final long serialVersionUID = -4921861046503460358L;

	// *************************************************************************************************
	// 											nodo della lista
	// *************************************************************************************************

	/**
	 * un nodo della lista
	 * @author Giampaolo Saporito
	 */
	protected class KListNode implements Serializable
	{
		private static final long serialVersionUID = 2205660939853734004L;
		private T _data;
		private KListNode _pNext;

		public KListNode()	{}

		public void clear()
		{
			_data = null;
			_pNext = null;
		}

		public void insertAfter(T data)
		{
			KListNode newNode = new KListNode();
			newNode._data = data;
			newNode._pNext = _pNext;
			_pNext = newNode;
		}
		
		public T getData()						{return _data;}
		public void setData(T newValue)			{_data=newValue;}
		public KListNode next()					{return _pNext;}

		@Override
		public int hashCode()
		{
			final int prime = 11;
			int result = prime  + getOuterType().hashCode();
			result = prime * result + ((_data == null) ? 0 : _data.hashCode());
			result = prime * result+ ((_pNext == null) ? 0 : _pNext.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj)
		{
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof KLinkedList.KListNode))
				return false;
			@SuppressWarnings("unchecked")
			KListNode other = (KListNode) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (_data == null)
			{
				if (other._data != null)
					return false;
			} else if (!_data.equals(other._data))
				return false;
			if (_pNext == null)
			{
				if (other._pNext != null)
					return false;
			} else if (!_pNext.equals(other._pNext))
				return false;
			return true;
		}

		private KLinkedList<T> getOuterType()
		{
			return KLinkedList.this;
		}

		@SuppressWarnings("unchecked")
		private void readObject(java.io.ObjectInputStream stream) throws java.io.IOException, ClassNotFoundException
		{
			stream.defaultReadObject();
			_data = (T) stream.readObject();
			_pNext = (KListNode) stream.readObject();
		}

		private void writeObject(java.io.ObjectOutputStream stream) throws java.io.IOException
		{
			stream.defaultWriteObject();
			stream.writeObject( _data );
			stream.writeObject( _pNext );
		}
	};

	// ----------------------------------------------------------------------------------- fields ----------------------------------------------------------------------------------- \\
	protected KListNode _head;
	protected KListNode _tail;
	private int _count;

	// ----------------------------------------------------------------------------------- costruttori ----------------------------------------------------------------------------------- \\

	public KLinkedList()
	{
		_head=_tail=null;
		_count = 0;
	}

	public KLinkedList(KLinkedList<T> other)
	{
		_head = _tail = null;
		_count = 0 ;
		if ( other.isEmpty() )
			return;
		KListNode ptr = other._head;
		while ( !ptr.equals(other._tail ) )
		{
			append(ptr._data);
			ptr = ptr._pNext;
		}
		append( other._tail._data);
	}

	public KLinkedList(T[] array)
	{
		for (T t : array) {
			this.append(t);
		}
	}
	public KLinkedList(Collection<? extends T> list)
	{
		if(list == null || list.size() == 0)
		{
			_count = 0;
			_head=_tail=null;
		}
		else
		{
			append(list);
		}
		
	}

	// ----------------------------------------------------------------------------------- metodi publici ----------------------------------------------------------------------------------- \\

	@Override
	public boolean isEmpty()						
	{
		if(_head==null) return true; return false;
	}
	/** numero di elementi nella lista */
	@Override
	public int size() 							{ return _count;}
	/** primo elemento della lista */
	@Override
	public T first()							{ return _head._data; }
	/** ultimo elemento della lista */
	@Override
	public T last()								{ return _tail._data;}
	@Override
	public KListIterator<T> begin()				{ return new KListIterator<T>(this,_head); }
	@Override
	public KListIterator<T> end()				{ return new KListIterator<T>(this,_tail); }
	@Override
	public KListIterator<T> iterator()			{ return  begin(); }
	/** Torna l'iteratore che punta all'eleemento con indice indx. L'indice parte da 0
	 * @param indx parte da 0
	 * @return iteratore
	 */
	@Override
	public KListIterator<T> iterator(int indx)  
	{
		if(indx<0 || indx >= this.size())
			throw new IndexOutOfBoundsException("KLinkedList::iterator(int) - index out of bounds. index == "+indx);
		KListIterator<T> itr = begin();
		if(indx==0)
			return itr;
		for(int i=0; i < indx && itr.hasNext() ; i++)
			itr.next();
		return itr;
	}

	/** Fa una deep copy dell'oggetto. Implementato tramite serializzazione */
	@SuppressWarnings("unchecked")
	@Override
	public KLinkedList<T> clone() throws CloneNotSupportedException
	{
		try
		{
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
			objOut.writeObject(this);

			ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(byteIn);

			return (KLinkedList<T>) ois.readObject();
		}
		catch(IOException | ClassNotFoundException e)
		{
			throw new CloneNotSupportedException();
		}
	}

	/** Appende un riferimento all'oggetto<br>return this
	 * @param data
	 */
	@Override
	public KLinkedList<T> append(T data)
	{
		if(_head == null)
		{
			_head = _tail= new KListNode();
			_head.setData(data);
		}
		else
		{
			_tail.insertAfter(data);
			_tail = _tail.next();
		}
		_count++;
		return this;
	}

	/** Appende riferimenti agli elementi dell'altra lista<br> return this
	 * @param other altra lista
	 */
	public KList<T> append( KList<? extends T> other)
	{
		if(_head == null)
		{
			this.copy(other);
		}
		else
		{
			for (T t : other) {
				this.append(t);
			}
		}
		return this;
	}
	
	/** Appende riferimenti agli elementi dell'altra lista<br> return this
	 * @param other altra lista
	 */
	@Override
	public KLinkedList<T> append (Collection<? extends T> list)
	{
		if( list == null || list.size() == 0)
			return this;
		Iterator<? extends T> itr = list.iterator();
		while( itr.hasNext() )
		{
			this.append( itr.next() );
		}
		return this;
	}
	/**
	 * Non fa una copia degli elementi ma punta agli stessi
	 * @param other altra lista
	 */
	public void copy(KList<? extends T> other)
	{
		_head = _tail = null;
		for (T element : other)
		{
			this.append(element);
		}
	}

	/**
	 * Inserisce un elemento all'inizio della lista
	 * @param data valore da attribuire al nodo
	 */
	@Override
	public void prepend( T data)
	{
		KListNode newNode = new KListNode();
		newNode._data = data;
		newNode._pNext = _head;
		_head = newNode;
		if(_tail == null)
			_tail = _head;
		_count++;
	}

	/** Inserts an item AFTER the current iterator (append if iterator is invalid)
	 * @param itr iteratore posizione
	 * @param value valore da inserire
	 */
	@Override
	public void insert( Iterator<T> itr, T value)
	{
		if(!(itr instanceof KListIterator<?>))
			return;
		KListIterator<T> it = (KListIterator<T>)itr;
		if( ! it._pList.equals(this) ) 		//non è iteratore di questa lista
			throw new IllegalArgumentException("KLinkedList::insert(itr, value) - invalid iterator");
		if( it._pNode != null)					//iteratore valido
		{
			it._pNode.insertAfter(value);
			if( it._pNode.equals(_tail) )		//update tail
			{
				_tail = it._pNode._pNext; 
			}
			_count++;
		}
		else	//iteratore di questa lista ma non punta ad un nodo valido
		{
			append(value);
		}
	}

	/**
	 * Rimuove l'elemento puntato dall'iteratore.<br>
	 * L'iteratore punterà al nodo successivo.
	 * @param itr iteratore della lista
	 */
	public T remove(Iterator<T> it)
	{
		if(!(it instanceof KListIterator<?>))
			return null;
		KListIterator<T> itr = (KListIterator<T>)it;
		KListNode node = _head;
		if( ! itr._pList.equals(this) ) 			//non è di questa lista
			throw new IllegalArgumentException("KLinkedList::remove(itr) - invalid iterator");
		if(itr._pNode == null )						//iterator is invalid
			return null;
		T temp = null;
		if(itr._pNode.equals(_head) )				//iterator is head
		{
			itr.next();
			return removeFirst();
		}
		else
		{
			//vai al nodo prima di quello da cancellare
			while( ! node.next().equals( itr._pNode ))		
				node = node.next();
			itr.next();						//muovi l'iterator fino al nodo da cancellare
			temp = itr.getData();
			if( node.next().equals(_tail))
			{
				_tail= node;						//update tail
			}
			node._pNext = itr._pNode;				//re-link the list
			_count--;
		}
		return temp;
	}
	
	@Override
	public T removeFirst()
	{
		KListNode node = null;
		T temp = null;
		if( _head != null )
		{
			node = _head.next();
			temp = _head._data;
			_head = node;
			if(_head == null)		//la lista era di un solo elemento
				_tail= null;
			_count--;
		}
		return temp;
	}
	
	@Override
	public T removeLast()
	{
		KListNode node = _head;
		if( _head == null)				//lista vuota
			return null;
		T temp = null;
		if(_head == _tail)				//only 1 element
		{
			temp = _tail._data;
			_head = _tail = null;
		}
		else
		{
			while( !node.next().equals(_tail))	//go to one before tail
			{
				node = node.next();
			}
			temp = _tail._data;
			_tail= node;					//reassign tail
			node._pNext = null;
		}
		_count--;
		return temp;
	}
	
	/**
	 * Rimuove tutti gli elementi con indce incluso nell'intervallo passato in input.<br>
	 * I limiti sono INCLUSIVI<br>
	 * Gli indici partono da 0 e non possono assumere lo stesso valore<br>
	 * ToIndex deve essere > fromIndex
	 * @param fromIndex primo indice da cancellare. Parte da 0
	 * @param toIndex ultimo indice da cancellare. Parte da 0
	 */
	@Override
	public void removeRange(int fromIndex, int toIndex) 
	{
		if( fromIndex<0 || fromIndex>= this.size() || toIndex>= this.size() || toIndex<= fromIndex)
		{
			throw new IndexOutOfBoundsException("KLinkedList::removeRange(int,int) - index out of bounds. size == "+ this._count +" , fromIndex == "+fromIndex+" , toIndex == "+toIndex);
		}
		KLinkedList<T>.KListNode ptrFrom = nodeAtIndex(fromIndex-1);
		KLinkedList<T>.KListNode ptrNext = ptrFrom._pNext;
		for(int i=fromIndex; i <= toIndex; i++)
		{
			KLinkedList<T>.KListNode temp = ptrNext;
			ptrNext = ptrNext._pNext;
			temp.clear();
			--_count;
		}
		if(ptrNext.equals(_tail) )
		{
			_tail = ptrNext;
		}
		ptrFrom._pNext = ptrNext;
	}
	
	@Override
	public void clear()
	{
		KListNode itr = _head;
		KListNode next = null;
		while( itr != null )
		{
			next = itr.next();
			itr.clear();
			itr = next;
		}
		_head = _tail = null;
		_count=0;
	}

	public void replace(Iterator<T> it, T newValue)
	{
		if(!(it instanceof KListIterator<?>))
			return;
		KListIterator<T> itr = (KListIterator<T>)it;
		if( ! itr._pList.equals(this) )
			throw new IllegalArgumentException("KLinkedList::replace(itr, value) - invalid iterator");
		if(itr._pNode == null)
			return;
		itr._pNode._data = newValue;
	}

	/** Non agisce su elementi null, se l'input == null torna false */
	@Override
	public boolean contains(T item)
	{
		if(item==null)
			return false;
		if(size() <= 0)
			return false;
		for (T x : this)
		{
			if( x == null)
				continue;
			if(x.equals(item))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Torna un iteratore che punta al primo oggetto.equals(item).<br>
	 * Se nessun oggetto è presente con questa caratteristica torna null
	 * @param item oggetto con cui fare equals
	 * @return iteratore nella posizione
	 */
	public KListIterator<T> iteratorOf(T item)
	{
		if(item == null)
			throw new NullPointerException("KLinkedList::iteratorOf(T) - parametro in input null");
		KListIterator<T> itr = begin();
		while(itr.hasNext())
		{
			T x = itr.next();
			if( x == null)
				continue;
			if( x.equals(item) )
			{
				return itr;
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public T[] toArray()
	{
		Object[] array = new Object[_count];
		KListIterator<T> itr = this.iterator();
		int i =0;
		while(itr.hasNext())
			array[i++] = itr.next();	
		return (T[]) array;
	}

	// ------------------------------------------------------------------------------------------ Private ------------------------------------------------------------------------------------------ \\

	private KLinkedList<T>.KListNode nodeAtIndex(int index)
	{
		KLinkedList<T>.KListNode ptr = _head;
		for(int i=0; i < index; i++)
		{
			ptr = ptr._pNext;
		}
		return ptr;
	}

	@SuppressWarnings("unchecked")
	private void readObject(java.io.ObjectInputStream stream) throws java.io.IOException, ClassNotFoundException
	{
		stream.defaultReadObject();
		_count = stream.readInt();
		_head = (KListNode) stream.readObject();
		_tail = (KListNode) stream.readObject();
	}

	private void writeObject(java.io.ObjectOutputStream stream) throws java.io.IOException
	{
		stream.defaultWriteObject();
		stream.writeInt(_count);
		stream.writeObject( _head );
		stream.writeObject( _tail );
	}

	@Override
	public int count(T value) {
		if(value == null)
			return 0;
		int count = 0;
		for (T x : this) {
			if(value.equals(x))
				++count;
		}
		return count;
	}

	@Override
	public KList<T> fill(T value)
	{
		if( _head == null)				//lista vuota
			return this;
		if(_head == _tail)				//only 1 element
		{
			_tail._data = value;
			_head = _tail;
		}
		KLinkedList<T>.KListNode ptr = _head;
		while(ptr._pNext != null)
		{
			ptr._data = value;
		}
		return this;
	}

	/** Ordina l'array in modo crescente (dal più piccolo al più grande) utilizzando la funzione compare in input.<br> */
	@Override
	public void sort(Ptr_Function_Compare<T> p_compare)
	{
		if(_head == null)					//lista vuota
			return;
		if(_head == _tail)					//only 1 element
			return;
		KLinkedList<T>.KListNode i = _head;
		while(i._pNext != null)
		{
			KLinkedList<T>.KListNode j = _head;
			while( j._pNext != null)
			{
				T temp = j._data;
				T prox = j._pNext._data;
				if( p_compare.compare(temp, prox)>0)
				{
					j._data = j._pNext._data;
					j._pNext._data = temp;
				}
			}
			j = j._pNext;
		}
		i = i._pNext;
	}
 
}//EO KLinkedList
