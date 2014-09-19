package it.konga.framework;

import it.konga.framework.datastruct.KLinkedList;
import it.konga.framework.datastruct.KListIterator;

public class KTest
{
	public static void main(String[] args)
	{
		KLinkedList<String> list = new KLinkedList<String>();
		list.append("ciao");
		list.append("02");
		list.append("ma ma");
		list.append("questo è un test");
		list.append("sono io");
		
		System.out.println("----------------------------");
		System.out.println( list.size() );
		for (String string : list) {
			System.out.println( string);
		}
		System.out.println("----------------------------");
		KListIterator<String> itr = list.iterator();
		
		itr.next();
		itr.next();
		
		System.out.println( itr.getData() );
		list.replace(itr, "sostituita");
		
		System.out.println("----------------------------");
		System.out.println( list.size() );
		for (String string : list) {
			System.out.println( string);
		}
		System.out.println("----------------------------");
	}
}
