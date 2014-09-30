package it.konga.framework.datastruct;

public class KBTreeIterator<T>
{

}


/*

//-------------------------------------------------
//Class GBTreeIterator : to iterate a binary tree
//-------------------------------------------------
template<class T> class GBTreeIterator
{
	typedef GBTree<T> Node;
public:
	GBTreeIterator( Node* ptr = nullptr)				{*this = ptr;}
	~GBTreeIterator()									{_pNode=nullptr;}

	inline GBTreeIterator& operator= (GBTree<T>* ptr)				{_pNode=ptr; return *this;}
	inline GBTreeIterator& operator=(const GBTreeIterator& it)		{_pNode=it._pNode; return *this;}
	
	inline void clear()												{_pNode= nullptr;}

	//vertical functions
	
	inline void moveToRoot()
	{
		if( _pNode != nullptr )
		{
			while ( _pNode->_pParent != nullptr )
				_pNode= _pNode->_pParent;
		}
	}

	//true if parent exists
	inline bool up()
	{
		if(_pNode != nullptr)
		{
			if( _pNode->_parent == nullptr)
				return false;
			_pNode = _pNode->_parent;
		}
		return true;
	}

	//move to the left child if exists
	inline bool leftChild()
	{
		if(_pNode->_pLeftCh != nullptr)
		{
			_pNode = _pNode->_pLeftCh;
			return true;
		}
		else
			return false;
	}
	//move to the right child if exists
	inline bool rightChild()
	{
		if(_pNode->_pRightCh != nullptr)
		{
			_pNode = _pNode->_pRightCh;
			return true;
		}
		else
			return false;
	}
	inline bool haveChildren() const
	{
		if(_pNode == nullptr)
			return false;
		if (_pNode->_pLeftCh == nullptr && _pNode->_pRightCh == nullptr)
			return false;
		return true;
	}

	//other functions
	
	//return false if node have 2 children or iterator is invalid
	//if left==null append to left, otherwise append to right
	inline bool appendChild(const T& childValue)
	{
		if( _pNode == nullptr)
			return false;
		if( _pNode->_pLeftCh != nullptr && _pNode->_pRightCh != nullptr)
			return false;
		Node* child = new Node(childValue);
		child->_pParent = _pNode;
		if( _pNode->_pLeftCh == nullptr)
		{
			_pNode->_pLeftCh = child;
			return true;
		}
		else
		{
			_pNode->_pRightCh = child;
			return true;
		}
	}
	inline bool appendLeftChild(const T& childValue)
	{
		if( _pNode == nullptr)
			return false;
		if( _pNode->_pLeftCh != nullptr)
			return false;
		Node* child = new Node(childValue);
		child->_pParent = _pNode;
		_pNode->_pLeftCh = child;
		return true;
	}
	inline bool appendRightChild(const T& childValue)
	{
		if( _pNode == nullptr)
			return false;
		if( _pNode->_pRightCh != nullptr)
			return false;
		Node* child = new Node(childValue);
		child->_pParent = _pNode;
		_pNode->_pRightCh = child;
		return true;
	}

	inline bool removeLeftChild()
	{
		if( _pNode == nullptr)
			return false;
		if( _pNode->_pLeftCh == nullptr)
			return false;
		delete _pNode->_pLeftCh;
		_pNode->_pLeftCh = nullptr;
		return true;
	}
	inline bool removeRightChild()
	{
		if( _pNode == nullptr)
			return false;
		if( _pNode->_pRightCh == nullptr)
			return false;
		delete _pNode->_pRightCh;
		_pNode->_pRightCh = nullptr;
		return true;
	}

	inline bool isValid() const									{return (_pNode!= nullptr);}
	inline bool isLeftChildValid() const						{return (_pNode->_pLeftCh != nullptr); }
	inline bool isRightChildValid() const						{return (_pNode->_pRightCh != nullptr); }

	//value in this node
	inline T& value()											{return _pNode->_data;}
	inline const T& const_value() const							{return _pNode->_data;}
	inline Node* node()											{return _pNode;}

	//return a pointer to a Tree with root==left child
	inline Node* getLeftChild()									{return _pNode->_pLeftCh;}
	inline Node* getRightChild()								{return _pNode->_pRightCh;}
	//return number of children
	inline int childrenNumber() const
	{
		int c=0;
		if(_pNode->_pLeftCh!= nullptr)
			c+=1;
		if(_pNode->_pRightCh!= nullptr)
			c+=1;
		return c;
	}

private:
	Node* _pNode;
};//EO classGTreeIterator



*/