package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary;

import java.io.Serializable;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIs;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;

public class DtCaptchaResponse implements Serializable, JIntIs{

	private static final long serialVersionUID = 2666825432999015497L;

	private DtCaptchaId id;
	private DtString response;
	
	public DtCaptchaResponse(DtCaptchaId id, PtString response){
		this.id = id;
		this.response = new DtString(response);
	}

	@Override
	public PtBoolean is() {
		return new PtBoolean(id.is().getValue() && response.value.getValue().length() >= 0);
	}

}
