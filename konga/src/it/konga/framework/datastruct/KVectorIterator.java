package it.konga.framework.datastruct;

import java.util.Iterator;

public class KVectorIterator<T> implements Iterator<T>
{
	protected KVector<T> _pVector;
	protected int _currentIndex = 0;

	// *************************************************************************************************
	// 									costruttori
	// *************************************************************************************************
	public KVectorIterator()							{}
	public KVectorIterator(KVector<T> vector)			{_pVector=vector;}
	public KVectorIterator(KVectorIterator<T> iter)		{_pVector=iter._pVector;}

	// *************************************************************************************************
	// 									implements iterator
	// *************************************************************************************************

	@Override
	public boolean hasNext()
	{
		if(_pVector.size() >_currentIndex)
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
		return "KListIterator on List with code " + _pVector.hashCode();
	}


}//EO KVectorIterator
