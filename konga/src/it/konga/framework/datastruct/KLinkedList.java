package it.konga.framework.datastruct;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;

/**
 * lista semplicemente linkata. Usare ZListIterator per iterarla.
 * @author Giampaolo Saporito
 */
public class KLinkedList<T> implements Serializable, Iterable<T>
{
	private static final long serialVersionUID = -4921861046503460358L;

	// *************************************************************************************************
	// 											nodo della lista
	// *************************************************************************************************
	
	/**
	 * un nodo della lista
	 * @author Giampaolo Saporito
	 */
	protected class ZListNode implements Serializable
	{
		private static final long serialVersionUID = 2205660939853734004L;
		private T _data;
		private ZListNode _pNext;
		
		public ZListNode()	{}

		public void clear()
		{
			_data = null;
			_pNext = null;
		}
		
		public void insertAfter(T data)
		{
			ZListNode newNode = new ZListNode();
			newNode._data = data;
			newNode._pNext = _pNext;
			_pNext = newNode;
		}
		public T getData()						{return _data;}
		public void setData(T newValue)			{_data=newValue;}
		public ZListNode next()					{return _pNext;}

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
			if (!(obj instanceof KLinkedList.ZListNode))
				return false;
			@SuppressWarnings("unchecked")
			ZListNode other = (ZListNode) obj;
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
			_pNext = (ZListNode) stream.readObject();
		}
		
		private void writeObject(java.io.ObjectOutputStream stream) throws java.io.IOException
		{
			stream.defaultWriteObject();
			stream.writeObject( _data );
			stream.writeObject( _pNext );
		}
	};
	
	protected ZListNode _head;
	protected ZListNode _tail;
	private int _count;
	
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
		ZListNode ptr = other._head;
		while ( !ptr.equals(other._tail ) )
		{
			append(ptr._data);
			ptr = ptr._pNext;
		}
		append( other._tail._data);
	}

	public boolean isEmpty()						
	{
		if(_head==null) return true; return false;
	}
	public int size() 							{ return _count;}
	public T first()							{ return _head._data; }
	public T last()								{ return _tail._data;}
	public KListIterator<T> begin()				{ return new KListIterator<T>(this,_head); }
	public KListIterator<T> end()				{ return new KListIterator<T>(this,_tail); }
	@Override
	public Iterator<T> iterator()				{ return  begin(); }
	
	/**
	 * Fa una deep copy dell'oggetto. Implementato tramite serializzazione
	 */
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
		} catch (ClassNotFoundException | IOException e)
		{
			throw new CloneNotSupportedException();
		}
	}
	
	/**
	 * appende un riferimento all'oggetto
	 * @param data
	 */
	public void append(T data)
	{
		if(_head == null)
		{
			_head = _tail= new ZListNode();
			_head.setData(data);
		}
		else
		{
			_tail.insertAfter(data);
			_tail = _tail.next();
		}
		_count++;
	}
	/**
	 * appende riferimenti agli elementi dell'altra lista
	 * @param other altra lista
	 */
	public void append( KLinkedList<T> other)
	{
		if(_head == null)
		{
			this.copy(other);
		}

		else
		{
			ZListNode ptr = other._head;
			while( ptr != other._tail )
			{
				append( ptr._data);
				ptr = ptr._pNext;
			}
			append( other._tail._data );
		}
	}
	/**
	 * non fa una copia degli elementi ma punta agli stessi
	 * @param other altra lista
	 */
	public void copy(KLinkedList<T> other)
	{
		_head = _tail = null;
		for (T element : other)
		{
			this.append(element);
		}
	}
	
	/**
	 * inserisce un elemento all'inizio della lista
	 * @param data valore da attribuire al nodo
	 */
	void prepend( T data)
	{
		ZListNode newNode = new ZListNode();
		newNode._data = data;
		newNode._pNext = _head;
		_head = newNode;
		if(_tail == null)
			_tail = _head;
		_count++;
	}
	
	//inserts an item AFTER the current iterator (append if iterator is invalid)
	void insert(  KListIterator<T> itr, T value)
	{
		if( ! itr._pList.equals(this) ) 		//non è iteratore di questa lista
			throw new IllegalArgumentException("ZLinkedList::insert(itr, value) - invalid iterator");
		if( itr._pNode != null)					//iteratore valido
		{
			itr._pNode.insertAfter(value);
			if( itr._pNode.equals(_tail) )		//update tail
			{
				_tail = itr._pNode._pNext; 
			}
			_count++;
		}
		else	//iteratore di questa lista ma non punta ad un nodo valido
		{
			append(value);
		}
	}

	/**
	 * rimuove l'elemento puntato dall'iteratore.<br>
	 * L'iteratore punterà al nodo successivo.
	 * @param itr iteratore della lista
	 */
	public void remove(KListIterator<T> itr)
	{
		ZListNode node = _head;
		if( ! itr._pList.equals(this) ) 			//non è di questa lista
			throw new IllegalArgumentException("ZLinkedList::remove(itr) - invalid iterator");
		if(itr._pNode == null )						//iterator is invalid
			return;
		if(itr._pNode.equals(_head) )				//iterator is head
		{
			itr.next();
			removeFirst();
		}
		else
		{
			//vai al nodo prima di quello da cancellare
			while( ! node.next().equals( itr._pNode ))		
				node = node.next();
			 itr.next();						//muovi l'iterator fino al nodo da cancellare
			if( node.next().equals(_tail))
			{
				_tail= node;						//update tail
			}
			node._pNext = itr._pNode;				//re-link the list
			_count--;
		}
	}
	public void removeFirst()
	{
		ZListNode node = null;
		if( _head != null )
		{
			node = _head.next();
			_head = node;
			if(_head == null)		//la lista era di un solo elemento
				_tail= null;
			_count--;
		}
	}
	public void removeLast()
	{
		ZListNode node = _head;
		if( _head == null)				//lista vuota
			return;
		if(_head == _tail)				//only 1 element
		{
			_head = _tail = null;
		}
		else
		{
			while( !node.next().equals(_tail))	//go to one before tail
			{
				node = node.next();
			}
			_tail= node;					//reassign tail
			node._pNext = null;
		}
		_count--;
	}
	public void clear()
	{
		ZListNode itr = _head;
		ZListNode next = null;
		while( itr != null )
		{
			next = itr.next();
			itr.clear();
			itr = next;
		}
		_head = _tail = null;
		_count=0;
	}
	
	public void replace(KListIterator<T> itr, T newValue)
	{
		if( ! itr._pList.equals(this) )
			throw new IllegalArgumentException("ZLinkedList::replace(itr, value) - invalid iterator");
		if(itr._pNode == null)
			return;
		itr._pNode._data = newValue;
	}

	@SuppressWarnings("unchecked")
	private void readObject(java.io.ObjectInputStream stream) throws java.io.IOException, ClassNotFoundException
	{
		stream.defaultReadObject();
		_count = stream.readInt();
		_head = (ZListNode) stream.readObject();
		_tail = (ZListNode) stream.readObject();
	}
	
	private void writeObject(java.io.ObjectOutputStream stream) throws java.io.IOException
	{
		stream.defaultWriteObject();
		stream.writeInt(_count);
		stream.writeObject( _head );
		stream.writeObject( _tail );
	}
	
}
