package it.konga.framework.util.format;

import it.konga.framework.util.format.KNumber.Formato;

import java.math.RoundingMode;
import java.util.Locale;

public class KNumber_Params implements KFormat
{
	private static final long serialVersionUID = 1822182257630396080L;

	private Locale		 localeInput     = Locale.ITALIAN;

	private Formato		 formato   		 = Formato.NONE;

	private String		 patternOutput	 = "";
	private RoundingMode roundingMode	 = RoundingMode.HALF_UP;

	private String		 undefined 		 = UNDEFINED_VALUE;
	private Number		 undefinedNumber = Double.NaN;
	private String		 prefix			 = "";
	private String		 suffix			 = "";


	public KNumber_Params(){
	}

	public KNumber_Params(Locale localeInput){
		this.localeInput = localeInput;
	}

	public KNumber_Params(Formato formato){
		this(formato, UNDEFINED_VALUE);
	}

	public KNumber_Params(Locale localeInput, Formato formato){
		this(localeInput, formato, UNDEFINED_VALUE);
	}

	public KNumber_Params(Formato formato, String undefined){
		this(formato, undefined, Double.NaN);
	}

	public KNumber_Params(Locale localeInput, Formato formato, String undefined){
		this(localeInput, formato, undefined, Double.NaN);
	}

	public KNumber_Params(Formato formato, String undefined, Number undefinedNumber){
		this(formato, undefined, undefinedNumber, "", "");
	}

	public KNumber_Params(Locale localeInput, Formato formato, String undefined, Number undefinedNumber){
		this(localeInput, formato, undefined, undefinedNumber, "", "");
	}

	public KNumber_Params(Formato formato, String undefined, Number undefinedNumber, String prefix, String suffix){
		this(Locale.ITALIAN, formato, undefined, undefinedNumber, prefix, suffix);
	}

	public KNumber_Params(Locale localeInput, Formato formato, String undefined, Number undefinedNumber, String prefix, String suffix){
		this.localeInput	 = localeInput;
		this.formato         = formato;
		this.undefined       = undefined;
		this.undefinedNumber = undefinedNumber;
		this.prefix          = prefix;
		this.suffix          = suffix;
	}


	public KNumber_Params(String patternOutput){
		this(patternOutput, UNDEFINED_VALUE);
	}

	public KNumber_Params(Locale localeInput, String patternOutput){
		this(localeInput, patternOutput, UNDEFINED_VALUE);
	}

	public KNumber_Params(String patternOutput, RoundingMode roundingMode){
		this(patternOutput, roundingMode, UNDEFINED_VALUE);
	}

	public KNumber_Params(Locale localeInput, String patternOutput, RoundingMode roundingMode){
		this(localeInput, patternOutput, roundingMode, UNDEFINED_VALUE);
	}

	public KNumber_Params(String patternOutput, String undefined){
		this(patternOutput, undefined, Double.NaN);
	}

	public KNumber_Params(Locale localeInput, String patternOutput, String undefined){
		this(localeInput, patternOutput, undefined, Double.NaN);
	}

	public KNumber_Params(String patternOutput, RoundingMode roundingMode, String undefined){
		this(patternOutput, roundingMode, undefined, Double.NaN);
	}

	public KNumber_Params(Locale localeInput, String patternOutput, RoundingMode roundingMode, String undefined){
		this(localeInput, patternOutput, roundingMode, undefined, Double.NaN);
	}

	public KNumber_Params(String patternOutput, String undefined, Number undefinedNumber){
		this(patternOutput, undefined, undefinedNumber, "", "");
	}

	public KNumber_Params(Locale localeInput, String patternOutput, String undefined, Number undefinedNumber){
		this(localeInput, patternOutput, undefined, undefinedNumber, "", "");
	}

	public KNumber_Params(String patternOutput, RoundingMode roundingMode, String undefined, Number undefinedNumber){
		this(patternOutput, roundingMode, undefined, undefinedNumber, "", "");
	}

	public KNumber_Params(Locale localeInput, String patternOutput, RoundingMode roundingMode, String undefined, Number undefinedNumber){
		this(localeInput, patternOutput, roundingMode, undefined, undefinedNumber, "", "");
	}

	public KNumber_Params(String patternOutput, String undefined, Number undefinedNumber, String prefix, String suffix){
		this(patternOutput, RoundingMode.HALF_UP, undefined, undefinedNumber, prefix, suffix);
	}

	public KNumber_Params(Locale localeInput, String patternOutput, String undefined, Number undefinedNumber, String prefix, String suffix){
		this(localeInput, patternOutput, RoundingMode.HALF_UP, undefined, undefinedNumber, prefix, suffix);
	}

	public KNumber_Params(String patternOutput, RoundingMode roundingMode, String undefined, Number undefinedNumber, String prefix, String suffix){
		this(Locale.ITALIAN, patternOutput, roundingMode, undefined, undefinedNumber, prefix, suffix);
	}

	public KNumber_Params(Locale localeInput, String patternOutput, RoundingMode roundingMode, String undefined, Number undefinedNumber, String prefix, String suffix){
		this.localeInput	 = localeInput;
		this.patternOutput   = patternOutput;
		this.roundingMode    = roundingMode;
		this.undefined       = undefined;
		this.undefinedNumber = undefinedNumber;
		this.prefix          = prefix;
		this.suffix          = suffix;
	}

	public Locale getLocaleInput() {
		return localeInput;
	}

	public void setLocaleInput(Locale localeInput) {
		this.localeInput = localeInput;
	}

	public Formato getFormato() {
		return formato;
	}

	public void setFormato(Formato formato) {
		this.formato = formato;
	}

	public String getPatternOutput() {
		return patternOutput;
	}

	public void setPatternOutput(String patternOutput) {
		this.patternOutput = patternOutput;
	}

	public RoundingMode getRoundingMode() {
		return roundingMode;
	}

	public void setRoundingMode(RoundingMode roundingMode) {
		this.roundingMode = roundingMode;
	}

	public String getUndefined() {
		return undefined;
	}

	public void setUndefined(String undefined) {
		this.undefined = undefined;
	}

	public Number getUndefinedNumber() {
		return undefinedNumber;
	}

	public void setUndefinedNumber(Number undefinedNumber) {
		this.undefinedNumber = undefinedNumber;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

}//EO KNumber_Params
