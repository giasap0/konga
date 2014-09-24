package it.konga.framework.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * classe helper per serializzare / deserializzare oggetti da file e/o stream
 *  @author Giampaolo Saporito
 * @Date 05/09/2014
 */
public class KSerializator
{
	/**
	 * fa una deep copy dell'oggetto utilizzando la serializzazione.<br>
	 * Torna null se qualcosa va male
	 * @param obj oggetto di cui fare la copia
	 * @return clone dell'oggetto
	 */
	@SuppressWarnings("unchecked")
	public static<T> T deepClone(T obj)
	{
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			return (T) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			return null;
		}
	}
	
	/**
	 * deserializza l'oggetto dallo stream<br>
	 * ricordarsi di chiudere lo stream
	 * @param iStream input stream nel quale è presente l'oggetto
	 * @return oggetto voluto
	 */
	public static<T> T deserialize(InputStream iStream) throws IOException, ClassNotFoundException
	{
		ObjectInputStream ois = new ObjectInputStream(iStream);
		@SuppressWarnings("unchecked")
		T obj = (T) ois.readObject();
		ois.close();
		return obj;
	}
	/**
	 * deserializza l'oggetto dal file con questo nome
	 * @param fileName nome del file da cui prendere l'oggetto serializzato
	 * @return oggetto voluto
	 */
    public static<T> T deserialize(String fileName) throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(fileName);
        ObjectInputStream ois = new ObjectInputStream(fis);
        @SuppressWarnings("unchecked")
		T obj = (T) ois.readObject();
        ois.close();
        fis.close();
        return obj;
    }

    /**
     * serializza l'oggetto e lo salva sullo stream<br>
     * ricordarsi di chiudere lo stream
     * @param obj oggetto da serializzare
     * @param oStream output stream
     */
 	public static void serialize(Object obj, OutputStream oStream) throws IOException
 	{
 		ObjectOutputStream oos = new ObjectOutputStream(oStream);
 		oos.writeObject(obj);
 		oos.close();
 	}

	/**
	 * serializza l'oggetto e lo salva in un file
	 * @param obj oggetto da serializzare
	 * @param fileName nome del file da creare (o sul quale andare a scrivere)
	 */
	public static void serialize(Object obj, String fileName) throws IOException
	{
		FileOutputStream fos = new FileOutputStream(fileName);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(obj);
		oos.close();
		fos.close();
	}
}
