package it.konga.framework.util.format;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Classe utile per formattare le date.<br>
 * Esistono vari pattern , fra i più comuni, come campi statici della classe KDate_Params
 * @author A28J
 *
 */
public class KDate implements KFormat
{
	private static final long serialVersionUID = -3533594221646737622L;

	private Serializable 			 valore  	   = "";
	private Date		 			 dataInterna   = null;
	private SimpleDateFormat		 patternInput  = new SimpleDateFormat();
	private SimpleDateFormat		 patternOutput = KDate_Params.DD_MM_YYYY;
	private String		 			 undefined 	   = UNDEFINED_VALUE;

	// ---------------------------------------------------------------------------------- COSTRUTTORI ---------------------------------------------------------------------------------- \\
	public KDate(){
		this.valore = null;
	}
	/**
	 * Crea una nuova data, inizianizzandola in base al numero di millisecondi passati dalla data nota come 'the epoch'
	 * @param valore millisecondi dall'1 Gennario 1970
	 */
	public KDate(Long valore){
		this(valore, KDate_Params.DD_MM_YYYY);
	}
	/**
	 * Crea una nuova data, inizianizzandola in base al numero di millisecondi passati dalla data nota come 'the epoch'
	 * @param valore millisecondi dall'1 Gennario 1970
	 */
	public KDate(Long valore, SimpleDateFormat patternOutput){
		this(valore, patternOutput, UNDEFINED_VALUE);
	}
	/**
	 * Crea una nuova data, inizianizzandola in base al numero di millisecondi passati dalla data nota come 'the epoch'
	 * @param valore millisecondi dall'1 Gennario 1970
	 */
	public KDate(Long valore, SimpleDateFormat patternOutput, String undefined){
		this.valore = valore;
		this.dataInterna = new Date(valore);
		this.patternOutput = patternOutput;
		this.undefined = undefined;
	}

	public KDate(String valore, SimpleDateFormat patternInput){
		this(valore, patternInput, KDate_Params.DD_MM_YYYY);
	}

	public KDate(String valore, SimpleDateFormat patternInput, SimpleDateFormat patternOutput){
		this(valore, patternInput, patternOutput, UNDEFINED_VALUE);
	}

	public KDate(String valore, SimpleDateFormat patternInput, SimpleDateFormat patternOutput, String undefined){
		this.valore = valore;
		this.undefined = undefined;
		try{
			if(!skipFormat())this.dataInterna = patternInput.parse(valore);
		}catch(Exception e){
			System.out.println("KDate errata o Pattern di input non valido: \""+valore+"\" --> "+patternInput.toPattern());
		}
		this.patternInput = patternInput;
		this.patternOutput = patternOutput;
	}

	public KDate(String valore, KDate_Params params){
		this(valore, params.getPatternInput(),params.getPatternOutput(), params.getUndefined());
	}

	public KDate(Date valore){
		this(valore, KDate_Params.DD_MM_YYYY);
	}

	public KDate(Date valore, SimpleDateFormat patternOutput){
		this(valore, patternOutput, UNDEFINED_VALUE);
	}

	public KDate(Date valore, SimpleDateFormat patternOutput, String undefined){
		this.valore = valore;
		this.dataInterna = valore;
		this.patternOutput = patternOutput;
		this.undefined = undefined;
	}

	public KDate(Date valore, KDate_Params params){
		this(valore, params.getPatternOutput(), params.getUndefined());
	}

	/** costruisce una nuova data partendo dalla rappresentazione W3 XML schema 1.0*/
	public KDate(XMLGregorianCalendar calendar)
	{
		this.patternOutput = KDate_Params.DD_sl_MM_sl_YYYY_HH_MM_SS;
		this.undefined = UNDEFINED_VALUE;
		try {
			GregorianCalendar x = calendar.toGregorianCalendar();
			this.valore = x.getTime();
			this.dataInterna = x.getTime();
		} catch (NullPointerException e)
		{
			this.valore = null;
			this.dataInterna = null;
		}
	}

