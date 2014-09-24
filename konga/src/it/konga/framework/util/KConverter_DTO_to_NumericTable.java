package it.konga.framework.util;

import it.konga.framework.kObjects.KAbstract_Dto;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.InvalidParameterException;
import java.util.List;

/**
 * Oggetto per estrarre da un array di DTO una matrice di double.<br>
 * L'utilizzo è quello di andare ad inserire questa matrice in una struttura tabellare (html, xml, excel, eccetera)<br>
 * <h3>IMPORTANTE : </h3> ricordarsi di chiamare il metodo initialize.<br>
 * Farsi tornare la tabella tramite getTable(), oppure accedere direttamente agli elementi desiderati tramite getValue(row, column)
 * 
 * @author Giampaolo Saporito
 * @Date 02.09.2014
 */
public class KConverter_DTO_to_NumericTable<DTO extends KAbstract_Dto > {

    private Method[] _metodi;
    private List<DTO> _listaOggetti;
    private Double[][] table;
    
    /**
     * Oggetto per estrarre da un array di DTO una matrice di stringhe di dati.<br>
     * L'utilizzo è quello di andare ad inserire questa matrice in una struttura tabellare (html, xml, excel, eccetera)<br>
     * <h3>IMPORTANTE : </h3> ricordarsi di chiamare il metodo initialize.<br>
     * Farsi tornare la tabella tramite getTable(), oppure accedere direttamente agli elementi desiderati tramite getValue(row, column)
     */
    public KConverter_DTO_to_NumericTable()
    {
    }
    
    public void initialize( List<DTO> listaOggetti, List<String> metodiDaInvocare) throws Exception
    {
		this._listaOggetti = listaOggetti;
		initMethods(metodiDaInvocare);
		buildTable();
    }
    
    public Double[][] getTable() {return table;}
    
    /**
     * accedi al valore della tabella nella posizione (riga, colonna)
     * @param row indice di riga, parte da 0
     * @param column indice di colonna, parte da 0
     * @return stringa nella posizione deisderata
     */
    public Double getValue(int row, int column) {return table[row][column];}
    
    // *************************************** METODI PRIVATI *************************************** \\
    
    private void buildTable() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NumberFormatException
    {
		table = new Double[_listaOggetti.size()][];
		Object ret = null;
		for(int r = 0; r < _listaOggetti.size(); r++)
		{
		    table[r] = new Double[_metodi.length];
		    for(int c=0; c < _metodi.length; c++)
		    {
			ret = _metodi[c].invoke(_listaOggetti.get(r) );
			if( ret == null )
			    table[r][c] = 0.0;
			else if( ret instanceof Double)
			    table[r][c] = (Double) ret;
			else if( ret instanceof Number)
			    table[r][c] = ((Number) ret).doubleValue();
			else if( ret instanceof String) //prova a fare parse
			{
			    table[r][c] =  Double.parseDouble((String) ret);
			}
		    }
		}
    }
    
    private void initMethods(List<String> metodiDaInvocare) throws NullPointerException, SecurityException, NoSuchMethodException
    {
		if(metodiDaInvocare == null || _listaOggetti == null)
		    throw new NullPointerException();
		if(metodiDaInvocare.size() <= 0)
		    throw new InvalidParameterException("la lista dei metodi da invocare è vuota");
		
		Class<? extends KAbstract_Dto> classe = _listaOggetti.get(0).getClass();
		_metodi = new Method[metodiDaInvocare.size()];
		int i=0;
		for (String nomeMetodo : metodiDaInvocare)
		{
		    _metodi[i++] = classe.getMethod(nomeMetodo);
		}
    }
}

