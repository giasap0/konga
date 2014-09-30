package it.konga.framework.datastruct;


/**
 * tipo arrayList ma con più metodi
 * @author Giampaolo Saporito
 */
public class KVector<T>
{

	// ------------------------------------------------------------------------- fields ------------------------------------------------------------------------- \\
	protected Object[] _v;
	private int _size;

	// ------------------------------------------------------------------------- costruttori ------------------------------------------------------------------------- \\
	public KVector()				{_v = new Object[10]; _size=0;}
	public KVector(int capacity )	{_v = new Object[capacity];	_size = 0; }

	public KVector(int size, T value)
	{
		_v = new Object[size];
		for (int i=0; i < _v.length; i++) {
			_v[i] = value;
		}
		_size=size;
	}
	public KVector(T[] fromArray)
	{
		if (fromArray == null || fromArray.length == 0)
		{
			_v = new Object[10]; _size=0; return;
		}
		_v = new Object[fromArray.length];
		for(int i=0; i < fromArray.length; i++)
		{
			_v[i] = fromArray[i];
		}
		_size = _v.length;
	}

	// ------------------------------------------------------------------------- metodi publici ------------------------------------------------------------------------- \\
	public int capacity()								{return _v.length;}

	/** se newSize > size aumenta la capacity */
	public void resize(int newSize)
	{
		if(newSize ==_size)
			return;
		if(newSize<=0)
		{
			_v = new Object[10];
			_size = 0;
			return;
		}

		Object[] temp = _v;
		_v = new Object[newSize];
		int maxIndex = 0;
		if(temp.length <= newSize)
			maxIndex = temp.length;
		else //lo sto rimpicciolendo
			maxIndex = newSize;
		for(int i=0; i < maxIndex; i++)
		{
			_v[i] = temp[i];
		}
		_size = maxIndex;
	}
	
