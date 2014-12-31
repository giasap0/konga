package it.konga.framework.util.format;

import java.text.SimpleDateFormat;
import java.util.Locale;

/** rappresenta i parametri di KDate:<br>
 * il pattern di input, il pattern di output, ed il valore per le date non definite<br>
 * Coniene anche una collezione dei pattern più comuni
 * @author Giampaolo Saporito
 */
public class KDate_Params implements KFormat
{
	private static final long serialVersionUID = -3854340582151255431L;
	//pattern - begin
	public static final SimpleDateFormat YYYY  	= new SimpleDateFormat("yyyy", Locale.ITALIAN);
	public static final SimpleDateFormat MM 	= new SimpleDateFormat("MM", Locale.ITALIAN);
	public static final SimpleDateFormat DD 	= new SimpleDateFormat("dd", Locale.ITALIAN);
	/** giorno della settimana , completo (lunedì, martedì)*/
	public static final SimpleDateFormat EEEEE 	= new SimpleDateFormat("EEEEE", Locale.ITALIAN);
	/** giorno della settimana abbreviato (es: domenica == dom) */
	public static final SimpleDateFormat EEE = new SimpleDateFormat("EEE", Locale.ITALIAN);

	public static final SimpleDateFormat MM_YYYY     = new SimpleDateFormat("MM.yyyy", Locale.ITALIAN);
	public static final SimpleDateFormat MM_sl_YYYY  = new SimpleDateFormat("MM/yyyy", Locale.ITALIAN);
	public static final SimpleDateFormat M12_YYYY    = new SimpleDateFormat("'12'.yyyy", Locale.ITALIAN);
	public static final SimpleDateFormat M12_sl_YYYY = new SimpleDateFormat("'12'/yyyy", Locale.ITALIAN);
	public static final SimpleDateFormat MMM_YYYY    = new SimpleDateFormat("MMM yyyy", Locale.ITALIAN);
	public static final SimpleDateFormat MMMMM_YYYY  = new SimpleDateFormat("MMMMM yyyy", Locale.ITALIAN);

	public static final SimpleDateFormat DDMMYYYY 			= new SimpleDateFormat("ddMMyyyy", Locale.ITALIAN);
	public static final SimpleDateFormat DD_MM_YYYY 		= new SimpleDateFormat("dd.MM.yyyy", Locale.ITALIAN);
	public static final SimpleDateFormat DD_sl_MM_sl_YYYY 	= new SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN);

	public static final SimpleDateFormat HH_MM_SS  = new SimpleDateFormat("HH:mm:ss", Locale.ITALIAN);
	public static final SimpleDateFormat HHMMSS    = new SimpleDateFormat("HHmmss", Locale.ITALIAN);
	public static final SimpleDateFormat HH_MM     = new SimpleDateFormat("HH:mm", Locale.ITALIAN);
	public static final SimpleDateFormat HH_dot_MM = new SimpleDateFormat("HH.mm", Locale.ITALIAN);

	public static final SimpleDateFormat DD_MM_YYYY_HH_MM_SS		= new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.ITALIAN);
	public static final SimpleDateFormat DD_MM_YYYY_HH_MM		 	= new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ITALIAN);
	public static final SimpleDateFormat DD_sl_MM_sl_YYYY_HH_MM_SS 	= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ITALIAN);
	public static final SimpleDateFormat DD_sl_MM_sl_YYYY_HH_MM 	= new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ITALIAN);
	public static final SimpleDateFormat YYYY_MM_DD_HH_MM_SS		= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ITALIAN);

	public static final SimpleDateFormat YYYYMM 	= new SimpleDateFormat("yyyyMM", Locale.ITALIAN);
	public static final SimpleDateFormat YYYYMMDD   = new SimpleDateFormat("yyyyMMdd", Locale.ITALIAN);
	public static final SimpleDateFormat YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd", Locale.ITALIAN);
	//pattern - end

	private SimpleDateFormat patternInput = new SimpleDateFormat();	//utilizzato solo se Numero.valore e' String
	private SimpleDateFormat patternOutput = DD_MM_YYYY;
	private String undefined = UNDEFINED_VALUE;

	public KDate_Params(){

	}

	public KDate_Params(SimpleDateFormat patternInput){
		this.patternInput = patternInput;
	}

	public KDate_Params(SimpleDateFormat patternInput, SimpleDateFormat patternOutput){
		this.patternInput  = patternInput;
		this.patternOutput = patternOutput;
	}

	public KDate_Params(SimpleDateFormat patternInput, SimpleDateFormat patternOutput, String undefined){
		this.patternInput  = patternInput;
		this.patternOutput = patternOutput;
		this.undefined	   = undefined;
	}

	public SimpleDateFormat getPatternInput() {
		return patternInput;
	}
	public void setPatternInput(SimpleDateFormat patternInput) {
		this.patternInput = patternInput;
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
}
