package lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary;

import java.io.Serializable;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIs;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtInteger;

public class DtCaptchaResponse implements Serializable, JIntIs{

	private static final long serialVersionUID = 2666825432999015497L;

	private DtCaptchaId id;
	private PtInteger response;
	
	public DtCaptchaResponse(DtCaptchaId id, PtInteger response){
		this.id = id;
		this.response = response;
	}

	@Override
	public PtBoolean is() {
		return new PtBoolean(id.is().getValue());
	}
	
	public DtCaptchaId getId(){
		return id;
	}
	
	public PtInteger getResponse(){
		return response;
	}
	
	public PtBoolean compare(DtCaptchaResponse response){
		return new PtBoolean(equals(response));
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof DtCaptchaResponse){
			DtCaptchaResponse ores = (DtCaptchaResponse) obj;
			return ores.id.equals(this.id) && ores.response.getValue() == this.response.getValue();
		}else{
			return false;
		}
	}
	
	@Override
	public int hashCode(){
		return (id.hashCode() * 32) + response.getValue();
	}
	
	public static PtInteger buildBinaryAnswer(boolean answer[]){
		if(answer.length != 9){
			throw new IllegalArgumentException();
		}
		int res = 0;
		for(int i = 0 ; i < 9 ; i++){
			res |= (answer[i]?1:0) << i;
		}
		return new PtInteger(res);
	}

}
