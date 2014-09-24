package it.konga.framework.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * 
 *  @author Giampaolo Saporito
 * @Date 05/09/2014
 *
 */
public class KFormatter
{
	/**
	 * <p>formatta un double in una stringa, approssimando dopo un certo numero di decimali</p>
	 * @param numero numero da formattare
	 * @param numeroDecimali numero di decimali che si vogliono vedere nella stringa
	 * @return numero formattato sotto forma di stringa
	 * @author Giampaolo Saporito
	 * @Date 24.09.2014
	 */
	public static String numberFormatter(Double numero, Integer numeroDecimali)
	{
		return numberFormatter(String.valueOf(numero), numeroDecimali);
	}
	
	/**
	 * <p>formatta un double in una stringa, approssimando dopo un certo numero di decimali</p>
	 * @param numero numero da formattare
	 * @param numeroDecimali numero di decimali che si vogliono vedere nella stringa
	 * @return numero formattato sotto forma di stringa. Torna null se la stringa di input non può essere parsificata in un numero
	 * @author Giampaolo Saporito
	 * @Date 24.09.2014
	 */
	public static String numberFormatter(String numero, Integer numeroDecimali)
	{
		if (numero==null || numero.trim().equalsIgnoreCase("null")) return "";
		if (numero.trim().equalsIgnoreCase("N.D.")) return "N.D.";
		if (numero.trim().equalsIgnoreCase("N.C.")) return "N.C.";
		
		double numDouble;
		try {
			numDouble = Double.parseDouble(numero);
		} catch (NumberFormatException e) {
			System.out.println("----------------------------------------------");
			System.out.println("number format Exception: Formatter.numberFormatter(String numero, in numeroDecimali).\nLA stringa in input non è un numero");
			e.printStackTrace();
			System.out.println("----------------------------------------------");
			return null;
		}
		if(numeroDecimali == null || numeroDecimali <0)
			numeroDecimali= new Integer(0);
		long digits = KMath.pow10(numeroDecimali);
		numDouble = Math.round(numDouble* digits);
		numero = String.valueOf(numDouble/digits);
		
		NumberFormat  formatter = DecimalFormat.getNumberInstance(Locale.ITALIAN);
		if(numeroDecimali ==0)
			formatter.setParseIntegerOnly(true);
		else
			formatter.setMinimumFractionDigits(numeroDecimali );
		return  formatter.format(Double.parseDouble(numero));
	}
}
