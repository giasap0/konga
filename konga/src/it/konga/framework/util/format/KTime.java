package it.konga.framework.util.format;

import java.text.DecimalFormat;
import java.util.Calendar;

/** Classe dedita alla rappresentazione e formattazione di un periodo temporale<br>
 * Può rappresentare un periodo della durata massima di <b>1.967593e+303 giorni </b>*/
public class KTime implements KFormat
{
	private static final long serialVersionUID = -7186493307044718971L;

	/** formatta gli interi con un minimo di due cifre */
	private static final DecimalFormat decimalFormatter = new DecimalFormat("00");
	/** formatta gli interi con un minimo di tre cifre, usata per i millisecondi */
	private static final DecimalFormat millisecondsFormatter = new DecimalFormat("000");

	// ----------------------------------------------------------------------------------- fields ----------------------------------------------------------------------------------- \\
	/**doubles max value è circa 1,80*(10^308) == 1.967593e+303 giorni == 6.347065e+301 mesi*/
	private double _secs=0;
	/** di default è impostato su HH_MM_SS */
	private KTimeFormat _formatter = KTimeFormat.HH_MM_SS;

	// ----------------------------------------------------------------------------------- costruttori ----------------------------------------------------------------------------------- \\
	public KTime()													{}
	public KTime(double secondi)									{ _secs = secondi;}
	public KTime(double secondi, KTimeFormat formato)				{ _secs = secondi; _formatter = formato; }
	public KTime(KTime t)											{ _secs = t._secs; _formatter = t._formatter;}
	public KTime(int h, int m, int s, int ms)						{ this(h,m,s,ms,KTimeFormat.HH_MM_SS); }
	public KTime(Calendar calendar)									{ this(calendar, KTimeFormat.HH_MM_SS);	}
	public KTime(int h, int m, int s, int ms, KTimeFormat formato)
	{
		_formatter = formato;
		_secs = s;
		_secs += ((double)ms)/1000.0;
		_secs += 60.0*m;
		_secs += 3600.0*h;
	}	
	public KTime(Calendar calendar, KTimeFormat formato)
	{
		_formatter = formato;
		_secs = calendar.get(Calendar.SECOND);
		_secs += ((double)calendar.get(Calendar.MILLISECOND))/1000.0;
		_secs += 60.0*calendar.get(Calendar.MINUTE);
		_secs += 3600.0*calendar.get(Calendar.HOUR_OF_DAY);
	}

	// ----------------------------------------------------------------------------------- metodi statici ----------------------------------------------------------------------------------- \\

	/** 24h = 86399.999000 sec */
	public static double timeInSeconds( KTime t)		{return t._secs;}
	/* ritorna l'ora corrente */
	public static KTime currentTime()
	{
		return new KTime(Calendar.getInstance());
	}
	/* ritorna l'ora corrente ed imposta il format a quello passato in input */
	public static KTime currentTime(KTimeFormat formato)
	{
		KTime t = new KTime(Calendar.getInstance());
		t.setFormato(formato);
		return t;
	}

	/** verifica se i parametri sono validi */
	public static boolean isValid(int h, int m, int s, int ms)
	{
		return (m>=0 && m<60) && (h>=0) && (s>=0 && s<60) && (ms>=0 && ms<1000);
	}

