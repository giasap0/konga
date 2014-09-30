package it.konga.framework.datastruct.interfaces;

import it.konga.framework.datastruct.KBTree;

/**
 * in pratica rappresenta un puntatore a funzione per il Binary Tree.<br>
 * Implementare i metodi per passarli alle funzioni di iterazione sui nodi
 * @author Giampaolo Saporito
 */
public interface Ptr_Function_KBTree<T>
{
	/**
	 * fai una operazione sul nodo, modificandolo
	 * @param nodo nodo da modificare 
	 */
	public void operation( KBTree<T> nodo );
}
