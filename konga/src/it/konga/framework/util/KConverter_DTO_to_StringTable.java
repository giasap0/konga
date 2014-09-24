package it.konga.framework.util;

import it.konga.framework.kObjects.KAbstract_Dto;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.InvalidParameterException;
import java.util.List;

/**
 * Oggetto per estrarre da un array di DTO una matrice di stringhe di dati.<br>
 * Ogni elemento dell'array sarà una riga della matrice <br>
 * L'utilizzo è quello di andare ad inserire questa matrice in una struttura tabellare (html, xml, excel, eccetera)<br>
 * <h3>IMPORTANTE : </h3> ricordarsi di chiamare il metodo initialize.<br>
 * Farsi tornare la tabella tramite getTable(), oppure accedere direttamente agli elementi desiderati tramite getValue(row, column)
 * 
 * @author Giampaolo Saporito
 * @Date 02.09.2014
 */
public class KConverter_DTO_to_StringTable<DTO extends KAbstract_Dto > {

    private Method[] _metodi;
    private List<DTO> _listaOggetti;
    private String[][] table;
    
    /**
     * Oggetto per estrarre da un array di DTO una matrice di stringhe di dati.<br>
     *  Ogni elemento dell'array sarà una riga della matrice <br>
     * L'utilizzo è quello di andare ad inserire questa matrice in una struttura tabellare (html, xml, excel, eccetera)<br>
     * <h3>IMPORTANTE : </h3> ricordarsi di chiamare il metodo initialize.<br>
     * Farsi tornare la tabella tramite getTable(), oppure accedere direttamente agli elementi desiderati tramite getValue(row, column)
     */
    public KConverter_DTO_to_StringTable()
    {
    }
    
    /**
     * inizializza l'oggetto costruendo la matrice di stringhe a partire da una lista di DTO , chiamando tutti i metodi passati in input
     * @param listaOggetti lista di oggetti , sui quali chiamare i metodi per ottenere le stringhe da inserire nella matrice
     * @param metodiDaInvocare nomi dei metodi da invocare per ogni oggetto della lista
     * @throws Exception generati dalla reflection a runTime qualora i metodi da invocare non fossero corrispondenti alla classe del DTO
     */
    public void initialize( List<DTO> listaOggetti, List<String> metodiDaInvocare) throws Exception
    {
		this._listaOggetti = listaOggetti;
		initMethods(metodiDaInvocare);
		buildTable();
    }
    
    public String[][] getTable() {return table;}
    
    /**
     * accedi al valore della tabella nella posizione (riga, colonna)
     * @param row indice di riga, parte da 0
     * @param column indice di colonna, parte da 0
     * @return stringa nella posizione deisderata
     */
    public String getValue(int row, int column) {return table[row][column];}
    
    // *************************************** METODI PRIVATI *************************************** \\
    
    private void buildTable() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException
    {
	table = new String[_listaOggetti.size()][];
	Object ret = null;
	for(int r = 0; r < _listaOggetti.size(); r++)
	{
	    table[r] = new String[_metodi.length];
	    for(int c=0; c < _metodi.length; c++)
	    {
		ret = _metodi[c].invoke(_listaOggetti.get(r) );
		if(ret == null)
		    table[r][c]= "";
		else if( ret instanceof String)
		    table[r][c] = (String) ret;
		else
		    table[r][c] = ret.toString();
	    }
	}
    }
    
    private void initMethods(List<String> metodiDaInvocare) throws NullPointerException, SecurityException, NoSuchMethodException
    {
	if(metodiDaInvocare == null || _listaOggetti == null)
	    throw new NullPointerException("Converter_Dto_ToStringTable::initMethods() - parametro in input == null");
	if(metodiDaInvocare.size() <= 0)
	    throw new InvalidParameterException("Converter_Dto_ToStringTable::initMethods() - la lista dei metodi da invocare è vuota");
	
	Class<? extends KAbstract_Dto> classe = _listaOggetti.get(0).getClass();
	_metodi = new Method[metodiDaInvocare.size()];
	int i=0;
	for (String nomeMetodo : metodiDaInvocare)
	{
	    _metodi[i++] = classe.getMethod(nomeMetodo);
	}
    }
    
}//EO Converter_Dto_To_Table