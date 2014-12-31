package it.konga.framework.interfaces;

import java.util.Collection;
import java.util.Iterator;

public interface KList<T> extends Iterable<T>
{
	/** numero di elementi nella lista */
	public int size();
	/** return number of list's elements with value.equals(value) */
	public int count(T value);
	/**cancella tutti gli elementi nella lista*/
	public void clear();
	/** primo elemento */
	public T first();
	/** reference all'ultimo lemento */
	public T last();
	/** vero se non ci sono elementi */
	public boolean isEmpty();
	/** Non agisce su elementi null, se l'input == null torna false */
	public boolean contains(T item);
	
	/** insert value at last position<br>return this */
	public KList<T> append(T value);
	/** insert values at last position<br>return this */
	public KList<T> append(KList<? extends T> other);
	
	public KList<T> append(Collection<? extends T> list);

	/**riempie la lista con valori == 'value'.<br>Torna this */
	public KList<T> fill(T value);
	
	/** Inserisce un elemento all'inizio della lista */
	public void prepend( T value);	
	
	/** ordina la lista in modo crescente (dal più piccolo al più grande) utilizzando la funzione compare in input. */
	public void sort( Ptr_Function_Compare<T> p_compare );
	
	// ========================================================================= accesso ========================================================================= 

	public Iterator<T> begin();
	public Iterator<T> end();
	/** Torna l'iteratore che punta all'elemento con indice indx. L'indice parte da 0
	 * @param indx parte da 0
	 * @return iteratore
	 */
	public Iterator<T> iterator(int indx)  ;

	/** Inserts an item AFTER the current iterator (append if iterator is invalid)
	 * @param itr iteratore posizione
	 * @param value valore da inserire
	 */
	void insert(Iterator<T> itr, T value);

	/**
	 * Rimuove l'elemento puntato dall'iteratore.<br>
	 * L'iteratore punterà al nodo successivo.
	 * @param itr iteratore della lista
	 */
	public T remove(Iterator<T> itr);
	public T removeFirst();
	public T removeLast();
	
	/**
	 * Rimuove tutti gli elementi con indce incluso nell'intervallo passato in input.<br>
	 * I limiti sono INCLUSIVI<br>
	 * Gli indici partono da 0 e non possono assumere lo stesso valore<br>
	 * ToIndex deve essere > fromIndex
	 * @param fromIndex primo indice da cancellare. Parte da 0
	 * @param toIndex ultimo indice da cancellare. Parte da 0
	 */
	public void removeRange(int fromIndex, int toIndex) ;

	public void replace(Iterator<T> itr, T newValue);

	/**
	 * Torna un iteratore che punta al primo oggetto.equals(item).<br>
	 * Se nessun oggetto è presente con questa caratteristica torna null
	 * @param item oggetto con cui fare equals
	 * @return iteratore nella posizione
	 */
	public Iterator<T> iteratorOf(T item);
}
