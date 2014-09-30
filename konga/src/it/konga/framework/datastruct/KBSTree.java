package it.konga.framework.datastruct;

import it.konga.framework.interfaces.Ptr_Function_Compare;

/**
 * Binary Search Tree.<br>
 * Non inserisce due dati uguali.<br>
 * Bisogna definire una funzione Compare, in grado di comparare due dati<br>
 * @author Giampaolo Saporito
 */
public class KBSTree<T>
{
	// ------------------------------------------------------------------------- fields ------------------------------------------------------------------------- \\
	private Ptr_Function_Compare<T> _compare;
	private KBTree<T> _pRoot;

	// ------------------------------------------------------------------------- costruttori ------------------------------------------------------------------------- \\
	KBSTree(Ptr_Function_Compare<T> p_compare)					{_compare = p_compare; _pRoot = null;}

	// ------------------------------------------------------------------------- metodi publici ------------------------------------------------------------------------- \\

	public int size() 											{return _pRoot.size();}
	public int depth() 											{return _pRoot.depth();}

	/** a sinistra se _compare<0, altrimenti destra.<br> non inserisce se il dato già esiste */
	public void insert(T data)
	{
		KBTreeIterator<T> it = new KBTreeIterator<T>(_pRoot);
		if(_pRoot == null)
			_pRoot = new KBTree<T>(data);
		else
		{
			int comp;
			while( it.isValid() )
			{
				comp = _compare.compare(data, it.value());
				if(comp == 0)
					return;
				else if(comp < 0)
				{
					if( !it.leftChild() )
					{
						it.appendLeftChild(data);
						it.clear();
					}
				}
				else
				{
					if( ! it.rightChild() )
					{
						it.appendRightChild(data);
						it.clear();
					}
				}
			}//	EO while (it.isValid )
		}// EO else {root exists}
	}

	/** return an iterator pointing to the node with value==data (return invalid it if doesn't exists) */
	public KBTreeIterator<T> find(T data)
	{
		KBTreeIterator<T> current = new KBTreeIterator<T>(_pRoot);
		int temp;
		while( current.isValid() )
		{
			temp = _compare.compare( data, current.value() );
			if ( temp == 0 )
				return current;
			else if ( temp<0 )
			{
				if (!current.leftChild() )
					current.clear();
			}
			else
			{
				if (current.rightChild() )
					current.clear();
			}
		}
		current.clear();
		return current;
	}

}//EO class KBSTree<T>

