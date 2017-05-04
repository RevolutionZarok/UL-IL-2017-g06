package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary;

import java.io.Serializable;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIs;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;

public class CtCaptchaImage implements Serializable, JIntIs{
//TODO: Complete this class
	private static final long serialVersionUID = -7909701326466541888L;
	
	private DtString url;

	public CtCaptchaImage(PtString url) {
		this.url = new DtString(url);
	}

	@Override
	public PtBoolean is() {
		return new PtBoolean(true);//TODO
	}
	
	public DtString getUrl(){
		return url;
	}

}
