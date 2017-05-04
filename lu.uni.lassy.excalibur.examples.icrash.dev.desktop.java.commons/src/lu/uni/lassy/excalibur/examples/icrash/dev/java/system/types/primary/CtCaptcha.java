package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary;

import java.io.Serializable;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIs;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtInteger;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;

public class CtCaptcha implements Serializable, JIntIs{//TODO: Complete this class (Images)
	
	private static final long serialVersionUID = 7612248309713646319L;

	private DtCaptchaId id;
	private DtString question;
	private CtCaptchaImage images[];
	
	public CtCaptcha(DtCaptchaId id, PtString question, CtCaptchaImage images[]){
		this.id = id;
		this.question = new DtString(question);
		this.images = images;
	}

	@Override
	public PtBoolean is() {
		return new PtBoolean(id.is().getValue() && question.value.getValue().length() >= 10);
	}
	
	public DtCaptchaId getId(){//TODO: Messir
		return id;
	}
	
	public DtString getQuestion(){//TODO: Messir
		return question;
	}
	
	public CtCaptchaImage getImageAt(PtInteger index){
		return images[index.getValue()];
	}

}
