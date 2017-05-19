package lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtCaptcha;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtCaptchaImage;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCaptchaId;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCaptchaResponse;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCaptchaResponseMap;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtInteger;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.RmiUtils;

public class ActCaptchaServiceImpl extends UnicastRemoteObject implements ActCaptchaService{

	private static final long serialVersionUID = -5086057999828832836L;
	
	private DtCaptchaResponseMap responseMap = new DtCaptchaResponseMap();
	private static ActCaptchaServiceImpl instance;
	
	private final List<CImageRef> captchaImageDatabase;
	private final List<CaptchaQuestion> captchaQuestions;
	
	private final Random random;

	private ActCaptchaServiceImpl() throws RemoteException {
		super(RmiUtils.getInstance().getPort());
		
		this.random = new Random(System.currentTimeMillis());

		ArrayList<CImageRef> captchaImageDatabase = new ArrayList<>();
		captchaImageDatabase.add(new CImageRef("http://kremi151.square7.ch/captcha/captcha_0.jpg", "cat"));
		captchaImageDatabase.add(new CImageRef("http://kremi151.square7.ch/captcha/captcha_1.jpg", "dog"));
		captchaImageDatabase.add(new CImageRef("http://kremi151.square7.ch/captcha/captcha_2.jpg", "dog", "puppy"));
		captchaImageDatabase.add(new CImageRef("http://kremi151.square7.ch/captcha/captcha_3.jpg", "cat", "kitten"));
		captchaImageDatabase.add(new CImageRef("http://kremi151.square7.ch/captcha/captcha_4.jpg", "horse"));
		captchaImageDatabase.add(new CImageRef("http://kremi151.square7.ch/captcha/captcha_5.jpg", "cat"));
		captchaImageDatabase.add(new CImageRef("http://kremi151.square7.ch/captcha/captcha_6.jpg", "horse"));
		captchaImageDatabase.add(new CImageRef("http://kremi151.square7.ch/captcha/captcha_7.jpg", "dog"));
		captchaImageDatabase.add(new CImageRef("http://kremi151.square7.ch/captcha/captcha_8.jpg", "dog"));
		captchaImageDatabase.add(new CImageRef("http://kremi151.square7.ch/captcha/captcha_9.jpg", "cat"));
		captchaImageDatabase.add(new CImageRef("http://kremi151.square7.ch/captcha/captcha_10.jpg", "horse"));
		captchaImageDatabase.add(new CImageRef("http://kremi151.square7.ch/captcha/captcha_11.jpg", "horse"));
		captchaImageDatabase.add(new CImageRef("http://kremi151.square7.ch/captcha/captcha_12.jpg", "squirrel"));
		captchaImageDatabase.add(new CImageRef("http://kremi151.square7.ch/captcha/captcha_13.jpg", "squirrel"));
		captchaImageDatabase.add(new CImageRef("http://kremi151.square7.ch/captcha/captcha_14.jpg", "squirrel"));
		captchaImageDatabase.add(new CImageRef("http://kremi151.square7.ch/captcha/captcha_15.jpg", "squirrel"));
		this.captchaImageDatabase = Collections.unmodifiableList(captchaImageDatabase);
		
		ArrayList<CaptchaQuestion> captchaQuestions = new ArrayList<>();
		captchaQuestions.add(new CaptchaQuestion("Select the pictures containing a dog", "dog"));
		captchaQuestions.add(new CaptchaQuestion("Select the pictures containing a cat", "cat"));
		captchaQuestions.add(new CaptchaQuestion("Select the pictures containing a horse", "horse"));
		captchaQuestions.add(new CaptchaQuestion("Select the pictures containing a squirrel", "squirrel"));
		this.captchaQuestions = Collections.unmodifiableList(captchaQuestions);
	}
	
	private CaptchaCouple generateRandomCaptcha(){
		CaptchaQuestion question = captchaQuestions.get(random.nextInt(captchaQuestions.size()));
		ArrayList<CImageRef> targetImages = new ArrayList<>();
		ArrayList<CImageRef> fillerImages = new ArrayList<>(captchaImageDatabase);
		Iterator<CImageRef> fillerIterator = fillerImages.iterator();
		while(fillerIterator.hasNext()){
			CImageRef ref = fillerIterator.next();
			for(String tag : ref.tags){
				if(tag.equalsIgnoreCase(question.tag)){
					fillerIterator.remove();
					targetImages.add(ref);
					break;
				}
			}
		}
		boolean answers[] = new boolean[9];
		for(int i = 0 ; i < 9 ; i++)answers[i] = false;
		CtCaptchaImage captchaImages[] = new CtCaptchaImage[9];
		ArrayList<Integer> slotsLeft = new ArrayList<>(9);
		for(int i = 0 ; i < 9 ; i++)slotsLeft.add(i);
		final int maxTargetImages = 2 + random.nextInt(3);
		for(int i = 0 ; i < maxTargetImages && targetImages.size() > 0 ; i++){
			CImageRef ref = targetImages.remove(random.nextInt(targetImages.size()));
			int slot = slotsLeft.remove(random.nextInt(slotsLeft.size()));
			captchaImages[slot] = new CtCaptchaImage(ref.url);
			answers[slot] = true;
		}
		while(slotsLeft.size() > 0){
			CImageRef ref = fillerImages.remove(random.nextInt(fillerImages.size()));
			int slot = slotsLeft.remove(random.nextInt(slotsLeft.size()));
			captchaImages[slot] = new CtCaptchaImage(ref.url);
		}
		DtCaptchaId id = new DtCaptchaId(new PtInteger((int)(Integer.MAX_VALUE * Math.random())));
		DtCaptchaResponse captchaAnswer = new DtCaptchaResponse(id, DtCaptchaResponse.buildBinaryAnswer(answers));
		CtCaptcha captchaTest = new CtCaptcha(id, question.question.value, captchaImages);
		return new CaptchaCouple(captchaTest, captchaAnswer);
	}

