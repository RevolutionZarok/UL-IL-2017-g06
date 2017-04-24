package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary;

import java.io.Serializable;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIs;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtInteger;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtInteger;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;

public class DtCaptchaImage extends DtString implements Serializable, JIntIs{
//TODO: Complete this class
	private static final long serialVersionUID = -7909701326466541888L;
	
	private DtInteger width, height;

	public DtCaptchaImage(PtString binary, PtInteger width, PtInteger height) {
		super(binary);
		this.width = new DtInteger(width);
		this.height = new DtInteger(height);
	}

	@Override
	public PtBoolean is() {
		return new PtBoolean(true);//TODO
	}

}
