package it.konga.framework.datastruct;

import it.konga.framework.interfaces.Ptr_Function_Compare;

import java.util.ArrayList;


// ATTENZIONE: non utilizzo l'indice 0 \\

//TODO implementare

/**
 * Coda Prioritaria
 * @author Giampaolo Saporito
 */
public class KPriorityQueue<T>
{
	// ------------------------------------------------------------------------- fields ------------------------------------------------------------------------- \\
	private ArrayList<T> _v;
	private Ptr_Function_Compare<T> _compare;
	
	// ------------------------------------------------------------------------- costruttori ------------------------------------------------------------------------- \\
	/**
	 * costruisci una coda prioritaria
	 * @param compare funzione di comparazione per il tipo specificato
	 */
	public KPriorityQueue(Ptr_Function_Compare<T> compare)		{_v = new ArrayList<T>(2); _v.add(null); _v.add(null); _compare = compare;}
	public KPriorityQueue(KPriorityQueue<T> other)				{_v = new ArrayList<T>(other._v); _compare = other._compare; }
	
	// ------------------------------------------------------------------------- metodi publici ------------------------------------------------------------------------- \\
	public int size() 											{return _v.size()-1;}
	public T first()											{return _v.get(1);}
	public boolean isEmpty()									{if(_v.size()>1) return false; return true;}
	//public void clear()											{_v.}
	
	// ------------------------------------------------------------------------- metodi privati ------------------------------------------------------------------------- \\
	//private void walkUp(int i);
	//private void walkDown(int i);
}

/*
public:

	inline bool isEmpty() const									{if(_v.size()>1) return false; return true;}
	//clear elements, not function pointer
	inline void clear()											{_v.resize(1);}
	//clear elements and change function pointer
	inline void clear( int(*p_compare)(const T&, const T&) )	{_v.resize(1); _compare=p_compare;}

	//insert into queue
	void enqueue(const T& data);
	//removing the root node of the heap
	void dequeue();

	inline bool			operator==(const GHeap<T>& o) const		{return (_v==o._v && _compare==o._compare);}
	inline bool			operator!=(const GHeap<T>& o) const		{return !this->operator==(o);}
	inline GHeap<T>&	operator+=(const T& x )					{enqueue(x); return *this;}


};

template<class T> inline void GHeap<T>::enqueue(const T& data)
{
	_v.append(data);
	walkUp( static_cast<int>(_v.size())-1 );
}
template<class T> inline void GHeap<T>::dequeue()
{
	int count = static_cast<int>( _v.size()-1 );
	if( count >= 1)
	{
		_v[1] = _v[count];		//root==last
		walkDown( 1 );			//re-order
		_v.remove( count);		//remove last element
		--count;
	}
}

template<class T> inline void GHeap<T>::walkDown(int i)
{
	int parent = i;
	int child = i*2;
	T temp = _v[parent];
	int count = _v.size()-1;
	while( child < count )
	{
		if( child < count-1 )
		{
			if( _compare( _v[child], _v[child+1] ) < 0)
				++child;
		}
		if( _compare(temp, _v[child]) < 0)
		{
			_v[parent] = _v[child];
			parent= child;
			child*=2;
		}
		else
			break;
	}
	_v[parent] = temp;
}

template<class T> inline void GHeap<T>::walkUp(int i)
{
	int parent = i/2;
	int child = i;
	T temp = _v[child];
	while(parent>0)
	{
		if( _compare( temp, _v[parent]) > 0)
		{
			_v[child] = _v[parent];
			child = parent;
			parent /=2;
		}
		else
			break;
	}//EO while (parent valid)
	_v[child] = temp;
}



*/