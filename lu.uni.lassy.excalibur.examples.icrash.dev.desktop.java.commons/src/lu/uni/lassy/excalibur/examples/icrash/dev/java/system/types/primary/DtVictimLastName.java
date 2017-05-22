package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIs;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;

public class DtVictimLastName extends DtString implements JIntIs {
	/**
	 * 
	 */
	private static final long serialVersionUID = 227L;

	public DtVictimLastName(PtString s){
		super(s);
	}
	
	/** The maximum length for the last name*/
	private int _maxLength = 50;
	
	public PtBoolean is(){
		return new PtBoolean(this.value.getValue().length() <= _maxLength);
	}
	
	public PtString getExpectedDataStructure(){
		return new PtString("Expected strucutre of the victim's last name is to have a maximum length of" + _maxLength); 
	}
}