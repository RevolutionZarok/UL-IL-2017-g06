package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary;

import java.io.Serializable;
import java.util.HashMap;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIs;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtInteger;

public class DtCaptchaResponseMap implements Serializable, JIntIs {

	private static final long serialVersionUID = -7836963821133592220L;
	
	private final HashMap<DtCaptchaId, DtCaptchaResponse> map;
	
	public DtCaptchaResponseMap(){
		this.map = new HashMap<DtCaptchaId, DtCaptchaResponse>();
	}

	@Override
	public PtBoolean is() {
		return new PtBoolean(true);
	}
	
	public PtBoolean register(DtCaptchaResponse AdtResponse){
		return new PtBoolean(map.put(AdtResponse.getId(), AdtResponse) == null);
	}
	
	public DtCaptchaResponse get(DtCaptchaId AId){
		DtCaptchaResponse res = map.get(AId);
		if(res != null){
			return res;
		}else{
			return new DtCaptchaResponse(new DtCaptchaId(new PtInteger(-1)), new PtInteger(-1));
		}
	}

	public PtBoolean remove(DtCaptchaId AId){
		return new PtBoolean(map.remove(AId) != null);
	}
	
}
