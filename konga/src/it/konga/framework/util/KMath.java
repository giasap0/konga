package it.konga.framework.util;

public class KMath
{

	/**
	 * fa potenze intere, positive di 10<br> più veloce di Math.pow(double,dobule)
	 * @param esponente esponente. Deve essere un numero positivo
	 * @return return 10^esponente
	 * @throws NumberFormatException lancia una eccezione se l'esponente è negativo
	 */
	public static long pow10(int esponente) throws NumberFormatException
	{	
		if(esponente < 0)
		{
			System.out.println("***** it.gruppodatel.wsmc.util.Utility.pow10(int) - l'esponente non può essere negativo");
			throw new NumberFormatException();
		}
		if(esponente==0)
			return 1;
		if(esponente==1)
			return 10;
		long result = 1L;	
		
		for(int i=1; i <= esponente; i++)
		{
			result = result*10L;
		}
		return result;
	}
}
