package it.konga.framework.util.format;

/** Indica metodi per formattare il KTime in stringhe.<br>Il separatore di default è ':' , se lo si vuole cambiare usare il setSeparator() 
 * <h3>Agire sul parametro boolean 'overflow' per cambiare il metodo di arrotondamento del valore più significativo. </h3>
 * **/
public enum KTimeFormat {

	/** stamperà stringa vuota */
	INVALID("",true),
	/** hh:mm:ss:zzz */
	HH_MM_SS_ZZZ(":",true),
	/** hh:mm:ss */
	HH_MM_SS(":",true),
	/** hh:mm */
	HH_MM(":",true),
	/** numero intero di ore */
	HH(":",true),
	/** numero di ore in virgola mobile */
	HH00(":",true),
	/** mm:ss:zzz */
	MM_SS_ZZZ(":",true),
	/** mm:ss */
	MM_SS(":",true),
	/** numero intero di minuti */
	MM(":",true),
	/** numero di minuti in virgola mobile */
	MM00(":",true),
	/** ss:zzz */
	SS_ZZZ(":",true),
	/** numero intero di secondi */
	SS(":",true),
	/** numero di secondi in virgola mobile */
	SS00(":",true),
	/** millisecondi */
	ZZZ(":",true);

	private String separator;
	private boolean overflow;
	KTimeFormat(String separator, boolean overflow)
	{
		this.setSeparator(separator); this.setOverflow(overflow);
	}

	@Override
	public String toString()
	{
		switch (this) {
		case HH_MM_SS_ZZZ: return "hh"+separator+"mm"+separator+"ss"+separator+"zzz";
		case HH_MM_SS: return "hh"+separator+"mm"+separator+"ss";
		case HH_MM: return "hh"+separator+"mm"+separator;
		case HH: return "hh";
		case HH00: return "hh.00";
		case MM_SS_ZZZ: return "mm"+separator+"ss"+separator+"zzz";
		case MM_SS: return "mm"+separator+"ss";
		case MM: return "mm";
		case MM00: return "mm.00";
		case SS_ZZZ: return "ss"+separator+"zzz";
		case SS: return "ss";
		case SS00: return "ss.00";
		case ZZZ: return "zzz";
		default:
			return "";
		}
	}//EO toString

	/** separatore fra ore, minuti, secondi, millisecondi */
	public String getSeparator() {
		return separator;
	}
	/**fai il set del separatore fra ore, minuti, secondi, millisecondi */
	public void setSeparator(String separator) {
		this.separator = separator;
	}

	/** overflow indica se la cifra più significativa deve essere troncata al suo valore assoluto o deve indicare anche i valori più significativi (eventualmente nascosti)<br>
	 * Ad Esempio:<br>
	 * Se si ha il Tempo 02:01:00 (in hh:mm:ss) e lo si vuole stampare come mm:ss<br>
	 * <ul><li> Se Overflow == false : stampa '01:00'</li>
	 * <li>Se Overflow == true :  stampa '121:00'</li></ul>
	 * Questo parametro è sempre considerato true se si richiede un formato in virgola mobile<br>
	 * Di default è impostato a true
	 */
	public boolean getOverflow() {
		return overflow;
	}

	/** overflow indica se la cifra più significativa deve essere troncata al suo valore assoluto o deve indicare anche i valori più significativi (eventualmente nascosti)<br>
	 * Ad Esempio:<br>
	 * Se si ha il Tempo 02:01:00 (in hh:mm:ss) e lo si vuole stampare come mm:ss<br>
	 * <ul><li> Se Overflow == false : stampa '01:00'</li>
	 * <li>Se Overflow == true :  stampa '121:00'</li></ul>
	 * Questo parametro è sempre considerato true se si richiede un formato in virgola mobile<br>
	 * Di default è impostato a true
	 */
	public void setOverflow(boolean overflow) {
		if(this == MM00 || this == SS00)
			return;
		this.overflow = overflow;
	}

}//EO KTimeFormat
