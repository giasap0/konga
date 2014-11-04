package it.konga.framework.util.format;

import java.io.Serializable;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class KNumber implements KFormat
{
	private static final long serialVersionUID = -3798386445762571446L;

	public static enum Formato {
		NONE, NUM0, IMP0, IMP1, IMP2, IMP4, PERC0, PERC2, PERC2_00, IRIS
	}

	//locale di input
	public final static Locale LOCALE_US  = Locale.US;
	public final static Locale LOCALE_IT  = Locale.ITALIAN;

	//pattern di output
	public final static String $_$$0      = "#,##0";
	public final static String $_$$0_0    = "#,##0.0";
	public final static String $_$$0_00   = "#,##0.00";
	public final static String $_$$0_000  = "#,##0.000";
	public final static String $_$$0_0000 = "#,##0.0000";
	public final static String $_$$0_00_P = "#,##0.00' %'";

	private Serializable valore    		 = "";
	private Number		 numeroInterno	 = null;
	private Locale		 localeInput     = LOCALE_US;

	private Formato		 formato   		 = Formato.NONE;

	private String		 patternOutput	 = "";
	private RoundingMode roundingMode	 = RoundingMode.HALF_UP;

	private String		 undefined 		 = UNDEFINED_VALUE;
	private Number		 undefinedNumber = Double.NaN;
	private String		 prefix			 = "";
	private String		 suffix			 = "";



	//--------------------------------------------------------------------------------- COSTRUTTORI --------------------------------------------------------------------------------- \\
	public KNumber(){
		this.valore 	   = null;
		this.numeroInterno = null;
	}

	public KNumber(String valore){
		this.valore 	   = valore;
		this.numeroInterno = parseString(); //IMPORTANTE: numeroInterno deve essere settato dopo valore
	}

	public KNumber(String valore, Locale localeInput){
		this.valore 	   = valore;
		this.localeInput   = localeInput;
		this.numeroInterno = parseString(); //IMPORTANTE: numeroInterno deve essere settato dopo valore e localeInput
	}

	public KNumber(String valore, Formato formato){
		this(valore, formato, UNDEFINED_VALUE);
	}

	public KNumber(String valore, Locale localeInput, Formato formato){
		this(valore, localeInput, formato, UNDEFINED_VALUE);
	}

	public KNumber(String valore, String patternOutput){
		this(valore, patternOutput, UNDEFINED_VALUE);
	}

	public KNumber(String valore, Locale localeInput, String patternOutput){
		this(valore, localeInput, patternOutput, UNDEFINED_VALUE);
	}

	public KNumber(String valore, Formato formato, String undefined){
		this(valore, formato, undefined, Double.NaN);
	}

	public KNumber(String valore, Locale localeInput, Formato formato, String undefined){
		this(valore, localeInput, formato, undefined, Double.NaN);
	}

	public KNumber(String valore, String patternOutput, String undefined){
		this(valore, patternOutput, undefined, Double.NaN);
	}

	public KNumber(String valore, Locale localeInput, String patternOutput, String undefined){
		this(valore, localeInput, patternOutput, undefined, Double.NaN);
	}

	public KNumber(String valore, Formato formato, String undefined, Number undefinedNumber){
		this(valore, LOCALE_US, formato, undefined, undefinedNumber);
	}

	public KNumber(String valore, Locale localeInput, Formato formato, String undefined, Number undefinedNumber){
		this.valore  		 = valore;
		this.localeInput	 = localeInput;
		this.undefined 		 = undefined;
		this.numeroInterno   = parseString(); //IMPORTANTE: numeroInterno deve essere settato dopo valore, localeInput e undefined
		this.formato 		 = formato;
		this.undefinedNumber = undefinedNumber;
	}

	public KNumber(String valore, String patternOutput, String undefined, Number undefinedNumber){
		this(valore, LOCALE_US, patternOutput, undefined, undefinedNumber);
	}

	public KNumber(String valore, Locale localeInput, String patternOutput, String undefined, Number undefinedNumber){
		this.valore  		 = valore;
		this.localeInput	 = localeInput;
		this.undefined 		 = undefined;
		this.numeroInterno   = parseString(); //IMPORTANTE: numeroInterno deve essere settato dopo valore, localeInput e undefined
		this.patternOutput 	 = patternOutput;
		this.undefinedNumber = undefinedNumber;
	}

	public KNumber(String valore, KNumber_Params params){
		this.valore  		 = valore;
		this.localeInput	 = params.getLocaleInput();
		this.undefined 		 = params.getUndefined();
		this.numeroInterno   = parseString(); //IMPORTANTE: numeroInterno deve essere settato dopo valore, localeInput e undefined
		this.formato   		 = params.getFormato();
		this.patternOutput	 = params.getPatternOutput();
		this.roundingMode	 = params.getRoundingMode();
		this.undefinedNumber = params.getUndefinedNumber();
		this.prefix			 = params.getPrefix();
		this.suffix			 = params.getSuffix();
	}



	public KNumber(Number valore){
		this.valore        = valore;
		this.numeroInterno = valore;
	}

	public KNumber(Number valore, Formato formato){
		this(valore, formato, UNDEFINED_VALUE);
	}

	public KNumber(Number valore, String patternOutput){
		this(valore, patternOutput, UNDEFINED_VALUE);
	}

	public KNumber(Number valore, Formato formato, String undefined){
		this(valore, formato, undefined, Double.NaN);
	}

	public KNumber(Number valore, String patternOutput, String undefined){
		this(valore, patternOutput, undefined, Double.NaN);
	}

	public KNumber(Number valore, Formato formato, String undefined, Number undefinedNumber){
		this.valore  		 = valore;
		this.numeroInterno 	 = valore;
		this.formato 		 = formato;
		this.undefined 		 = undefined;
		this.undefinedNumber = undefinedNumber;
	}

	public KNumber(Number valore, String patternOutput, String undefined, Number undefinedNumber){
		this.valore  	 	 = valore;
		this.numeroInterno 	 = valore;
		this.patternOutput 	 = patternOutput;
		this.undefined 		 = undefined;
		this.undefinedNumber = undefinedNumber;
	}

	public KNumber(Number valore, KNumber_Params params){
		this.valore  		 = valore;
		this.numeroInterno 	 = valore;
		this.formato   		 = params.getFormato();
		this.patternOutput	 = params.getPatternOutput();
		this.roundingMode	 = params.getRoundingMode();
		this.undefined 		 = params.getUndefined();
		this.undefinedNumber = params.getUndefinedNumber();
		this.prefix			 = params.getPrefix();
		this.suffix			 = params.getSuffix();
	}

	private boolean skipFormat()
	{
		return (valore == null ||
				valore.toString().trim().equalsIgnoreCase(BLANK_VALUE) ||
				valore.toString().trim().equalsIgnoreCase(ND_VALUE) ||
				valore.toString().trim().equalsIgnoreCase(NC_VALUE) ||
				valore.toString().trim().equalsIgnoreCase(NS_VALUE) ||
				valore.toString().trim().equalsIgnoreCase(UNDEFINED_VALUE) ||
				valore.toString().trim().equalsIgnoreCase(undefined) ||
				valore.toString().trim().equalsIgnoreCase("NaN"));
	}

	private String getStringOnSkipFormat() {
		if(valore==null)return undefined;
		if(valore.toString().trim().equals("NaN"))return undefined;
		if(valore.toString().trim().equals(BLANK_VALUE))return BLANK_VALUE;
		return valore.toString();
	}


	private Number parseString(){
		try{
			if(!skipFormat()){
				NumberFormat formatter = NumberFormat.getNumberInstance(localeInput);			
				return formatter.parse(valore.toString());
			}else{
				return null;
			}
		}catch(Exception e){
			System.out.println("KNumber errato o Locale di input non valido: \""+valore+"\" --> "+localeInput);
			return null;
		}
	}

	private String applyPattern(String _patternOutput){
		if(numeroInterno==null || skipFormat())return getStringOnSkipFormat();
		try{
			DecimalFormat formatter = new DecimalFormat(_patternOutput, new DecimalFormatSymbols(Locale.ITALIAN));
			formatter.setRoundingMode(roundingMode);
			return formatter.format(numeroInterno);
		}catch(Exception e){
			System.out.println("Pattern di output non valido: \""+numeroInterno+"\" --> "+_patternOutput);
			return getString();
		}
	}

	//--------------------------------------------------------------------------------- GETTERS E SETTERS --------------------------------------------------------------------------------- \\

	public Serializable getValore() 						{ return valore; }
	protected Number getNumeroInterno()						{ return numeroInterno; }
	public Locale getLocaleInput() 							{ return localeInput; }
	public Formato getFormato() 							{ return formato; }
	public void setFormato(Formato formato) 				{ this.formato = formato; }
	public String getPatternOutput() 						{ return patternOutput; }
	public void setPatternOutput(String patternOutput) 		{ this.patternOutput = patternOutput; }
	public RoundingMode getRoundingMode() 					{ return roundingMode; }
	public void setRoundingMode(RoundingMode roundingMode) 	{ this.roundingMode = roundingMode; }
	public String getUndefined()	 						{ return undefined; }
	public void setUndefined(String undefined) 				{this.undefined = undefined;}
	public Number getUndefinedDouble() 						{return undefinedNumber;}
	public void setUndefinedNumber(Number undefinedNumber) 	{this.undefinedNumber = undefinedNumber;}
	public String getPrefix() 								{return prefix;}
	public void setPrefix(String prefix) 					{this.prefix = prefix;}
	public String getSuffix() 								{return suffix;}
	public void setSuffix(String suffix) 					{this.suffix = suffix;}
	/** Converte il numero in Number */
	 public Number getNumber()								{return getNumeroInterno();}
	 
	/** Converte il numero in double */
	 public double getDouble(){
		 if(numeroInterno==null)return undefinedNumber.doubleValue();
		 return numeroInterno.doubleValue();
	 }

	 /** Converte il numero in String */
	 public String getString() {
		 if(numeroInterno==null)return BLANK_VALUE;
		 return numeroInterno.toString();
	 }

	 /** Formatta il numero in #,##0 cioè con separatore delle migliaia e senza decimali */
	 public String getNum0(){
		 if(skipFormat())return getStringOnSkipFormat();
		 return prefix+applyPattern($_$$0)+suffix;
	 }

	 /** Formatta il numero in #,##0 cioè con separatore delle migliaia e senza decimali */
	 public String getIris(){
		 if(skipFormat())return getStringOnSkipFormat();
		 if(getString() != BLANK_VALUE && getNumber().intValue() > 0 ){
			 //return prefix+"+"+formatImp0(getString())+suffix;
			 return prefix+"+"+applyPattern($_$$0)+suffix;
		 }
		 return prefix+applyPattern($_$$0)+suffix;
	 }

	 /** Formatta il numero in #,##0 cioè con separatore delle migliaia e senza decimali */
	 public String getImp0(){
		 if(skipFormat())return getStringOnSkipFormat();
		 //return prefix+formatImp0(getString())+suffix;
		 return prefix+applyPattern($_$$0)+suffix;
	 }

	 /** Formatta il numero in #,##0.0 cioè con separatore delle migliaia e 1 decimale */
	 public String getImp1(){
		 if(skipFormat())return getStringOnSkipFormat();
		 //return prefix+formatImp1(getString())+suffix;
		 return prefix+applyPattern($_$$0_0)+suffix;
	 }

	 /** Formatta il numero in #,##0.00 cioè con separatore delle migliaia e 2 decimali */
	 public String getImp2(){
		 if(skipFormat())return getStringOnSkipFormat();
		 //return prefix+formatImp2(getString())+suffix;
		 return prefix+applyPattern($_$$0_00)+suffix;
	 }


	 /** Formatta il numero in #,##0.0000 cioè con separatore delle migliaia e 4 decimali IMP4 */
	 public String getImp4(){
		 if(skipFormat())return getStringOnSkipFormat();
		 //return prefix+formatImp4(getString())+suffix;
		 return prefix+applyPattern($_$$0_0000)+suffix;
	 }

	 /** Formatta il numero in #,##0 cioè con separatore delle migliaia e senza decimali */
	 public String getPerc0(){
		 if(skipFormat())return getStringOnSkipFormat();
		 //return prefix+formatPerc0(getString())+suffix;
		 return prefix+applyPattern($_$$0)+suffix;
	 }

	 /** Formatta il numero in #,##0.00 cioè con separatore delle migliaia e 2 decimali */
	 public String getPerc2(){
		 if(skipFormat())return getStringOnSkipFormat();
		 //return prefix+formatPerc2(getString())+suffix;
		 return prefix+applyPattern($_$$0_00)+suffix;
	 }

	 /** Formatta il numero in #,##0.00 % cioè con separatore delle migliaia, 2 decimali e simbolo % */
	 public String getPerc2_00(){
		 if(skipFormat())return getStringOnSkipFormat();
		 //return prefix+formatPerc2_00(getString())+suffix;
		 return prefix+applyPattern($_$$0_00_P)+suffix;
	 }


	 public String toString(){
		 if(skipFormat())return getStringOnSkipFormat();
		 if(patternOutput!=null && !patternOutput.trim().equals(""))return prefix+applyPattern(patternOutput)+suffix;

		 switch (formato){
		 case NUM0: return getNum0();
		 case IMP0: return getImp0();
		 case IMP1: return getImp1();
		 case IMP2: return getImp2();
		 case IMP4: return getImp4();
		 case IRIS: return getIris();
		 case PERC0: return getPerc0();
		 case PERC2: return getPerc2();
		 case PERC2_00: return getPerc2_00();
		 default: return prefix+getString()+suffix;
		 }
	 }

	 @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime + ((numeroInterno == null) ? 0 : numeroInterno.hashCode());
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
		if (!(obj instanceof KNumber)) {
			return false;
		}
		KNumber other = (KNumber) obj;
		 if(skipFormat()){
			 return (valore!=null && valore.equals(other.getValore()));
		 }
		return (numeroInterno!=null && numeroInterno.equals(other.getNumeroInterno()));
	}

	public static void main(String[] args){
		 KNumber num = new KNumber("1,923.45678",Formato.IMP0);

		 System.out.println("*********************> "+num);
		 System.out.println("*********************> "+num.getPerc2_00());
		 System.out.println("*********************> "+num.getDouble());

		 System.out.println("*********************> "+new KNumber("1923.45678",Formato.IMP1));
		 System.out.println("*********************> "+new KNumber("1923.45678",Formato.IMP2));

		 System.out.println("*********************> "+new KNumber(1923.45678,Formato.IMP0));
		 System.out.println("*********************> "+new KNumber(1923.45678,Formato.IMP1));
		 System.out.println("*********************> "+new KNumber(1923.45678,Formato.IMP2));

		 KNumber num2 = new KNumber(1936.27,Formato.IMP1);
		 num2.setPrefix("(");
		 num2.setSuffix(")");
		 System.out.println("*********************> "+num2);
		 num2.setPrefix("");
		 num2.setSuffix(" €");
		 System.out.println("*********************> "+num2);

		 String nm = null;
		 KNumber num3 = new KNumber(nm,Formato.IMP1);
		 System.out.println("*********************> "+num3);
		 System.out.println("*********************> "+num3.getDouble());
		 num3.setUndefinedNumber(0.0);
		 System.out.println("*********************> "+num3.getDouble());

		 System.out.println("*********************> "+(new KNumber(1923.45678,Formato.IMP0)).equals(new KNumber(1923.45678,Formato.IMP1)));

		 System.out.println("*********************> INDEFINITO: "+new KNumber());

		 System.out.println("*********************> NaN: "+new KNumber(Double.NaN,Formato.IMP0));
		 System.out.println("*********************> NaN: "+new KNumber(Float.NaN,Formato.IMP0));

		 System.out.println("---------------------> "+new KNumber("1923.41678",KNumber.$_$$0_0));
		 System.out.println("---------------------> "+new KNumber("1923.43678",KNumber.$_$$0_0));
		 System.out.println("---------------------> "+new KNumber("1923.45000",KNumber.$_$$0_0));
		 System.out.println("---------------------> "+new KNumber("1923.45678",KNumber.$_$$0_0));
		 System.out.println("---------------------> "+new KNumber("1923.47678",KNumber.$_$$0_0));

		 System.out.println("---------------------> "+new KNumber(1923.41678,KNumber.$_$$0_0));
		 System.out.println("---------------------> "+new KNumber(1923.43678,KNumber.$_$$0_0));
		 System.out.println("---------------------> "+new KNumber(1923.45000,KNumber.$_$$0_0));
		 System.out.println("---------------------> "+new KNumber(1923.45678,KNumber.$_$$0_0));
		 System.out.println("---------------------> "+new KNumber(1923.47678,KNumber.$_$$0_0));

		 System.out.println("---------------------> "+new KNumber(23.41678,KNumber.$_$$0_00_P));
		 System.out.println("---------------------> "+new KNumber(1923.41678,"'('#,##0.00')'"));

		 System.out.println("*********************> "+(new KNumber(1923.45678,$_$$0_0)).equals(new KNumber(1923.45678,$_$$0_00)));
		 System.out.println("*********************> NaN: "+new KNumber(Double.NaN,$_$$0_0));
		 System.out.println("*********************> NaN: "+new KNumber(Float.NaN,$_$$0_0));

		 KNumber_Params params = new KNumber_Params($_$$0_00,ND_VALUE);
		 System.out.println("---------------------> NaN: "+new KNumber(Double.NaN,params));
		 System.out.println("---------------------> NaN: "+new KNumber(1936.273,params));
		 System.out.println("---------------------> "+new KNumber(1936.273,"$,$$&.&&"));
		 System.out.println("---------------------> "+new KNumber("1927xxx",KNumber.$_$$0_0));
		 System.out.println("---------------------> "+new KNumber("(1927)",KNumber.$_$$0_0));

		 KNumber num4 = new KNumber("5.120.345,12445", KNumber.LOCALE_IT, KNumber.$_$$0_00);
		 System.out.println("*********************> double: "+num4.getDouble());
		 System.out.println("*********************> double: "+num4);
	 }

}//EO KNumber
