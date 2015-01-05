package it.konga.framework.kObjects;

/** contiene informazioni di testata e di formattazione delle celle rispettive ad una colonna, ereditare da questa classe se si vuole personalizzare */
public class DTO_InformazioniColonna extends KAbstract_Dto
{
	private static final long serialVersionUID = -550802203816641479L;
	
	protected String nomeColonna;
	protected String getter;
	protected KTipoDatoExcel tipoDato;
	
	public DTO_InformazioniColonna() {}
	
	public DTO_InformazioniColonna(String nomeColonna, String getter, KTipoDatoExcel tipoDato)
	{
		this.nomeColonna = nomeColonna;
		this.getter = getter;
		this.tipoDato = tipoDato;
	}


	// ************************************************************************ getters e setters ************************************************************************ \\
	/** nome da assegnare alla colonna */
	public String getNomeColonna()						{ return nomeColonna; }
	/** getter da chiamare per questa colonna */		
	public String getGetter()							{ return getter; }
	/** tipo dato - serve per la formattazione */		
	public KTipoDatoExcel getTipoDato()					{ return tipoDato;  }
	
	/** nome da assegnare alla colonna */
	public void setNomeColonna(String nomeColonna)		{ this.nomeColonna = nomeColonna; }
	/** getter da chiamare per questa colonna */
	public void setGetter(String getter)				{ this.getter = getter; }
	/** tipo dato - serve per la formattazione */
	public void setTipoDato(KTipoDatoExcel tipoDato)		{ this.tipoDato = tipoDato; }
		
}//EO DTO_InformazioniColonna
