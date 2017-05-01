package lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.captcha;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActProxyAuthenticated;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCaptcha;

public class CaptchaUI {
	
	private final Parent root;

	public CaptchaUI(DtCaptcha captchaTest, ActProxyAuthenticated actAuthenticated){
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("CaptchaUI.fxml"));
			loader.setController(new CaptchaUIController(captchaTest, actAuthenticated));
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("This should not happen");
		}

	}
	
	public void show(){
		Platform.runLater(() -> {
			Stage stage = new Stage();
	        stage.setTitle("Captcha verification");
	        stage.setScene(new Scene(root, 600, 550));
	        stage.show();
		});
	}
}
