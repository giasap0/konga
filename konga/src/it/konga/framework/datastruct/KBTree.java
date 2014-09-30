package it.konga.framework.datastruct;

import it.konga.framework.datastruct.interfaces.Ptr_Function_KBTree;

/**
 * Binary Tree
 * @author Giampaolo Saporito
 */
public class KBTree<T>
{
	//TODO implementare
	
	//questo è solo syntax sugar
	private class Node extends KBTree<T>
	{
		public Node() {super();}
		public Node(T val) {super(val);}
	}
	// ------------------------------------------------------------------------- fields ------------------------------------------------------------------------- \\
	protected T _data;
	protected Node _pParent;
	protected Node _pLeftCh;
	protected Node _pRightCh;
	
	// ------------------------------------------------------------------------- costruttori ------------------------------------------------------------------------- \\
	public KBTree() 		{}
	public KBTree(T val) 	{_data = val;}
	
	// ------------------------------------------------------------------------- metodi publici ------------------------------------------------------------------------- \\
	/**delete all children from this node */
	public void clearChildren()
	{
		if( _pLeftCh != null )
		{
			_pLeftCh.clearChildren();
			_pLeftCh = null;
		}
		if(_pRightCh != null)
		{
			_pRightCh.clearChildren();
			_pRightCh = null;
		}
	}
	
	/** return total number of nodes */
	public int size()
	{
		int c=1;
		if(_pLeftCh != null)
		{
			c += _pLeftCh.size();
		}
		if( _pRightCh != null)
		{
			c+= _pRightCh.size();
		}
		return c;
	}
	
	public T value() 				{return _data;}
	public void setValue(T value) 	{_data = value;}
	public boolean haveChildren()	{if(_pLeftCh== null && _pRightCh== null) return false; return true;}
	/**Profondità dell'albero:<br>
	 *  0== solo root, 1== root e children sono leaf, e così via
	 *  */
	public int depth()
	{
		int d =0;
		if(! haveChildren() ) //leaf
		{
			return nodeDepth();
		}
		else
		{
			int depL=0, depR=0;
			if( _pLeftCh!= null )
			{
				depL = _pLeftCh.depth();
			}
			if( _pRightCh != null )
			{
				depR = _pRightCh.depth();
			}
			if(depL>d)
				d= depL;
			if(depR>d)
				d= depR;
			return d;
		}
	}
	
	// ------------------------------------------------------------------------- publici - statici ------------------------------------------------------------------------- \\
	/**dalla root alle foglie (prima sinistra, poi destra) */
	public static<E> void iteratePreorder(KBTree<E> p_node, Ptr_Function_KBTree<E> ptr_function )
	{
		if(p_node == null)
			return;
		ptr_function.operation(p_node);
		if(p_node._pLeftCh != null)
			iteratePreorder(p_node._pLeftCh, ptr_function);
		if(p_node._pRightCh != null)
			iteratePreorder(p_node._pRightCh, ptr_function);
	}
	/** process from leaves to root ( root == last element processed) */
	public static<E> void iteratePostorder(KBTree<E> p_node, Ptr_Function_KBTree<E> ptr_function )
	{
		if(p_node == null)
			return;
		if(p_node._pLeftCh != null)
			iteratePostorder(p_node._pLeftCh, ptr_function);
		if(p_node._pRightCh != null)
			iteratePostorder(p_node._pRightCh, ptr_function);
		ptr_function.operation(p_node);
	}
	
	public static<E> void iterateInOrder(KBTree<E> p_node, Ptr_Function_KBTree<E> ptr_function )
	{
		if(p_node==null)
			return;
		if( p_node._pLeftCh != null)
			iterateInOrder(p_node._pLeftCh, ptr_function);
		ptr_function.operation( p_node );
		if( p_node._pRightCh != null)
			iterateInOrder(p_node._pRightCh ,ptr_function );
	}
	
	// ------------------------------------------------------------------------- metodi privati ------------------------------------------------------------------------- \\

	private int nodeDepth()
	{
		int d=0;
		Node ptr = _pParent;
		while(ptr != null)
		{
			++d;
			ptr = ptr._pParent;
		}
		return d;
		
	}
	// ------------------------------------------------------------------------- privati - statici ------------------------------------------------------------------------- \\
	private static<T> int nodeDepth(KBTree<T> t)
	{
		int d=0;
		KBTree<T> ptr = null;
		if(t== null)
			return 0;
		ptr = t._pParent;
		while(ptr != null)
		{
			++d;
			ptr = ptr._pParent;
		}
		return d;
	}
}//EO class GBTree

