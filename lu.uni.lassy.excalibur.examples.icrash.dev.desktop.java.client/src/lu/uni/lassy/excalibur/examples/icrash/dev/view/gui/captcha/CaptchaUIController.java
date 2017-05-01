package lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.captcha;

import java.rmi.RemoteException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActProxyAuthenticated;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCaptcha;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCaptchaResponse;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;

public class CaptchaUIController {

	@FXML ImageView img0;
	@FXML ImageView img1;
	@FXML ImageView img2;
	@FXML ImageView img3;
	@FXML ImageView img4;
	@FXML ImageView img5;
	@FXML ImageView img6;
	@FXML ImageView img7;
	@FXML ImageView img8;
	
	@FXML Label labelQuestion;
	
	private final boolean selection[] = new boolean[9];
	private final ActProxyAuthenticated actAuthenticated;
	private final DtCaptcha captchaTest;
	
	CaptchaUIController(DtCaptcha captchaTest, ActProxyAuthenticated actAuthenticated){
		this.captchaTest = captchaTest;
		this.actAuthenticated = actAuthenticated;
	}
	
	@FXML
    public void initialize() {//TODO: Load images from actual captcha test
		labelQuestion.setText(captchaTest.getQuestion().toString());
		img0.setImage(new Image("http://kremi151.square7.ch/captcha/captcha_0.jpg"));
		img1.setImage(new Image("http://kremi151.square7.ch/captcha/captcha_1.jpg"));
		img2.setImage(new Image("http://kremi151.square7.ch/captcha/captcha_2.jpg"));
		img3.setImage(new Image("http://kremi151.square7.ch/captcha/captcha_3.jpg"));
		img4.setImage(new Image("http://kremi151.square7.ch/captcha/captcha_4.jpg"));
		img5.setImage(new Image("http://kremi151.square7.ch/captcha/captcha_5.jpg"));
		img6.setImage(new Image("http://kremi151.square7.ch/captcha/captcha_6.jpg"));
		img7.setImage(new Image("http://kremi151.square7.ch/captcha/captcha_7.jpg"));
		img8.setImage(new Image("http://kremi151.square7.ch/captcha/captcha_8.jpg"));
	}
	
	@FXML
	protected void btnSendCaptcha_OnClick(){
		try {
			actAuthenticated.oeSubmitCaptcha(new DtCaptchaResponse(captchaTest.getId(), new PtString(buildBinaryAnswerString())));
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}
		labelQuestion.getScene().getWindow().hide();
	}
	
	private String buildBinaryAnswerString(){
		StringBuilder sb = new StringBuilder();
		int currentChar = 0;
		for(int i = 0 ; i < 9 ; i++){
			currentChar |= (selection[i] ? (1 << i) : 0);
		}
		sb.append((char)(currentChar & 255));
		sb.append((char)((currentChar >> 8) & 255));
		return sb.toString();
	}
	
	@FXML
	protected void img0_onClick(){
		if(selection[0]){
			img0.setStyle("");
			selection[0] = false;
		}else{
			img0.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
			selection[0] = true;
		}
	}
	
	@FXML
	protected void img1_onClick(){
		if(selection[1]){
			img1.setStyle("");
			selection[1] = false;
		}else{
			img1.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
			selection[1] = true;
		}
	}
	
	@FXML
	protected void img2_onClick(){
		if(selection[2]){
			img2.setStyle("");
			selection[2] = false;
		}else{
			img2.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
			selection[2] = true;
		}
	}
	
	@FXML
	protected void img3_onClick(){
		if(selection[3]){
			img3.setStyle("");
			selection[3] = false;
		}else{
			img3.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
			selection[3] = true;
		}
	}
	
	@FXML
	protected void img4_onClick(){
		if(selection[4]){
			img4.setStyle("");
			selection[4] = false;
		}else{
			img4.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
			selection[4] = true;
		}
	}
	
	@FXML
	protected void img5_onClick(){
		if(selection[5]){
			img5.setStyle("");
			selection[5] = false;
		}else{
			img5.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
			selection[5] = true;
		}
	}
	
	@FXML
	protected void img6_onClick(){
		if(selection[6]){
			img6.setStyle("");
			selection[6] = false;
		}else{
			img6.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
			selection[6] = true;
		}
	}
	
	@FXML
	protected void img7_onClick(){
		if(selection[7]){
			img7.setStyle("");
			selection[7] = false;
		}else{
			img7.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
			selection[7] = true;
		}
	}
	
	@FXML
	protected void img8_onClick(){
		if(selection[8]){
			img8.setStyle("");
			selection[8] = false;
		}else{
			img8.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
			selection[8] = true;
		}
	}
}