	@Override
	public PtBoolean ieGenerateGaptcha() throws RemoteException, NotBoundException {
		Logger log = Log4JUtils.getInstance().getLogger();
		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());

		//Gathering the remote object as it was published into the registry
		IcrashSystem iCrashSys_Server = (IcrashSystem) registry
				.lookup("iCrashServer");
		
		log.info("Generating captcha test...");
		
		CaptchaCouple generated = generateRandomCaptcha();
		
		log.info("Saving captcha test and answer...");
		responseMap.register(generated.answer);
		
		iCrashSys_Server.oeSendCaptcha(generated.test);
		return new PtBoolean(true);
	}

	@Override
	public PtBoolean ieVerifyCaptcha(DtCaptchaResponse AdtCaptchaResponse) throws RemoteException, NotBoundException {
		Logger log = Log4JUtils.getInstance().getLogger();
		DtCaptchaResponse master = responseMap.get(AdtCaptchaResponse.getId());
		if(master.is().getValue()){
			if(master.compare(AdtCaptchaResponse).getValue()){
				oeCaptchaValid(master.getId());
				return new PtBoolean(true);
			}else{
				oeCaptchaInvalid(master.getId());
				return new PtBoolean(false);
			}
		}else{
			log.info("Unknown captcha test answer received");
		}
		return new PtBoolean(false);
	}

	@Override
	public PtBoolean oeSendCaptcha(CtCaptcha AdtCaptcha) throws RemoteException, NotBoundException {
		Logger log = Log4JUtils.getInstance().getLogger();
		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());
		IcrashSystem iCrashSys_Server = (IcrashSystem) registry
				.lookup("iCrashServer");
		log.info("Sending ieConfirmCaptcha to actAuthenticated");
		return iCrashSys_Server.getCurrentRequestingAuthenticatedActor().ieConfirmCaptcha(AdtCaptcha);
	}

	@Override
	public PtBoolean oeCaptchaInvalid(DtCaptchaId AdtCaptchaId) throws RemoteException, NotBoundException {
		Logger log = Log4JUtils.getInstance().getLogger();
		log.info("Captcha answer is invalid");
		
		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());
		IcrashSystem iCrashSys_Server = (IcrashSystem) registry
				.lookup("iCrashServer");
		iCrashSys_Server.getCurrentRequestingAuthenticatedActor().ieMessage(new PtString("Your submitted captcha response is invalid. Try again."));
		
		return new PtBoolean(true);
	}

	@Override
	public PtBoolean oeCaptchaValid(DtCaptchaId AdtCaptchaId) throws RemoteException, NotBoundException {
		Logger log = Log4JUtils.getInstance().getLogger();
		log.info("Captcha answer is valid, retrying login attempt...");
		Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());
		IcrashSystem iCrashSys_Server = (IcrashSystem) registry
				.lookup("iCrashServer");
		if(iCrashSys_Server.getCurrentRequestingAuthenticatedActor().oeLogin(
				iCrashSys_Server.getCurrentRequestingAuthenticatedLogin(),
				iCrashSys_Server.getCurrentRequestingAuthenticatedPassword()
				).getValue()){
			iCrashSys_Server.getCurrentRequestingAuthenticatedActor().ieCaptchaAuthenticationSucceeded();
		}
		
		return new PtBoolean(true);
	}
	
	public static ActCaptchaService getInstance() throws RemoteException{
		if(instance == null){
			instance = new ActCaptchaServiceImpl();
		}
		return instance;
	}
	
	private static class CImageRef{
		final PtString url;
		final String tags[];
		
		private CImageRef(String url, String... tags){
			this.url = new PtString(url);
			this.tags = tags;
		}
	}
	
	private static class CaptchaCouple{
		final CtCaptcha test;
		final DtCaptchaResponse answer;
		
		private CaptchaCouple(CtCaptcha test, DtCaptchaResponse answer){
			this.test = test;
			this.answer = answer;
		}
	}
	
	private static class CaptchaQuestion{
		final DtString question;
		final String tag;
		
		private CaptchaQuestion(String question, String tag){
			this.question = new DtString(new PtString(question));
			this.tag = tag;
		}
	}

}