	// ----------------------------------------------------------------------------------- metodi publici ----------------------------------------------------------------------------------- \\
	public void setFormato(KTimeFormat formato)		{_formatter = formato;}
	public KTimeFormat getFormato()					{return _formatter;}
	/** setta l'overflow nel formato <br><b>vedi KTimeFormat.setOverflow() </b>*/
	public void setOverflow(boolean overflow)		{_formatter.setOverflow(overflow);}
	/** torna un nuovo oggetto , non modifica questo*/
	public KTime addMSecs(int ms) 					{double s = ((double)ms)/1000.0; return new KTime(_secs+s);}
	/** torna un nuovo oggetto , non modifica questo*/
	public KTime addSecs(int s) 					{return new KTime(_secs+s);}
	/** torna un nuovo oggetto , non modifica questo*/
	public KTime addTempo(KTime t)					{return new KTime(_secs+t._secs, _formatter);}
	/** torna un nuovo oggetto , non modifica questo*/
	public KTime sottraiTempo(KTime t)				{return new KTime(_secs-t._secs, _formatter);}
	/** se l'orario è 13:20:15.100 torna 13 */ 
	public int hour() 								{return (int)(_secs/3600.0);}
	/** se l'orario è 13:20:15.100 torna 20 */
	public int minutes() 
	{ 
		//1h = 3600s
		double h = Math.floor(_secs/3600.0f);
		h*=3600.0f;
		return (int)((_secs-h)/60.0f);
	}
	/** se l'orario è 13:20:15.100 torna 15 */
	public int secs() 
	{
		double h = Math.floor(_secs/3600.0f);
		h*=3600.0f;
		double m = Math.floor( (_secs-h)/60.0f );
		m*=60.f;
		return (int)( _secs-h-m );
	}
	/** se l'orario è 13:20:15.100 torna 100 */
	public int msecs() 
	{
		double h = Math.floor(_secs/3600.0f);
		h*=3600.0f;
		double m = Math.floor( (_secs-h)/60.0f );
		m*=60.f;
		double sec = Math.floor( _secs-h-m );
		return (int)((_secs-h-m-sec)*1000.0f);
	}
	/*24h = 86399.999000 sec */
	public double inSeconds() 								{return _secs;}
	/** torna la rappresentazione in millisecondi di questo tempo */
	public long inMilliSeconds()							{return (long)(_secs*1000.0);}
	public void setTimeinSeconds(double timeInSeconds)		{_secs=timeInSeconds;}
	/** calcola quanti millisecondi ci sono fra il tempo in input e questo */
	public long msecsTo( KTime t) 							{double d = t._secs-_secs; d*=1000.0; return (long)d;}
	/** calcola quanti secondi ci sono fra il tempo in input e questo */
	public double secsTo( KTime t) 							{return t._secs-_secs;}
	/** sets to h,m,s,ms if they are valid return true */
	public boolean setHMS(int h, int m, int s, int ms)
	{
		boolean valid = (m>=0 && m<60) && (h>=0) && (s>=0 && s<60) && (ms>=0 && ms<1000);
		if(!valid)
			return false;
		_secs=s;
		_secs+=(double)(ms)/1000.0f;
		_secs+=60.0*m;
		_secs+=3600.0*h;
		return true;
	}

	public boolean isMaggioreDi(KTime t)					{ return Double.doubleToLongBits(_secs)>Double.doubleToLongBits(t._secs); }
	public boolean insMaggioreUgualeDi(KTime t)				{ return Double.doubleToLongBits(_secs)>=Double.doubleToLongBits(t._secs); }
	public boolean isMinoreDi(KTime t)						{ return Double.doubleToLongBits(_secs)<Double.doubleToLongBits(t._secs); }
	public boolean isMinoreUgualeDi(KTime t)				{ return Double.doubleToLongBits(_secs)<=Double.doubleToLongBits(t._secs); }

	// ----------------------------------------------------------------------------------- Override di Object ----------------------------------------------------------------------------------- \\

	@Override
	public int hashCode() {
		long temp = Double.doubleToLongBits(_secs);
		return 5 * 1 + (int) (temp ^ (temp >>> 32));
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		KTime other = (KTime) obj;
		if (Double.doubleToLongBits(_secs) != Double.doubleToLongBits(other._secs)) {
			return false;
		}
		return true;
	}
	/** formatta il tempo secondo le regole di formato impostate <h3>ricordarsi dell'overflow</h3>*/
	@Override
	public String toString()
	{
		double h = Math.floor(_secs/3600.0f);
		h*=3600.0f;
		double m = Math.floor( (_secs-h)/60.0f );
		m*=60.f;
		double sec = Math.floor( _secs-h-m );
		double ms = Math.floor( (_secs-h-m-sec)*1000.0f );

		String separator = _formatter.getSeparator();
		String str ="-";
		boolean overflow = _formatter.getOverflow();

		switch (_formatter) {
		case INVALID:
			str=""; 			break;
		case HH_MM_SS_ZZZ:
			str =decimalFormatter.format((int)(h/3600.0)) + separator +decimalFormatter.format( (int)( m/60.0 ) ) + separator +decimalFormatter.format( (int) sec ) + separator +millisecondsFormatter.format( (int) ms );
			break;
		case HH_MM_SS:
			str =decimalFormatter.format((int)(h/3600.0)) + separator +decimalFormatter.format( (int)( m/60.0 ) ) + separator +decimalFormatter.format( (int) sec ); break;
		case HH_MM:
			str =decimalFormatter.format((int)(h/3600.0)) + separator +decimalFormatter.format( (int)( m/60.0 ) );break;
		case HH:
			str =decimalFormatter.format((int)(h/3600.0));break;
		case HH00:
			str = String.valueOf(_secs/3600.0f);break;
		case MM_SS_ZZZ:
			if(overflow)
				str =decimalFormatter.format( (long)Math.floor(_secs/60.0))+ separator +decimalFormatter.format( (int) sec )+ separator +millisecondsFormatter.format( (int) ms );
			else
				str =decimalFormatter.format( (int)( m/60.0 ) ) + separator +decimalFormatter.format( (int) sec ) + separator +millisecondsFormatter.format( (int) ms );
			break;
		case MM_SS: 
			if(overflow)
				str =decimalFormatter.format( (long)Math.floor(_secs/60.0))+ separator +decimalFormatter.format( (int) sec );
			else
				str =decimalFormatter.format( (int)( m/60.0 ) ) + separator +decimalFormatter.format( (int) sec );
			break;
		case MM:
			if(overflow)
				str =decimalFormatter.format( (long)Math.floor(_secs/60.0));
			else
				str =decimalFormatter.format( (int)( m/60.0 ) );
			break;
		case MM00:
			str = String.valueOf( _secs/60.0); break;
		case SS_ZZZ: 
			if(overflow)
				str = String.valueOf( (long)_secs) + separator +millisecondsFormatter.format( (int) ms );
			else
				str =decimalFormatter.format( (int) sec ) + separator +millisecondsFormatter.format( (int) ms );
			break;
		case SS: 
			if(overflow)
				str =String.valueOf( (long)_secs);
			else
				str =decimalFormatter.format( (int) sec );
			break;
		case SS00: 
			str = String.valueOf( _secs); break;
		case ZZZ:
			if(overflow)
				str =String.valueOf( (long)(_secs*1000.0) );
			else
				str = millisecondsFormatter.format( (int) ms );
			break;
		default: break;
		}
		return str;
	}//EO toString()

