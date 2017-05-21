package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIs;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;

public class DtResetCode extends DtString implements JIntIs{

	private static final long serialVersionUID = 5059103203282388572L;

	public DtResetCode(PtString s) {
		super(s);
	}

	@Override
	public PtBoolean is() {
		return new PtBoolean(this.value.getValue().length() == 8);
	}

}
