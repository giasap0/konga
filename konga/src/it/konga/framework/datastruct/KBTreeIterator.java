package it.konga.framework.datastruct;

/**
 * Per iterare un binary Tree
 * @author Giampaolo Saporito
 */
public class KBTreeIterator<T>
{
	// ------------------------------------------------------------------------- fields ------------------------------------------------------------------------- \\
	private KBTree<T> _pNode;

	// ------------------------------------------------------------------------- costruttori ------------------------------------------------------------------------- \\
	public KBTreeIterator()								{}
	public KBTreeIterator(KBTree<T> node)
	{
		_pNode = node;
	}
	public KBTreeIterator(KBTreeIterator<T> other)
	{
		_pNode = other._pNode;
	}

	// ------------------------------------------------------------------------- metodi publici ------------------------------------------------------------------------- \\
	public void clear()										{ _pNode = null; }
	public boolean haveChildren()
	{
		if(_pNode == null)
			return false;
		if (_pNode._pLeftCh == null && _pNode._pRightCh == null)
			return false;
		return true;
	}

	// ------------------------------------------------------------------------- funzioni verticali ------------------------------------------------------------------------- \\

	public void moveToRoot()
	{
		if(_pNode != null)
		{
			while( _pNode._pParent != null)
				_pNode = _pNode._pParent;
		}
	}

	/** true if parent exists */
	public boolean up()
	{
		if(_pNode != null)
		{
			if( _pNode._pParent == null)
				return false;
			_pNode = _pNode._pParent;
		}
		return true;
	}

	/** move to the left child if exists */
	public boolean leftChild()
	{
		if(_pNode._pLeftCh != null)
		{
			_pNode = _pNode._pLeftCh;
			return true;
		}
		else
			return false;
	}
	
	/** move to the right child if exists */
	public boolean rightChild()
	{
		if(_pNode._pRightCh != null)
		{
			_pNode = _pNode._pRightCh;
			return true;
		}
		else
			return false;
	}
	
	// ------------------------------------------------------------------------- altre funzioni ------------------------------------------------------------------------- \\
	
	//return false if node have 2 children or iterator is invalid
		//if left==null append to left, otherwise append to right
		public boolean appendChild( T childValue)
		{
			if( _pNode == null)
				return false;
			if( _pNode._pLeftCh != null && _pNode._pRightCh != null)
				return false;
			KBTree<T> child = new KBTree<T>(childValue);
			child._pParent = _pNode;
			if( _pNode._pLeftCh == null)
			{
				_pNode._pLeftCh = child;
				return true;
			}
			else
			{
				_pNode._pRightCh = child;
				return true;
			}
		}
		public  boolean appendLeftChild(T childValue)
		{
			if( _pNode == null)
				return false;
			if( _pNode._pLeftCh != null)
				return false;
			KBTree<T>child = new KBTree<T>(childValue);
			child._pParent = _pNode;
			_pNode._pLeftCh = child;
			return true;
		}
		public  boolean appendRightChild(T childValue)
		{
			if( _pNode == null)
				return false;
			if( _pNode._pRightCh != null)
				return false;
			KBTree<T> child = new KBTree<T>(childValue);
			child._pParent = _pNode;
			_pNode._pRightCh = child;
			return true;
		}

		public boolean removeLeftChild()
		{
			if( _pNode == null)
				return false;
			if( _pNode._pLeftCh == null)
				return false;
			_pNode._pLeftCh.clearChildren();
			_pNode._pLeftCh = null;
			return true;
		}
		
		public boolean removeRightChild()
		{
			if( _pNode == null)
				return false;
			if( _pNode._pRightCh == null)
				return false;
			_pNode._pRightCh.clearChildren();
			_pNode._pRightCh = null;
			return true;
		}

		public boolean isValid()							{return (_pNode!= null);}
		public boolean isLeftChildValid()					{return (_pNode._pLeftCh != null); }
		public boolean isRightChildValid()					{return (_pNode._pRightCh != null); }

		/** value in this node */
		public T value()									{return _pNode._data;}
		public KBTree<T> node()								{return _pNode;}

		/** return a pointer to a Tree with root==left child */
		public KBTree<T> getLeftChild()						{return _pNode._pLeftCh;}
		/** return a pointer to a Tree with root==right child */
		public KBTree<T> getRightChild()					{return _pNode._pRightCh;}
		//return number of children
		public int childrenNumber()
		{
			int c=0;
			if(_pNode._pLeftCh!= null)
				c+=1;
			if(_pNode._pRightCh!= null)
				c+=1;
			return c;
		}

}//EO class KBTreeIterator<T>
