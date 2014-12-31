package it.konga.framework.kObjects;

import java.io.InvalidObjectException;
import java.io.ObjectInputValidation;
import java.io.Serializable;

/**
 * Classe base per i DTO.<br>
 * Utile come gerarchica base
 * @author Giampaolo Saporito
 * @date 24-09-2014
 */
public abstract class KAbstract_Dto implements Serializable, ObjectInputValidation
{
	private static final long serialVersionUID = 7163853062626L;
	private Long id;

	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}

	@Override
	public void validateObject() throws InvalidObjectException
	{
		//validare l'oggetto
		if( getId() < 0) throw new InvalidObjectException("L'id deve essere positivo");	
	}

}
