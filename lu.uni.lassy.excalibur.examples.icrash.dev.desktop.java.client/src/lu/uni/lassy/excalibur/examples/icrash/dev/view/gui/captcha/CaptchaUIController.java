package lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.captcha;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActProxyAuthenticated;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtCaptcha;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCaptchaResponse;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtInteger;

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
	private final CtCaptcha captchaTest;
	
	CaptchaUIController(CtCaptcha captchaTest, ActProxyAuthenticated actAuthenticated){
		this.captchaTest = captchaTest;
		this.actAuthenticated = actAuthenticated;
	}
	
	@FXML
    public void initialize() {
		labelQuestion.setText(captchaTest.getQuestion().toString());
		System.out.println("Loading images...");
		img0.setImage(new Image(captchaTest.getImageAt(new PtInteger(0)).getUrl().toString()));
		img1.setImage(new Image(captchaTest.getImageAt(new PtInteger(1)).getUrl().toString()));
		img2.setImage(new Image(captchaTest.getImageAt(new PtInteger(2)).getUrl().toString()));
		img3.setImage(new Image(captchaTest.getImageAt(new PtInteger(3)).getUrl().toString()));
		img4.setImage(new Image(captchaTest.getImageAt(new PtInteger(4)).getUrl().toString()));
		img5.setImage(new Image(captchaTest.getImageAt(new PtInteger(5)).getUrl().toString()));
		img6.setImage(new Image(captchaTest.getImageAt(new PtInteger(6)).getUrl().toString()));
		img7.setImage(new Image(captchaTest.getImageAt(new PtInteger(7)).getUrl().toString()));
		img8.setImage(new Image(captchaTest.getImageAt(new PtInteger(8)).getUrl().toString()));
		System.out.println("Done");
	}
	
	@FXML
	protected void btnSendCaptcha_OnClick(){
		try {
			actAuthenticated.oeSubmitCaptcha(new DtCaptchaResponse(captchaTest.getId(), DtCaptchaResponse.buildBinaryAnswer(selection)));
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		} catch (NotBoundException e) {
			throw new RuntimeException(e);
		}
		labelQuestion.getScene().getWindow().hide();
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
