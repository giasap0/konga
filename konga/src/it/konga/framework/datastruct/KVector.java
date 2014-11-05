package it.konga.framework.datastruct;

import it.konga.framework.interfaces.Ptr_Function_Compare;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.Iterator;
import java.util.List;


/**
 * tipo arrayList ma con più metodi
 * @author Giampaolo Saporito
 */
@SuppressWarnings("unchecked")
public class KVector<T> implements Serializable, Iterable<T>
{
	private static final long serialVersionUID = -5316121059953715244L;
	// ------------------------------------------------------------------------- fields ------------------------------------------------------------------------- \\
	protected Object[] _v;
	private int _size;

	// ------------------------------------------------------------------------- costruttori ------------------------------------------------------------------------- \\
	public KVector()				{this(10);}
	public KVector(int capacity )
	{
		_size = 0;
		_v = new Object[capacity];
	}

	/** costruisce un vettore di size 'size' con tutti gli elementi assegnati al valore 'value' */
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
	public KVector(KVector<T> other)
	{
		 _size = other._size;
		 _v = new Object[other._v.length];
		 for(int i=0; i < _v.length; i++){
			 _v[i] = other._v[i];
		 }
	}

	// ------------------------------------------------------------------------- metodi publici ------------------------------------------------------------------------- \\
	/** pool di memoria occupata */
	public int capacity()											{return _v.length;}
	/** numero di elementi nell'array */
	public int size()												{return _size;}
	/**cancella tutti gli elementi nel vettore e fa un resize a 0.<br>*/
	public void clear()												{resize(0);}
	/** elemento all'indice 0 */
	public T first()												{if(capacity()==0) return null; return (T) _v[0];}
	/** reference all'ultimo lemento */
	public T last()													{if(capacity()==0) return null; return (T)_v[_size-1];}
	/** vero se non ci sono elementi */
	public boolean isEmpty() 										{return _size<=0;}
	/** riserva la memoria per aggiungere n elementi */
	public void reserve(int n)										{this.resize(_size+n);}
	/**release extra memory: make capacity == size */
	public void squeeze ()											{this.resize(_size);}
	/**torna la reference all'elemento in posizione i<br>Gli indici partono da 0*/
	public T at(int i)
	{
		if(i <0 || i >= _size )
			throw new IndexOutOfBoundsException("KVector::at - index out of bound - index value == "+i + " ,array size == "+_size);
		return (T)_v[i];
	}

	/** se newSize > size aumenta la capacity, altrimenti taglia via tutti gli elementi in più */
	public void resize(int newSize)
	{
		if(newSize<=0)
		{
			_v = new Object[0];
			_size = 0;
			return;
		}
		Object[] temp = _v;
		this._v = new Object[newSize];
		int maxIndex = 0;
		if(_size < newSize)
			maxIndex = _size;
		else //lo sto rimpicciolendo
			maxIndex = newSize;
		for(int i=0; i < maxIndex; i++)
		{
			_v[i] = temp[i];
		}
		_size = maxIndex;
	}
	
