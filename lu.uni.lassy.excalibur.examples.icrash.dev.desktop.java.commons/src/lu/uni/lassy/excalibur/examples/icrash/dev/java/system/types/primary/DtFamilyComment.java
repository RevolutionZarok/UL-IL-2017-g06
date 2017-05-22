package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIs;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;

public class DtFamilyComment extends DtString implements JIntIs {
	/**
	 * 
	 */
	private static final long serialVersionUID = 227L;

	public DtFamilyComment(PtString s){
		super(s);
	}
	
	/** The maximum length of a family comment. */
	private int _maxLength = 160;

	public PtBoolean is() {
		return new PtBoolean((this.value.getValue().length() <= _maxLength));
	}
}