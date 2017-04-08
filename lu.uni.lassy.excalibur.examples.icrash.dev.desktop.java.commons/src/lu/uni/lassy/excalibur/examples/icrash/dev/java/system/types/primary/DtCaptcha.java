package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary;

import java.io.Serializable;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIs;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;

public class DtCaptcha implements Serializable, JIntIs{//TODO: Complete this class
	
	private static final long serialVersionUID = 7612248309713646319L;

	@Override
	public PtBoolean is() {
		return new PtBoolean(true);
	}

}
