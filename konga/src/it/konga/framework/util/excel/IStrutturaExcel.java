package it.konga.framework.util.excel;

import java.util.ArrayList;

import it.konga.framework.kObjects.DTO_InformazioniColonna;

/** Interfaccia publica per le strutture dati degli excel.
 * Le classi che la implementano sono classi wrapper fra la ( o le) liste di DTO e la rappresentazione tabellare del foglio excel
 * @author Giampaolo Saporito
 *
 */
public interface IStrutturaExcel {
	public String[][] getDati(); 
	public ArrayList<DTO_InformazioniColonna> getInformazioniColonne();
	public ArrayList<String> getStringheIntestazione();	
}
