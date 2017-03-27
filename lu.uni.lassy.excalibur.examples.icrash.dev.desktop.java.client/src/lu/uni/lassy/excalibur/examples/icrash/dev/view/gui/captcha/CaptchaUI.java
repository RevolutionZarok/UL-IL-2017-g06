package lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.captcha;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CaptchaUI {
	
	private final Parent root;

	public CaptchaUI(){
		try {
			root = FXMLLoader.load(getClass().getResource("CaptchaUI.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("This should not happen");
		}

	}
	
	public void show(){
		Stage stage = new Stage();
        stage.setTitle("Captcha verification");
        stage.setScene(new Scene(root, 600, 550));
        stage.show();
	}
}
