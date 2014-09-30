package it.konga.framework.interfaces;

/**
 * equivalente di un puntatore a funzione.<br>
 * Utilizzata per il metodo compare<br>
 * <ul> <li> < 0 --> left < right </li> <li>>0 --> left>right </li> <li>== 0 --> left==right </li> </ul> 
 * @author Giampaolo Saporito
 */
public interface Ptr_Function_Compare<T>
{
	/** <ul> <li> < 0 --> left < right </li> <li>>0 --> left>right </li> <li>== 0 --> left==right </li> </ul> */
	public int compare(T left, T right);
}