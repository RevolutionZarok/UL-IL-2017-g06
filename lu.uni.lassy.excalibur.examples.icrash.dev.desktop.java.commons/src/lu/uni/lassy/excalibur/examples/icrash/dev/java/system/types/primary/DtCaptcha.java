package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary;

import java.io.Serializable;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIs;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;

public class DtCaptcha implements Serializable, JIntIs{//TODO: Complete this class (Images)
	
	private static final long serialVersionUID = 7612248309713646319L;

	private DtCaptchaId id;
	private DtString question;
	
	public DtCaptcha(DtCaptchaId id, PtString question){
		this.id = id;
		this.question = new DtString(question);
	}

	@Override
	public PtBoolean is() {
		return new PtBoolean(id.is().getValue() && question.value.getValue().length() >= 10);
	}
	
	public DtCaptchaId getId(){//TODO: Messir
		return id;
	}

}
