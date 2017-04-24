package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary;

import java.io.Serializable;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIs;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtInteger;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtInteger;

public class DtCaptchaId extends DtInteger implements Serializable, JIntIs{

	private static final long serialVersionUID = 3185974190877124804L;

	public DtCaptchaId(PtInteger id) {
		super(id);
	}

	@Override
	public PtBoolean is() {
		return new PtBoolean(value.getValue() >= 0);
	}

}
