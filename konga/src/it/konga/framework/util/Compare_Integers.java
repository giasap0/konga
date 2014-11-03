package it.konga.framework.util;

import it.konga.framework.interfaces.Ptr_Function_Compare;

/**
 * Implements Ptr_Function_Compare<Integer>
 * @author Giampaolo Saporito
 *
 */
public class Compare_Integers implements Ptr_Function_Compare<Integer>, Cloneable
{
	@Override
	public int compare(Integer left, Integer right) {
		if(left < right)
			return -1;
		else if(left > right)
			return 1;
		else
			return 0;
	}

}
