package it.konga.framework.datastruct;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Classe che consente un accoppiamento di due oggetti.<br>
 * Chiamare getFirst() o getSecond() per recuperarli
 * 
 * @author Giampaolo Saporito
 * @Date 05/09/2014
 *
 * @param <E> tipo del primo oggetto
 * @param <T> tipo del secondo oggetto
 */
public class KPair<E,T> implements Serializable, Cloneable
{
// campi privati
    
    private static final long serialVersionUID = -2937309031138670967L;
    
    private E first;
    private T second;
    
    // ------------------------------------------------------------------------------------------ costruttori ------------------------------------------------------------------------------------------ \\
    
    public KPair() {
	first = null;
	second = null;
    }

    public KPair(E first, T second)
    {
	this.first = first;
	this.second = second;
    }
    
    // ------------------------------------------------------------------------------------------ metodi publici ------------------------------------------------------------------------------------------ \\
    
    @Override
    public int hashCode()
    {
	final int prime = 193;
	int result = prime + ((first == null) ? 0 : first.hashCode());
	result = prime * result + ((second == null) ? 0 : second.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj)
    {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	
	@SuppressWarnings("unchecked")
	KPair<E,T> other = (KPair<E, T>) obj;
	if (first == null)
	{
	    if (other.first != null)
		return false;
	}else if (!first.equals(other.first))
	    return false;
	if (second == null)
	{
	    if (other.second != null)
		return false;
	} else if (!second.equals(other.second))
	    return false;
	return true;
    }
    
    //il modo migliore per clonare un oggetto è quello di serializzarlo e fare una copia byte per byte
    //perchè essendoci i generici , i campi first e second potrebbero o meno aver implementato clone() in modo corretto. Loro stessi potrebbero essere collezioni o altri tipi di oggetti composti
    //così facendo siamo sicuri di fare una deep copy
    /**
     * implementato tramite serializzazione
     */
    @SuppressWarnings("unchecked")
	@Override
    public KPair<E,T> clone()
    {
    	ObjectOutputStream oStream = null;
    	ObjectInputStream iStream = null;
    	try
    	{
    		ByteArrayOutputStream byteStreamOut = new ByteArrayOutputStream();
    		oStream = new ObjectOutputStream(byteStreamOut);
    		oStream.writeObject(this);
    		iStream = new ObjectInputStream( new ByteArrayInputStream(byteStreamOut.toByteArray()));
    		return (KPair<E,T>)iStream.readObject();
    	}
    	catch (Exception e) {
    		throw new AssertionError(e);
		}
    	finally
    	{
    		try{
    			if(oStream != null)
    				oStream.close();
    			if(iStream != null)
    				iStream.close();
    		}
    		catch (IOException e) {
			}
    	}
    }
      
    @Override
    public String toString() {
	return "Pair " + hashCode() +" [first=" + first + ", second=" + second + "]";
    }
    
    // ------------------------------------------------------------------------------------------ metodi statici ------------------------------------------------------------------------------------------ \\

    /**
     * metodo factory che crea un Pair con first di tipo A e second di tipo B
     * @param first primo elemento
     * @param second secondo elemento
     * @return il Pair costruito
     */
    public static<A,B> KPair<A,B> makePair(A first, B second)
    {
	return new KPair<A,B>(first, second);
    }
    
    // ------------------------------------------------------------------------------------------ getters e setters ------------------------------------------------------------------------------------------ \\

    public E getFirst() {
        return first;
    }

    public void setFirst(E first) {
        this.first = first;
    }

    public T getSecond() {
        return second;
    }

    public void setSecond(T second) {
        this.second = second;
    }
    
    // ------------------------------------------------------------------------------------------ serializable ------------------------------------------------------------------------------------------ \\
    
    /**
     * Reconstitute this <tt>LinkedList</tt> instance from a stream (that is
     * deserialize it).
     */
     @SuppressWarnings("unchecked")
	private void readObject( ObjectInputStream aInputStream) throws ClassNotFoundException, IOException
     {
       aInputStream.defaultReadObject(); // Read in any hidden serialization magic

       first = (E) aInputStream.readObject();
       second = (T) aInputStream.readObject(); 
    }

      /**
      * Save the state of this <tt>Pair</tt> instance to a stream (that is, serialize it).
      */
      private void writeObject(ObjectOutputStream outStream) throws IOException
      {
    	  outStream.defaultWriteObject(); 	//perform the default serialization for all non-transient, non-static fields
    	  
    	  outStream.writeObject(first);
    	  outStream.writeObject(second);
      }
} // EO class KPair<E,T>
