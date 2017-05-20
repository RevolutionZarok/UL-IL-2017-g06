package lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.password_reset;

import java.awt.Color;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.IcrashSystem;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtLogin;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtPassword;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.RmiUtils;

public class PasswordResetGUIController {

	@FXML private TextField txtFieldLogin;
	@FXML private TextField txtFieldResetCode;
	@FXML private PasswordField txtFieldPwd;
	@FXML private PasswordField txtFieldConfirmPwd;
	@FXML private Button btnReset;
	
	private Runnable closeHandler;
	
	void setCloseHandler(Runnable closeHandler){
		this.closeHandler = closeHandler;
	}
	
	@FXML
	protected void initialize(){
		btnReset.requestFocus();
		txtFieldLogin.textProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue.length() == 0){
				setBackgroundColor(txtFieldLogin, Color.RED);
			}else{
				setBackgroundColor(txtFieldLogin, Color.WHITE);
			}
		});
		txtFieldResetCode.textProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue.length() == 0){
				setBackgroundColor(txtFieldResetCode, Color.RED);
			}else{
				setBackgroundColor(txtFieldResetCode, Color.WHITE);
			}
		});
		txtFieldPwd.textProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue.length() == 0){
				setBackgroundColor(txtFieldPwd, Color.RED);
			}else if(!txtFieldPwd.getText().equals(txtFieldConfirmPwd.getText())){
				setBackgroundColor(txtFieldPwd, Color.YELLOW);
				setBackgroundColor(txtFieldConfirmPwd, Color.YELLOW);
			}else{
				setBackgroundColor(txtFieldPwd, Color.WHITE);
				setBackgroundColor(txtFieldConfirmPwd, Color.WHITE);
			}
		});
		txtFieldConfirmPwd.textProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue.length() == 0){
				setBackgroundColor(txtFieldConfirmPwd, Color.RED);
			}else if(!txtFieldPwd.getText().equals(txtFieldConfirmPwd.getText())){
				setBackgroundColor(txtFieldPwd, Color.YELLOW);
				setBackgroundColor(txtFieldConfirmPwd, Color.YELLOW);
			}else{
				setBackgroundColor(txtFieldPwd, Color.WHITE);
				setBackgroundColor(txtFieldConfirmPwd, Color.WHITE);
			}
		});
	}
	
	@FXML
	protected void btnReset_OnClick(ActionEvent event) throws RemoteException, NotBoundException{
		if(verifyCorrectness()){
			DtLogin login = new DtLogin(new PtString(txtFieldLogin.getText()));
			DtString resetCode = new DtString(new PtString(txtFieldResetCode.getText()));
			DtPassword pwd = new DtPassword(new PtString(txtFieldPwd.getText()));
			Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());
		 	IcrashSystem iCrashSys_Server = (IcrashSystem)registry.lookup("iCrashServer");
			if(iCrashSys_Server.oeTryPasswordReset(login, resetCode, pwd).getValue()){
				showConfirmationMessage("Account reactivation", "Your account has been reactivated. You can now log in with your new password.");
				if(closeHandler != null)closeHandler.run();
			}else{
				showWarningMessage("Account reactivation", "The account could not be reactivated. Either the given login name is unknown or the entered reset code is incorrect. Please try again.");
			}
		}else{
			showWarningMessage("Incomplete information", "Some information are missing or invalid. Please try again.");
		}
	}
	
	@FXML
	protected void linkSendCode_OnClick(ActionEvent event) throws RemoteException, NotBoundException{
		if(txtFieldLogin.getText().length() > 0){
			Registry registry = LocateRegistry.getRegistry(RmiUtils.getInstance().getHost(),RmiUtils.getInstance().getPort());
		 	IcrashSystem iCrashSys_Server = (IcrashSystem)registry.lookup("iCrashServer");
		 	if(iCrashSys_Server.oeSendResetCodePerMail(new DtLogin(new PtString(txtFieldLogin.getText()))).getValue()){
		 		showConfirmationMessage("Send reset code", "Your reset code has been sent to your indicated e-mail address. Please have a look in your inbox.");
		 	}else{
		 		showWarningMessage("Send reset code", "Your reset code could not be sent via e-mail. Either the given coordinator login name is unknown or an unexpected error occured. Please try again.");
		 	}
		}else{
			showWarningMessage("Incomplete information", "Please enter the login name for your account.");
		}
	}
	
	private void showConfirmationMessage(String title, String message){
		showMessage(title, message, AlertType.CONFIRMATION);
	}
	
	private void showWarningMessage(String title, String message){
		showMessage(title, message, AlertType.WARNING);
	}
	
	private void showMessage(String title, String message, AlertType type){
		Alert alert = new Alert(type);
		alert.setHeaderText(title);
		alert.setContentText(message);
		//alert.initOwner(window);
		alert.initModality(Modality.WINDOW_MODAL);
		alert.showAndWait();
	}
	
	private void setBackgroundColor(Node node, Color color){
		node.setStyle("-fx-control-inner-background: " + String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()));
	}
	
	private boolean verifyCorrectness(){
		return txtFieldLogin.getText().length() > 0 && txtFieldResetCode.getText().length() == 8
				&& txtFieldPwd.getText().length() > 0 && txtFieldConfirmPwd.getText().length() > 0
				&& txtFieldPwd.getText().equals(txtFieldConfirmPwd.getText());
	}
}