	/** insert value at last position */
	public void	append(T value)
	{
		if(_size==0)
		{
			if(capacity()==0)
			{
				_v = new Object[10];
			}
			//se sono qui il size è 0 ma la capacità è più alta
			_v[0] = value;
			++_size;
			return;
		}
		//il problema è se la capacity è uguale al size, questo non deve mai succedere
		if( capacity() == _size)
		{
			resize(_size+10);
		}
		_v[_size] = value;
		++_size;
	}
}
/*
	//return ant reference to element in position i
	T&		at(uint i) ;

	//clear all the elements in the vector and resize it to 0
	void			clear();	
	bool			contains( T& value) ;
	//return number of vector's elements with value==value
	int				count( T& value) ;
	//return pointer to the first element
	T*				data();
	//fill the vector with value value and if size!=-1 resize to size
	GVector<T>&		fill( T& value, int size = -1);
	//return reference to first element
	T&				first();
	//return position of element with value value. if it doesn't exists return -1
	int				indexOf( T& value, uint from=0) ;
	//insert value in position = index
	void			insert(uint index,  T& value);
	//insert number elements with value==value, starting from position index
	void			insert(uint index, uint number,  T& value);
	//true if ther's no elements
	bool			isEmpty() ;
	//reference to last element
	T&				last();
	//the maximum potential size due to system
	public uint		max_size()							{return _v.max_size();}
	//insert element with value value at position 0
	void			prepend( T& value);
	//remove element at index==atIndx and resize vector to size-1
	void			remove(uint atIndx);
	//remove number of lemenets starting from index atIndex (included), also resize vector
	void			remove(uint atIndx, uint number);
	void			replace(uint atIndx,  T& value);
	void			reserve(uint n);
	//p_compare = a function pointer that return true if left<right
	public void		sort(bool(*p_compare)(T,T))			{std::sort(_v.begin(),_v.end(),p_compare);}
	uint			size() ;
	//release extra memory: TRY TO make capacity == size
	void			squeeze ();
	void			swap ( GVector<T> & other );
	std::vector<T>	toStdVector () ;
	//return the value of the element in position atIndx. if indx is not valid return default uction
	T				value ( uint atIndx ) ;
	//return the value of the element in position atIndx, if indx is not valid return defaultValue
	T				value ( uint atIndx,  T & defaultValue ) ;

	//return reference to element at position indx
	public T at(int indx) {}

	// ------------------------------------------------------------------------- publici statici ------------------------------------------------------------------------- \\

	public static<E> KVector<E> fromSTDVector( T[] v);

	// ------------------------------------------------------------------------- metodi privati ------------------------------------------------------------------------- \\

}


/*

protected:
	std::vector<T> _v;
};

template<class T> GVector<T>::GVector(uint size) : _v(size)								{}
template<class T> GVector<T>::GVector(uint size, const T& value) : _v(size,value)		{}
template<class T> GVector<T>::GVector(const GVector& other) : _v(other.toStdVector())	{}

template<class T> public const T& GVector<T>::at(uint i) const							{return _v.at(i);}
template<class T> public int GVector<T>::capacity() const								{return _v.capacity();}
template<class T> public void GVector<T>::clear()										{_v.clear();}
template<class T> public bool GVector<T>::contains(const T& value) const
{
	for (uint i=0; i<_v.size(); i++)
	{
		if(_v.at(i)==value)
			return true;
	}
	return false;
}
template<class T> public int GVector<T>::count(const T& value) const
{
	int n=0;
	for(uint i=0; i<_v.size(); i++)
	{
		if(_v.at(i)==value)
			n++
	}
	return n;
}
template<class T> public T* GVector<T>::data() {return _v.data();}
template<class T> public GVector<T>& GVector<T>::fill(const T& value, int size)
{
	if(size<0)
		size = _v.size();
	_v.assign(size,value);
	return *this;
}
template<class T> public T& GVector<T>::first() {return _v.front();}
template<class T> public int GVector<T>::indexOf(const T& value, uint from) const
{
	if(from>=_v.size())
		return -1;
	for(int i=from; i<_v.size(); i++)
	{
		if(_v.at(i) == value)
			return i;
	}
	return -1;
}
template<class T> public void GVector<T>::insert(uint index, const T& value)	{_v.insert(_v.begin()+index,value);}
template<class T> public void GVector<T>::insert(uint index, uint number, const T& value)
{
	_v.reserve(_v.size()+number);
	_v.insert(_v.begin()+index,number,value);
}
template<class T> public bool GVector<T>::isEmpty() const						{return _v.empty();}
template<class T> public T& GVector<T>::last()									{return _v.back();}
template<class T> public void GVector<T>::prepend(const T& value)				{_v.insert(_v.begin(),value);}
template<class T> public void GVector<T>::remove(uint atIndx)					{_v.erase(_v.begin()+atIndx);}
template<class T> public void GVector<T>::remove(uint atIndx, uint number)		{_v.erase(_v.begin()+atIndx,_v.begin()+atIndx+number);}
template<class T> public void GVector<T>::replace(uint atIndx, const T& value)	{_v[atIndx]=value;}
template<class T> public void GVector<T>::reserve(uint n)						{_v.reserve(n);}
template<class T> public void GVector<T>::resize(uint newSize)					{_v.resize(newSize);}
template<class T> public uint GVector<T>::size() const							{return _v.size();}
template<class T> public void GVector<T>::squeeze()								{_v.shrink_to_fit();}
template<class T> public void GVector<T>::swap(GVector<T>& other)				{_v.swap(other._v);}
template<class T> public std::vector<T> GVector<T>::toStdVector () const		{return _v;}
template<class T> public T GVector<T>::value(uint atIndx) const
{
	if(atIndx<_v.size())
		return _v.at(atIndx);
	return T();
}
template<class T> public T GVector<T>::value(uint atIndx,const T& defaultValue) const
{
	if(atIndx<_v.size())
		return _v.at(atIndx);
	return defaultValue;
}

template<class T> public GVector<T>& GVector<T>::operator=(const GVector<T>& other)
{
	if(*this==other)
		return *this;
	_v = other._v;
	return *this;
}
template<class T> public bool GVector<T>::operator==(const GVector<T>& other) const {return _v==other._v;}
template<class T> public bool GVector<T>::operator!=(const GVector<T>& other) const {return _v!=other._v;}
template<class T> public GVector<T> GVector<T>::operator+(const GVector<T>& other) const
{
	GVector<T> res(_v);
	uint vSize = _v.size();
	uint newSize = vSize+other.size();
	res.reserve(newSize);
	for(uint i=vSize; i<newSize; i++)
	{
		res.append(other.at(i-vSize));
	}
	return res;
}
template<class T> public GVector<T>& GVector<T>::operator+=(const GVector<T>& other) 
{
	uint vSize = _v.size();
	uint newSize = vSize+other.size();
	reserve(newSize);
	for(uint i=vSize; i<newSize; i++)
	{
		append(other.at(i-vSize));
	}
	return *this;
}
template<class T> public GVector<T>& GVector<T>::operator+=(const T& value)				{append(value); return *this;}
template<class T> public GVector<T>& GVector<T>::operator<<(const GVector<T>& other)	{return this->operator+=(other);}
template<class T> public GVector<T>& GVector<T>::operator<<(const T& value)				{return this->operator+=(T);}
template<class T> public T&			 GVector<T>::operator[](uint indx)					{return _v[indx];}
template<class T> public const T&	 GVector<T>::operator[](uint indx) const			{return _v[indx];}

	//static members

template<class T> public GVector<T> GVector<T>::fromSTDVector(const std::vector<T>& v) {GVector<T> x; x._v=v; return x;}




 */