	/**
	 * crea un oggetto che rappresenta un periodo temporale, a partire da una stringa formattata in un certo modo
	 * @param str stringa contenente le informazioni
	 * @param formato indica il formato utilizzato per la stringa
	 * @return oggetto che rappresenta un lasso di tempo
	 */
	public static KTime fromString( String str,  KTimeFormat formato) throws NumberFormatException
	{
		if(str == null || "".equals(str.trim()) || formato == KTimeFormat.INVALID )
			return new KTime(0.0, KTimeFormat.INVALID);
		KTime tmp = new KTime(0.0, formato);
		String hh,mm,ss,zzz;
		double hh0, mm0, ss0;
		long millis;
		int indexH = 0, indexM=0, indexS=0;
		switch (formato) {
		case HH_MM_SS_ZZZ:
			indexH = str.indexOf(formato.getSeparator());
			hh = str.substring(0, indexH);
			indexM = str.indexOf(formato.getSeparator(), indexH+1);
			mm = str.substring(indexH +formato.getSeparator().length(), indexM);
			indexS = str.indexOf(formato.getSeparator(), indexM+1);
			ss = str.substring(indexM+formato.getSeparator().length(),indexS);
			zzz = str.substring(indexS + formato.getSeparator().length());
			hh0 = Integer.parseInt(hh); mm0 = Integer.parseInt(mm); ss0 = Integer.parseInt(ss); millis = Integer.parseInt(zzz);
			tmp._secs = ss0 + (millis/1000.0) + (mm0*60.0) + (hh0*3600.0);
			break;
		case HH_MM_SS:
			indexH = str.indexOf(formato.getSeparator());
			hh = str.substring(0, indexH);
			indexM = str.indexOf(formato.getSeparator(), indexH+1);
			mm = str.substring(indexH +formato.getSeparator().length(), indexM);
			indexS = str.indexOf(formato.getSeparator(), indexM+1);
			ss = str.substring(indexM+formato.getSeparator().length());
			hh0 = Integer.parseInt(hh); mm0 = Integer.parseInt(mm); ss0 = Integer.parseInt(ss);
			tmp._secs = ss0 + (mm0*60.0) + (hh0*3600.0);
			break;
		case HH_MM:
			indexH = str.indexOf(formato.getSeparator());
			hh = str.substring(0, indexH);
			mm = str.substring(indexH +formato.getSeparator().length());
			hh0 = Integer.parseInt(hh); mm0 = Integer.parseInt(mm);
			tmp._secs = (mm0*60.0) + (hh0*3600.0);
			break;
		case HH:
		case HH00:
			hh0 = Double.parseDouble(str); tmp._secs = hh0*3600.0; 
			break;
		case MM_SS_ZZZ:
			indexM = str.indexOf(formato.getSeparator());
			mm = str.substring(0, indexM);
			indexS = str.indexOf(formato.getSeparator(), indexM+1);
			ss = str.substring(indexM +formato.getSeparator().length(), indexS);
			zzz = str.substring(indexS + formato.getSeparator().length());
			mm0 = Integer.parseInt(mm); ss0 = Integer.parseInt(ss); millis = Integer.parseInt(zzz);
			tmp._secs = ss0 + (millis/1000.0) + (mm0*60.0);
			break;
		case MM_SS: 
			indexM = str.indexOf(formato.getSeparator());
			mm = str.substring(0, indexM);
			ss = str.substring(indexM +formato.getSeparator().length());
			mm0 = Integer.parseInt(mm); ss0 = Integer.parseInt(ss);
			tmp._secs = ss0  + (mm0*60.0);
			break;
		case MM:
		case MM00:
			mm0 = Double.parseDouble(str); tmp._secs = mm0*60;
			break;
		case SS_ZZZ: 
			indexS = str.indexOf(formato.getSeparator());
			ss = str.substring(0, indexS);
			zzz = str.substring(indexS + formato.getSeparator().length());
			ss0 = Integer.parseInt(ss); millis = Integer.parseInt(zzz);
			tmp._secs = ss0  + ((double)millis/1000.0);
			break;
		case SS: 
		case SS00: 
			ss0 = Double.parseDouble(str); tmp._secs = ss0;
			break;
		case ZZZ:
			millis = Long.parseLong(str); tmp._secs = (double)millis/1000.0;
			break;
		default: throw new NumberFormatException("KTime.fromString() - casistica non gestita. Stringa input = "+str +" , formato = "+formato.toString());
		}

		return tmp;
	}

