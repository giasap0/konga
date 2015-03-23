package it.konga.framework.kObjects.excel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.RandomAccess;

import it.konga.framework.kObjects.KAbstract_Dto;

public class DTO_ExcelSheet extends KAbstract_Dto  implements Iterable<DTO_TabellaExcel>, RandomAccess
{
	private static final long serialVersionUID = -5294501250365888805L;

	private static final int MIN_BUFFER_SIZE = 100;
	private static long counter = 0;
	
	private int maxLunghezzaIntestazione;
	private int numColonne;
	private boolean flagIntestazioniRighe;
	private ArrayList< DTO_TabellaExcel > tabelle = new ArrayList<DTO_TabellaExcel>(1);
	private String nomeSheet;
	/** numero di righe dello sheet */
	private int bufferSize;
	private int realNumberRows = 0;
	
	public DTO_ExcelSheet()
	{
		tabelle = new ArrayList<DTO_TabellaExcel>(1);		
		nomeSheet = "sheet"+(++counter);
		numColonne = 0;
		realNumberRows = 0;
		bufferSize = MIN_BUFFER_SIZE;
	}
	
	// **************************************************************************** publici **************************************************************************** \\
	
	
	@Override public Iterator<DTO_TabellaExcel> iterator()					{ return tabelle.iterator(); }
	public int getTablesNumber()											{ return tabelle.size(); }
	public void add(DTO_TabellaExcel t)										{ tabelle.add(t); }
	public DTO_TabellaExcel get(int i)
	{
		if(i<0 || i > tabelle.size() )
			throw new IndexOutOfBoundsException("DTO_ExcelSheet - indice non valido. indx == "+i+" , numero tabelle = "+ tabelle.size() );
		return tabelle.get(i);
	}

	public void setNumColonne_checkMax(int numColonne)
	{
		if(this.numColonne < numColonne)
			this.numColonne = numColonne;
	}
	
	public void updateBufferSize(int deltaBuffer)
	{
		if(deltaBuffer<= 0)
			return;
		
		realNumberRows += deltaBuffer;
		if(realNumberRows  < MIN_BUFFER_SIZE)
		{			
			return;
		}
		this.bufferSize = realNumberRows;
	}

	// **************************************************************************** getters e setters **************************************************************************** \\
	
	public ArrayList<DTO_TabellaExcel> getTabelle()							{ return tabelle; }
	public String getNomeSheet()											{ return nomeSheet; }
	public int getBufferSize()												{ return bufferSize; }
	public int getMaxLunghezzaIntestazione()								{ return maxLunghezzaIntestazione; }
	public int getNumColonne()												{ return numColonne; }
	public boolean isFlagIntestazioniRighe()								{ return flagIntestazioniRighe; }
	
	
	public void setTabelle(ArrayList<DTO_TabellaExcel> tabelle)				{ this.tabelle = tabelle; }
	public void setNomeSheet(String nomeSheet)								{ this.nomeSheet = nomeSheet; }
	public void setMaxLunghezzaIntestazione(int maxLunghezzaIntestazione)	{ this.maxLunghezzaIntestazione = maxLunghezzaIntestazione; }	
	public void setFlagIntestazioniRighe(boolean flagIntestazioniRighe)		{ this.flagIntestazioniRighe = flagIntestazioniRighe; }
	public void setNumColonne(int numColonne)								{ if(numColonne >= 0) this.numColonne = numColonne; else this.numColonne = 0; }
	public void setBufferSize(int bufferSize)
	{
		if(bufferSize > MIN_BUFFER_SIZE)
			this.bufferSize = bufferSize;
		else
			this.bufferSize = MIN_BUFFER_SIZE;
	}
	
	// **************************************************************************** overrides di object **************************************************************************** \\
	
	@Override public int hashCode()
	{
		int result = 31 * 1 + bufferSize;
		result = 31 * result + ((tabelle == null) ? 0 : tabelle.hashCode());
		return result;
	}
	@Override public boolean equals(Object obj)
	{
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (getClass() != obj.getClass()) { return false; }
		DTO_ExcelSheet other = (DTO_ExcelSheet) obj;
		if (bufferSize != other.bufferSize) { return false; }
		if (tabelle == null)
		{
			if (other.tabelle != null) { return false; }
		} else if (!tabelle.equals(other.tabelle)) { return false; }
		return true;
	}
}
