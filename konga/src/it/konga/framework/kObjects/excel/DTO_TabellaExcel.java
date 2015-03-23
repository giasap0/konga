package it.konga.framework.kObjects.excel;

import java.util.ArrayList;

import it.konga.framework.kObjects.KAbstract_Dto;
import it.konga.framework.util.excel.IStrutturaExcel;


/**
	#
	#
	#	intestazione (optional)
	#
	#
	
	________________________________________________________
	|														|
	|			header (infoColonne, optional)				|				
	|_______________________________________________________|
	|	righe	|											|
	|(opzional)	|											|
	|			|			matrice Valori					|
	|			|											|
	|			|											|

*/
public class DTO_TabellaExcel extends KAbstract_Dto implements IStrutturaExcel
{
	private static final long serialVersionUID = 1977433823003495653L;
	private ArrayList<String> intestazione;
	private ArrayList<String> nomiRighe;
	private ArrayList<DTO_InformazioniColonna>  infoColonne;	
	private String[][] matriceValori;
	
	public DTO_TabellaExcel()
	{
		matriceValori = new String[1][1];
		matriceValori[0][0] = "";
	}
	
	public DTO_TabellaExcel( IStrutturaExcel tabella )
	{
		this.setDati(tabella.getDati() );
		this.setInformazioniColonne( tabella.getInformazioniColonne() );
		this.setNomiRighe( tabella.getNomiRighe() );
		this.setStringheIntestazione( tabella.getStringheIntestazione() );
	}

	@Override public ArrayList<String> getStringheIntestazione()					{ return intestazione;  }
	@Override public ArrayList<DTO_InformazioniColonna> getInformazioniColonne()	{ return infoColonne; }
	@Override public ArrayList<String> getNomiRighe()								{ return nomiRighe; }
	@Override public String[][] getDati()											{ return matriceValori; }
	

	public void setStringheIntestazione(ArrayList<String> intestazione)					{ this.intestazione = intestazione; }	
	public void setInformazioniColonne(ArrayList<DTO_InformazioniColonna> infoColonne)	{ this.infoColonne = infoColonne; }
	public void setNomiRighe(ArrayList<String> nomiRighe)								{ this.nomiRighe = nomiRighe; }
	public void setDati(String[][] matriceValori)										{ this.matriceValori = matriceValori; }

}//EO DTO_TabellaExcel
