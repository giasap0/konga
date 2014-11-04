package it.konga.framework.datastruct;

import it.konga.framework.util.Compare_Integers;

import java.util.ArrayList;
import java.util.Iterator;

import javax.el.MethodNotFoundException;

/**
 * Iteratore per la coda prioritaria
 * @author Giampaolo Saporito
 * @Date 05/09/2014
 */
public class KPriorityQueueIterator<T> implements Iterator<T>
{
	protected KPriorityQueue<T> _q;
	protected int _currentIndex = 0;
	// *************************************************************************************************
	// 									costruttori
	// *************************************************************************************************
	public KPriorityQueueIterator() {}
	
	public KPriorityQueueIterator( KPriorityQueue<T> queue)
	{
		_q = queue;
		_currentIndex = 0;
	}
	
	public KPriorityQueueIterator( KPriorityQueueIterator<T> it)
	{
		_q = it._q; _currentIndex= it._currentIndex;
	}
	
	// *************************************************************************************************
	// 									metodi publici
	// *************************************************************************************************
	
	@Override
	public String toString()			{return "KPriorityQueueIterator on queue with code " + _q.hashCode(); }
	
	/** not implemented */
	@Override
	public void remove()				{throw new MethodNotFoundException("KPriorityQueueIterator - method remove - not dlecared"); }
	
	@Override
	public boolean hasNext()			{return (_currentIndex < _q._v.size()-1); }

	//TODO fare in modo che funzioni, al momento non scorre correttamente
	@Override
	public T next()
	{
		ArrayList<T> v = _q._v;
		int count = v.size()-1;
		if(count >= 1)
		{
			if( _currentIndex <= 0)
			{
				_currentIndex =1;
				return v.get(1);
			}
			else{
				if( _q._compare.compare(  v.get(_currentIndex+1) , v.get((_currentIndex*2)+1) ) > 0 )
				{
					_currentIndex +=1;
					return v.get(_currentIndex);
				}
				else
				{
					_currentIndex +=2;
					return v.get(_currentIndex);
				}
			}
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		KPriorityQueue<Integer> q = new KPriorityQueue<Integer>(new Compare_Integers() );
		q.append(1).append(90).append(10).append(12).append(80).append(-1).append(200).append(8).append(5).append(15).append(170).append(-12);
		
//		for (Integer e : q) {
//			System.out.println(e);
//		}
	}
	
}//EO KPriorityQueueIterator