	// --------------------------------------------------------- metodi privati --------------------------------------------------------- \\ 

	private boolean skipFormat(){
		return (valore == null ||
				valore.toString().trim().equalsIgnoreCase(BLANK_VALUE) ||
				valore.toString().trim().equalsIgnoreCase(ND_VALUE) ||
				valore.toString().trim().equalsIgnoreCase(NC_VALUE) ||
				valore.toString().trim().equalsIgnoreCase(NS_VALUE) ||
				valore.toString().trim().equalsIgnoreCase(UNDEFINED_VALUE) ||
				valore.toString().trim().equalsIgnoreCase(undefined) 
				//||(valore instanceof Date && isDateIstanceSkipFormat((Date)valore)) 
				);
	}

	private String getStringOnSkipFormat() {
		if(valore==null)return undefined;
		if(valore.toString().trim().equals(BLANK_VALUE))return BLANK_VALUE;

		return valore.toString();
	}


	private String applyPattern(SimpleDateFormat pattern){
		if(dataInterna==null || skipFormat() )return getStringOnSkipFormat();
		try{
			return pattern.format(dataInterna);
		}catch(Exception e){
			System.out.println("Pattern di output non valido: "+pattern);
			return getString();
		}
	}

	/**
	 * GETTER E SETTER
	 */
	public Serializable getValore() {
		return valore;
	}

	protected Date getDataInterna(){
		return dataInterna;
	}

	public SimpleDateFormat getPatternInput() {
		return patternInput;
	}

	public SimpleDateFormat getPatternOutput() {
		return patternOutput;
	}

	public void setPatternOutput(SimpleDateFormat patternOutput) {
		this.patternOutput = patternOutput;
	}

	public String getUndefined() {
		return undefined;
	}

	public void setUndefined(String undefined) {
		this.undefined = undefined;
	}

	/**
	 * Converte la data in java.util.Date
	 */
	public Date getDate() {
		return getDataInterna();
	}

	/**
	 * Converte la data in java.util.Date, settando le ore ,i minuti e i secondi a 0 
	 */
	public Date getDateStart() {
		if(getDataInterna()==null)return null;
		Calendar cal = Calendar.getInstance();       // get calendar instance
		cal.setTime(getDataInterna());               // set cal to date
		cal.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
		cal.set(Calendar.MINUTE, 0);                 // set minute in hour
		cal.set(Calendar.SECOND, 0);                 // set second in minute

		return cal.getTime();
	}
	/**
	 * Converte la data in java.util.Date, settando le ore ,i minuti e i secondi a 23
	 */
	public Date getDateEnd() {
		if(getDataInterna()==null)return null;
		Calendar cal = Calendar.getInstance();       // get calendar instance
		cal.setTime(getDataInterna());               // set cal to date
		cal.set(Calendar.HOUR_OF_DAY, 23);           // set hour to midnight
		cal.set(Calendar.MINUTE, 59);                // set minute in hour
		cal.set(Calendar.SECOND, 59);                // set second in minute

		return cal.getTime();
	}

	/**
	 * Converte la data in java.sql.Date
	 */
	public java.sql.Date getSqlDate() {
		if(getDataInterna()==null)return null;
		return new java.sql.Date(getDataInterna().getTime());
	}

	/**
	 * Converte la data in String
	 */
	public String getString() {
		if(valore==null)return BLANK_VALUE;
		return valore.toString();
	}

	/**
	 * Metodi di formattazione
	 */
	public String getAnno() {
		return applyPattern(KDate_Params.YYYY);
	}

	public String getMese() {
		return applyPattern(KDate_Params.MM);
	}

	public String getGiorno() {
		return applyPattern(KDate_Params.DD);
	}

	public String getGiornoSettimana() {
		return applyPattern(KDate_Params.EEEEE);
	}

	public String getPeriodo() {
		return applyPattern(KDate_Params.MM_YYYY);
	}

