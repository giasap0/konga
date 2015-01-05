package it.konga.framework.datastruct;

import java.util.Iterator;

public class KArrayIterator<T> implements Iterator<T>
{
	protected KArray<T> _pVector;
	protected int _currentIndex = 0;

	// *************************************************************************************************
	// 									costruttori
	// *************************************************************************************************
	public KArrayIterator()							{}
	public KArrayIterator(KArray<T> vector)			{_pVector=vector;}
	public KArrayIterator(KArrayIterator<T> iter)	{_pVector=iter._pVector;}

	// *************************************************************************************************
	// 									implements iterator
	// *************************************************************************************************

	@Override
	public boolean hasNext()
	{
		if(_pVector.size() >= _currentIndex)
			return false;
		return true;
	}

	/** return current data and move to next */
	@Override
	public T next()
	{
		if( !hasNext())
			return null;
		@SuppressWarnings("unchecked")
		T val = (T)_pVector._v[_currentIndex];
		++_currentIndex;
		return val;
	}
	
	@Override
	public void remove()
	{
		if( _pVector== null)
			return;
		_pVector.remove(_currentIndex);
	}

	// *************************************************************************************************
	// 									metodi publici
	// *************************************************************************************************
	
	
	@Override
	public String toString()
	{
		return "KVectorIterator on KVector with code " + _pVector.hashCode();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int  result = prime * 1 + _currentIndex;
		result = prime * result + ((_pVector == null) ? 0 : _pVector.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof KArrayIterator)) {
			return false;
		}
		@SuppressWarnings("unchecked")
		KArrayIterator<T> other = (KArrayIterator<T>) obj;
		if (_currentIndex != other._currentIndex) {
			return false;
		}
		if (_pVector == null) {
			if (other._pVector != null) {
				return false;
			}
		} else if (!_pVector.equals(other._pVector)) {
			return false;
		}
		return true;
	}
	
	/** valore del nodo puntato */
	public T getData()					{if(_pVector== null) return null; return _pVector.at(_currentIndex);}
	public int currentIndex()			{return _currentIndex;}
	public boolean isValid()			{return _pVector != null;}
	public void gotoBegin()				{if(_pVector != null) _currentIndex = 0;}
	public void gotoEnd()				{if(_pVector != null) _currentIndex = _pVector.size()-1;}
	/** mette i puntamenti a null */
	public void clear()					{_pVector = null; _currentIndex = 0;}

}//EO KVectorIterator