	// ----------------------------------------------------------------------------------- main di test ----------------------------------------------------------------------------------- \\


	public static void main(String[] args)
	{		
		KTime t = KTime.currentTime();
		t.setFormato(KTimeFormat.HH_MM_SS_ZZZ); System.out.print(t.getFormato().toString() + " -> " +t.toString() );  System.out.println("  FROM STRING : " +KTime.fromString(t.toString(), t.getFormato())); 
		t.setFormato(KTimeFormat.HH_MM_SS); System.out.print(t.getFormato().toString() + " -> " +t.toString() );  System.out.println("  FROM STRING : " +KTime.fromString(t.toString(), t.getFormato()));
		t.setFormato(KTimeFormat.HH_MM); System.out.print(t.getFormato().toString() + " -> " +t.toString() );  System.out.println("  FROM STRING : " +KTime.fromString(t.toString(), t.getFormato()));
		t.setFormato(KTimeFormat.HH); System.out.print(t.getFormato().toString() + " -> " +t.toString() );  System.out.println("  FROM STRING : " +KTime.fromString(t.toString(), t.getFormato()));
		t.setFormato(KTimeFormat.HH00); System.out.print(t.getFormato().toString() + " -> " +t.toString() );  System.out.println("  FROM STRING : " +KTime.fromString(t.toString(), t.getFormato()));
		t.setFormato(KTimeFormat.MM_SS_ZZZ); System.out.print(t.getFormato().toString() + " -> " +t.toString() );  System.out.println("  FROM STRING : " +KTime.fromString(t.toString(), t.getFormato()));
		t.setFormato(KTimeFormat.MM_SS); System.out.print(t.getFormato().toString() + " -> " +t.toString() );  System.out.println("  FROM STRING : " +KTime.fromString(t.toString(), t.getFormato()));
		t.setFormato(KTimeFormat.MM); System.out.print(t.getFormato().toString() + " -> " +t.toString() );  System.out.println("  FROM STRING : " +KTime.fromString(t.toString(), t.getFormato()));
		t.setFormato(KTimeFormat.MM00); System.out.print(t.getFormato().toString() + " -> " +t.toString() );  System.out.println("  FROM STRING : " +KTime.fromString(t.toString(), t.getFormato()));
		t.setFormato(KTimeFormat.SS_ZZZ); System.out.print(t.getFormato().toString() + " -> " +t.toString() );  System.out.println("  FROM STRING : " +KTime.fromString(t.toString(), t.getFormato()));
		t.setFormato(KTimeFormat.SS); System.out.print(t.getFormato().toString() + " -> " +t.toString() );  System.out.println("  FROM STRING : " +KTime.fromString(t.toString(), t.getFormato()));
		t.setFormato(KTimeFormat.SS00); System.out.print(t.getFormato().toString() + " -> " +t.toString() );  System.out.println("  FROM STRING : " +KTime.fromString(t.toString(), t.getFormato()));
		t.setFormato(KTimeFormat.ZZZ); System.out.print(t.getFormato().toString() + " -> " +t.toString() );  System.out.println("  FROM STRING : " +KTime.fromString(t.toString(), t.getFormato()));
	}


}//EO class KTime