	public String getPeriodo_sl() {
		return applyPattern(KDate_Params.MM_sl_YYYY);
	}

	public String getPeriodoMMM() {
		return applyPattern(KDate_Params.MMM_YYYY);
	}

	public String getPeriodoMMMMM() {
		return applyPattern(KDate_Params.MMMMM_YYYY);
	}

	public String getData() {
		return applyPattern(KDate_Params.DD_MM_YYYY);
	}

	public String getData_sl() {
		return applyPattern(KDate_Params.DD_sl_MM_sl_YYYY);
	}

	public String getDataYYYYMMDD() {
		return applyPattern(KDate_Params.YYYYMMDD);
	}

	public String getTimestamp() {
		return applyPattern(KDate_Params.DD_MM_YYYY_HH_MM_SS);
	}

	public XMLGregorianCalendar getXmlGregorianCalendar() {
		try {
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(dataInterna);
			XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
			return date2;
		} catch (DatatypeConfigurationException e) {
			System.out.println("KDate: errore date to xmlGregorianCalendar");
			return null;
		}
	}

	public XMLGregorianCalendar getXmlGregorianCalendarTimestamp() {
		try {
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(dataInterna);
			XMLGregorianCalendar xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH),
																								cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND), 
																								DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED);
			return xmlDate;
		} catch (DatatypeConfigurationException e) {
			System.out.println("KDate: errore date to xmlGregorianCalendar");
			return null;
		}
	}

	public String getTimestamp_sl() {
		return applyPattern(KDate_Params.DD_sl_MM_sl_YYYY_HH_MM_SS);
	}

	public String getTimestampDB() {
		return applyPattern(KDate_Params.YYYY_MM_DD_HH_MM_SS);
	}

	public String getOra() {
		return applyPattern(KDate_Params.HH_MM_SS);
	}

	public String getOraHH_MM() {
		return applyPattern(KDate_Params.HH_MM);
	}

	public String getOraHH_dot_MM() {
		return applyPattern(KDate_Params.HH_dot_MM);
	}

	public String getOraHHMMSS() {
		return applyPattern(KDate_Params.HHMMSS);
	}

	public String toString(){
		return applyPattern(patternOutput);
	}
	
	@Override
	public int hashCode() {
		final int prime = 11;
		int result =prime + ((dataInterna == null) ? 0 : dataInterna.hashCode());
		if(skipFormat())
			result = prime + ((valore == null) ? 0 : valore.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof KDate)) {
			return false;
		}
		KDate other = (KDate) obj;
		if(skipFormat()){
			return (valore!=null && valore.toString().trim().equals(other.getValore().toString().trim()));
		}else{
			return (dataInterna!=null && dataInterna.equals(other.getDataInterna()));
		}
	}

	public boolean equalsIgnoreHour(KDate data){
		if(data==null)return false;
		if(skipFormat()){
			return (valore!=null && valore.toString().trim().equals(data.getValore().toString().trim()));
		}else{
			return (getData()!=null && getData().equals(data.getData()));
		}
	}

	public boolean before(KDate data){
		if(getDataInterna()==null || data==null || data.getDataInterna()==null || skipFormat())return false;

		Calendar calThis = Calendar.getInstance();       // get calendar instance
		calThis.setTime(getDataInterna());               // set cal to date

		Calendar calData = Calendar.getInstance();       // get calendar instance
		calData.setTime(data.getDataInterna());          // set cal to date

		return calThis.before(calData);
	}


	public boolean after(KDate data){
		if(getDataInterna()==null || data==null || data.getDataInterna()==null || skipFormat())return false;

		Calendar calThis = Calendar.getInstance();       // get calendar instance
		calThis.setTime(getDataInterna());               // set cal to date

		Calendar calData = Calendar.getInstance();       // get calendar instance
		calData.setTime(data.getDataInterna());          // set cal to date

		return calThis.after(calData);
	}
}
