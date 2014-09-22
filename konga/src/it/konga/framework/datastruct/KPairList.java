package it.konga.framework.datastruct;

import java.util.ArrayList;

/**
 * Semplicemente una lista di coppie di elementi di tipo E,T<br>
 * Iterabile nel forEach<br>
 * Ogni elemento è costituito da un Pair<E,T> <br>
 * Estende linked list: si consigli di utilizzare un iteratore
 * 
 * @author Giampaolo Saporito
 * @Date 05/09/2014
 *
 * @param <E> tipo del primo elemento
 * @param <T> tipo del secondo elemento
 */
public class KPairList<E,T> extends KLinkedList< KPair<E,T> >
{

	private static final long serialVersionUID = -3547461809320836828L;

	// ----------------------------------------------------------------------------------- costruttori ----------------------------------------------------------------------------------- \\
	public KPairList()	{super();}
	
	// ----------------------------------------------------------------------------------- metodi publici ----------------------------------------------------------------------------------- \\
    
	   /**
	    * inserisce una nuova coppia first-second alla fine della lista
	    * @param first primo elemento della coppia
	    * @param second secondo elemento della coppia
	    * @return true (as specified by Collection.add)
	    */
	    public void append(E first,  T second)
	    {
	    	KPair<E,T> p = new KPair<E, T>(first, second);
	    	super.append(p);
	    }
	    
	    /**
	     * costruisce una coppiafirst-second, e la inserisce all'indice index
	     * @param itr posizione in cui inserire la coppia
	     * @param first primo elemento della coppia
	     * @param second secondo elemento della coppia
	     */
	    public void insert(KListIterator<KPair<E, T>> itr, E first , T second)
	    {
	    	KPair<E,T> p = new KPair<E, T>(first, second);
	    	super.insert(itr, p);
	    }
	    
	    /**
	     * Una lista composta da tutti gli elementi 'first' delle coppie di valori
	     */
	    public ArrayList<E> getFirstsAsList()
	    {
	    	ArrayList<E> lista = new ArrayList<E>(this.size() );
	    	for (KPair<E,T> coppia : this) {
	    		lista.add(coppia.getFirst());
			}
	    	return lista;
	    }
	    
	    /**
	     *  Una lista composta da tutti gli elementi 'second' delle coppie di valori
	     */
	    public ArrayList<T> getSecondsAsList()
	    {
	    	ArrayList<T> lista = new ArrayList<T>(this.size() );
	    	for (KPair<E,T> coppia : this) {
	    		lista.add(coppia.getSecond());
			}
	    	return lista;
	    }
	    
	    /**
	     * rimuove tutti gli elementi negli indici passati come valori<br>
	     * <h3>WARNING</h3> gli indici devono essere passati in ordine crescente (dal più piccolo al più grande)<br>
	     * Essendo questa una lista linkata l'operazione è veloce e non comporta riallocazioni di memoria
	     * @param indiceToRemove indici nelle cui posizioni eliminare gli elementi. Gli indici devono essere in ordine crescente
	     */
	    public void removeAtIndices(int...indiceToRemove)
	    {
	    	KListIterator<KPair<E, T>> it = iterator(indiceToRemove[0]);
	    	int j=0;
	    	for(int i=indiceToRemove[0]; i <= indiceToRemove[indiceToRemove.length-1] && j< indiceToRemove.length; i++)
	    	{
	    		it.next();
	    		if (i == indiceToRemove[j])
	    		{
	    			it.remove();
	    			++j;
	    		}
	    	}
	    }
	    
	    // ------------------------------------------------------------------------------------------ Override ------------------------------------------------------------------------------------------ \\

	    /**
	     * Rimuove dalla lista tutti gli elementi fa 'fromIndex' (incluso) e 'toIndex' (ESCLUSO).<br>
	     * L'elemento all'indice 'toIndex' verrà linkato all'elemento precedente, tutta la lista è come se venisse shiftata verso sinistra.<br>
	     * Questa chiamata riduce il size della lista di (toIndex - fromIndex).<br>
	     * (Se toIndex==fromIndex, questa operazione non ha alcun effetto) <br>
	     * Essendo questa una lista linkata l'operazione è veloce e non comporta riallocazioni di memoria
	     * @param fromIndex indice del primo elemento da rimuovere
	     * @param toIndex indice successivo all'ultimo elemento da rimuovere
	     */
	    @Override
	    public void removeRange(int fromIndex, int toIndex)
	    {
	    	super.removeRange(fromIndex, toIndex);
	    }
		
	    /**
	     * crea una nuova lista, facendo deep copy di tutti gli elementi
	     * @return deep copy
	     */
		@Override
	    public KPairList<E, T> clone()
	    {
			KPairList<E, T> copy = new KPairList<E, T>();
	    	for (KPair<E, T> pair : this) {
	    		copy.append(pair.clone());
			}
	    	
	    	return copy;
		};
			
	    // ------------------------------------------------------------------------------------------ Statici ------------------------------------------------------------------------------------------ \\
	    
		/**
		 * metodo statico per fare una deep copy di una lista passata in input
		 * @param list lista dalla quale copiare gli elementi
		 * @return nuova lista che contiene una coppia di tutti gli elementi della lista in input
		 */
	    public static<E,T> KPairList<E, T> copyList(KPairList<E, T> list)
	    {
	    	KPairList<E, T> copy = new KPairList<E, T>();
	    	for (KPair<E, T> pair : list) {
				copy.append(pair.clone() ) ;
			}
	    	
	    	return copy;
	    }
	    
	    // ----------------------------------------------------------------------------------- metodi delle interfacce ----------------------------------------------------------------------------------- \\

	    //Iterable
	    @Override
	    public KListIterator<KPair<E, T>> iterator()
	    {
	    	return super.iterator();
	    }
	    
	    /**
	     * ritorna un iteratore posizionato nella cella precedente ad index. <br>
	     * Chiamare iterator.next() per farsi tornare l'elemento alla posizione index
	     * @param index indice in cui andare a posizioneare l'iteratore
	     * @return iteratore nella posizione desiderata
	     */
	    @Override
	    public KListIterator<KPair<E,T>> iterator(int index)
	    {
	    	return super.iterator(index);
	    }
	    
}//EO KPairList<E,T>