	/** insert value at last position<br>return this */
	public KVector<T> append(T value)
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
			return this;
		}
		//se capicity == size mi riservo altra memoria
		if( capacity() == _size)
		{
			resize(_size+10);
		}
		_v[_size] = value;
		++_size;
		return this;
	}

	/** if value == null return false */
	public boolean contains(T value)
	{
		if (_v.length == 0) return false;
		if(value == null)	return false;
		for (Object obj : _v)
		{
			T x = (T)obj;
			if(value.equals(x))
				return true;
		}
		return false;
	}
	/** return number of vector's elements with value==value.<br>if value == null return 0 */
	public int count(T value)
	{
		if (_v.length == 0) return 0;
		if(value == null)	return 0;
		int count = 0;
		for (Object obj : _v)
		{
			T x = (T)obj;
			if(value.equals(x))
				return ++count;
		}
		return count;
	}
	
	/**riempie il vettore con valori == 'value'.<br>Torna this */
	public KVector<T> fill(T value)							{return fill(value,-1);}
	/**riempie il vettore con valori == 'value' e se il size è != -1 fa il resize.<br>Torna this */
	public KVector<T> fill(T value, int size)
	{
		if(size<0)
			size = _size;
		this.resize(size);
		if(size==0)
			return this;
		for(int i=0; i < _size; i++)
		{
			_v[i] = value;
		}
		return this;
	}
	
	/**torna la posizione dell'elemento con value.equals(value).<br>Se non esiste torna -1 */
	public int indexOf(T value)								{return indexOf(value,0);}
	/**torna la posizione dell'elemento con value.equals(value).<br>Se non esiste torna -1 */
	public int indexOf(T value, int from)
	{
		if( from >= _size || from <0)
			throw new IndexOutOfBoundsException("KVector::indexOf - index out of bound - index value == "+from + " ,array size == "+_size);
		if(value == null)
			throw new InvalidParameterException("KVector::indexOf - invalid parameter 'value' == null");
		for(int i=from; i < _size; i++)
		{
			T x = (T)_v[i];
			if(value.equals(x))
				return i;
		}
		return -1;
	}
	/** inserisce 'value' nella posizione 'index'.<br>Sposta a destra tutti i valori con indice successivo.<br>Causa resize. */
	public void insert(int index, T value)
	{
		if( index < 0 || index > _size)
			throw new IndexOutOfBoundsException("KVector::insert - index out of bound - index value == "+index + " array size == "+_size);
		int capacity = _v.length;
		if(capacity<= _size)
			resize(_size+10);
		for(int i= _size+1; i>index;i--) //sposto tutti gli elementi successivi ad index a destra
		{
			_v[i] = _v[i-1];
		}
		_v[index] = value;
		++_size;
	}
	
	/**
	 * inserisce 'number' elemti con valore == value, partendo dall'indice 'index'
	 * @param index indice da cui cominciare l'inserimento
	 * @param number numero di elementi da inserire. Deve essere > 0
	 * @param value valore dei nuovi elementi
	 */
	public void insert(int index, int number, T value)
	{
		if( index < 0 || index > _size)
			throw new IndexOutOfBoundsException("KVector::inser - index out of bound - index value == "+index + " array size == "+_size);
		if(number<=0)
			throw new InvalidParameterException("KVector::insert - invalid parameter 'number' == "+number +" , expected > 0");
		int capacity = _v.length;
		int newSize = _size+number;
		if(capacity< newSize)
		{
			resize(newSize);
		}		
		for(int i=newSize-1; i>= index+number; i--) //sposto a destra gli elementi
		{
			_v[i] = _v[i-number];
		}
		for(int i=index; i < index+number; i++) //valorizzo i posti lasciati vuoti
		{
			_v[i] = value;
		}
		_size = newSize;
	}
	/** inserisce un nuovo elemento alla posizione 0 dell'array.<br>Shifta tutti gli altri elementi a destra */
	public void prepend( T value)								{this.insert(0, value);}
	/** rimuove l'elemento all'indice atIndex */
	public void remove(int atIndex)
	{
		if( atIndex < 0 || atIndex > _size)
			throw new IndexOutOfBoundsException("KVector::remove - index out of bound - index value == "+atIndex + " array size == "+_size);
		for(int i=atIndex; i < _size; i++){
			_v[i] = _v[i+1];
		}
		_v[_size-1] = null;
		--_size;
	}
	
	/**rimuove 'number' di elementi partendo dall'indice 'adIndex'(incluso).<br>Causa il resize */
	public void remove(int atIndex, int number)
	{
		if( atIndex < 0 || atIndex >= _size)
			throw new IndexOutOfBoundsException("KVector::remove - index out of bound - index value == "+atIndex + " array size == "+_size);
		if(number<=0)
			throw new InvalidParameterException("KVector::remove - invalid parameter 'number' == "+number +" , expected > 0");
		if(atIndex+number> _size)
			number = _size-atIndex;
		
		for(int i = atIndex+number; i < _size; i++){
			_v[i-number] = _v[i];	//copio a sinistra i valori da mantenere
		}
		this.resize(_size-number); //butto via tutto l'avanzo
	}
	
	public void replace(int atIndx, T value)
	{
		if( atIndx < 0 || atIndx >= _size)
			throw new IndexOutOfBoundsException("KVector::replace - index out of bound - index value == "+atIndx + " array size == "+_size);
		_v[atIndx] = value;
	}
	
	/** ordina l'array in modo crescente (dal più piccolo al più grande) utilizzando la funzione compare in input.<br>(bubble sort) */
	public void sort( Ptr_Function_Compare<T> p_compare )
	{
		for(int i = 0; i<_size-1; i++){
			for(int j = 0; j<_size-1-i; j++){
				T temp = (T) _v[j];
				T prox = (T)_v[j+1];
				if(p_compare.compare(temp, prox)> 0){
			          _v[j] = _v[j+1];
			          _v[j+1] = temp;
			     }
			 }
		}
	}
	
	public void swap(KVector<T> other)
	{
		Object[] temp = _v;
		int thisSize = _size;
		_size = other._size;
		_v = other._v;
		other._v = temp;
		other._size = thisSize;
	}
	
	// ------------------------------------------------------------------------- publici statici ------------------------------------------------------------------------- \\

	public static<T> KVector<T> fromList( List<T> list )
	{
		KVector<T> v = new KVector<T>(list.size());
		for(int i=0; i < list.size(); i++)
			v.append(list.get(i));
		return v;
	}
	
	// ------------------------------------------------------------------------- Interfacce ------------------------------------------------------------------------- \\
	@Override
	public KVectorIterator<T> iterator()
	{
		return new KVectorIterator<T>(this);
	}
	
}//EO KVector<T>



