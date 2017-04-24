package lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors;

import java.io.Serializable;
import java.rmi.RemoteException;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIsActor;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCaptcha;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCaptchaId;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCaptchaResponse;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;

public interface ActCaptchaService extends java.rmi.Remote, Serializable, JIntIsActor {

	public PtBoolean ieGenerateGaptcha() throws RemoteException;
	public PtBoolean ieVerifyCaptcha(DtCaptchaResponse AdtCaptchaResponse) throws RemoteException;
	
	public PtBoolean oeSendCaptcha(DtCaptcha AdtCaptcha, DtCaptchaResponse AdtCaptchaResponse) throws RemoteException;
	public PtBoolean oeCaptchaInvalid(DtCaptchaId AdtCaptchaId) throws RemoteException;
	public PtBoolean oeCaptchaValid(DtCaptchaId AdtCaptchaId) throws RemoteException;
}
