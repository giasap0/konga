package it.konga.framework.zObjects;

import java.io.InvalidObjectException;
import java.io.ObjectInputValidation;
import java.io.Serializable;

/**
 * classe base per i DTO.<br>
 * Utile sia come base gerarchica sia come lookUp per implementare la serializzazione
 * @author Giampaolo
 *
 */
public class KAbstract_Dto implements Serializable, ObjectInputValidation
{
	private static final long serialVersionUID = 7163816705753062626L;
	private static Integer id;

	public static Integer getId(){
		return id;
	}
	public static void setId(Integer id){
		KAbstract_Dto.id = id;
	}
	
	private void readObject(java.io.ObjectInputStream stream) throws java.io.IOException, ClassNotFoundException
	{
		stream.defaultReadObject();
		setId(stream.readInt());
		//setXXX( (cast) stream.readObject() );
	}
	
	private void writeObject(java.io.ObjectOutputStream stream) throws java.io.IOException
	{
		//l'ordine in cui si legge e si scrive è lo stesso
		stream.defaultWriteObject();
		stream.writeInt( getId() );
		//stream.writeObject( getXXX() );
	}

	@Override
	public void validateObject() throws InvalidObjectException
	{
		//validare l'oggetto
		if( getId() < 0) throw new InvalidObjectException("L'id deve essere positivo");	
	}
}
