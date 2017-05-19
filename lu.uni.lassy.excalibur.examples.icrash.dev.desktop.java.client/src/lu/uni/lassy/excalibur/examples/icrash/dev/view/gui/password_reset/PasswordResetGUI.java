package lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.password_reset;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PasswordResetGUI {
	
	private final Parent root;
	private final PasswordResetGUIController controller;

	public PasswordResetGUI(){
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("PasswordResetGUI.fxml"));
			controller = new PasswordResetGUIController();
			loader.setController(controller);
			root = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("This should not happen");
		}

	}
	
	public void show(){
		Platform.runLater(() -> {
			Stage stage = new Stage();
	        stage.setTitle("Reactivate account");
	        controller.setCloseHandler(() -> stage.close());
	        stage.setScene(new Scene(root, 300, 300));
	        stage.show();
		});
	}
}
