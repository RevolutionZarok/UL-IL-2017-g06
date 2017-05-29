/*******************************************************************************
 * Copyright (c) 2014-2015 University of Luxembourg.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alfredo Capozucca - initial API and implementation
 *     Christophe Kamphaus - Remote implementation of Actors
 *     Thomas Mortimer - Updated client to MVC and added new design patterns
 ******************************************************************************/
package lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.coordinator;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
/*
 * This is the import section to be replaced by modifications in the ICrash.fxml document from the sample skeleton controller
 */
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.util.Callback;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.CoordinatorController;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.IncorrectActorException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.IncorrectFormatException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.ServerNotBoundException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.ServerOfflineException;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActProxyAuthenticated.UserType;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIsActor;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtAlert;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtCrisis;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtAlertStatus;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtCrisisStatus;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.model.Message;
import lu.uni.lassy.excalibur.examples.icrash.dev.model.actors.ActProxyCoordinatorImpl;
import lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.AbstractAuthGUIController;
/*
 * This is the end of the import section to be replaced by modifications in the ICrash.fxml document from the sample skeleton controller
 */
/**
 * The Class ICrashGUIController, used for dealing with the GUI for the coordinator.
 */
public class ICrashCoordGUIController extends AbstractAuthGUIController {
	/*
	* This section of controls and methods is to be replaced by modifications in the ICrash.fxml document from the sample skeleton controller
	* When replacing, remember to reassign the correct methods to the button event methods and set the correct types for the tableviews
	*/

	/** The logon pane that holds the controls for logging on. */
	@FXML
    private Pane pnLogon;

    /** The textfield for entering in the username for logging on. */
    @FXML
    private TextField txtfldCoordLogonUserName;

    /** The passwordfield for entering in the password for logging on. */
    @FXML
    private PasswordField psswrdfldCoordLogonPassword;

    /** The button that allows a user to initiate the logon function. */
    @FXML
    private Button bttnCoordLogon;

    /** The main tabpane that holds the normal user controls. */
    @FXML
    private TabPane tbpnMain;

    /** The tab containing the controls for alerts. */
    @FXML
    private Tab tbCoordAlerts;

    /** The button that allows validation of the alert. */
    @FXML
    private Button bttnValidateAlert;

    /** The button that allows invalidation of the alert. */
    @FXML
    private Button bttnInvalidateAlert;

    /** The combobox that allows a user to select which alert status type to view. */
    @FXML
    private ComboBox<EtAlertStatus> cmbbxAlertStatus;

    /** The tableview of the alerts the user has retrieved from the system. */
    @FXML
    private TableView<CtAlert> tblvwAlerts;

    /** The tab containing the controls for crises. */
    @FXML
    private Tab tbCoordCrisis;

    /** The button that allows a user to handle a crisis. */
    @FXML
    private Button bttnHandleCrisis;

    /** The button that allows a user to close a crisis. */
    @FXML
    private Button bttnCloseCrisis;

    /** The button that allows a user to report on a crisis. */
    @FXML
    private Button bttnReportCrisis;

    /** The button that allows a user to change the status of a crisis. */
    @FXML
    private Button bttnChangeStatusCrisis;

    /** The combobox that allows a user to select which crisis status type to view. */
    @FXML
    private ComboBox<EtCrisisStatus> cmbbxCrisisStatus;

    /** The tablview that shows the user the crises they have selected. */
    @FXML
    private TableView<CtCrisis> tblvwCrisis;

    /** The tableview of the messages the user has recieved. */
    @FXML
    private TableView<Message> tblvwCoordMessages;

    /** The button that allows a user to logoff. */
    @FXML
    private Button bttnCoordLogoff;

    /**
     * Button event that deals with changing the status of a crisis
     *
     * @param event The event type fired, we do not need it's details
     */
    @FXML
    void bttnChangeStatusCrisis_OnClick(ActionEvent event) {
    	changeCrisisStatus();
    }

    /**
     * Button event that deals with closing a crisis
     *
     * @param event The event type fired, we do not need it's details
     */
    @FXML
    void bttnCloseCrisis_OnClose(ActionEvent event) {
    	closeCrisis();
    }

    /**
     * Button event that deals with logging off the user
     *
     * @param event The event type fired, we do not need it's details
     */
    @FXML
    void bttnCoordLogoff_OnClick(ActionEvent event) {
    	logoff();
    }

    /**
     * Button event that deals with logging on the user
     *
     * @param event The event type fired, we do not need it's details
     */
    @FXML
    void bttnCoordLogon_OnClick(ActionEvent event) {
    	logon();
    }

    /**
     * Button event that deals with handling of a crisis
     *
     * @param event The event type fired, we do not need it's details
     */
    @FXML
    void bttnHandleCrisis_OnClick(ActionEvent event) {
    	handleCrisis();
    }

    /**
     * Button event that deals with invalidating of an alert
     *
     * @param event The event type fired, we do not need it's details
     */
    @FXML
    void bttnInvalidateAlert_OnClick(ActionEvent event) {
    	invalidateAlert();
    }

    /**
     * Button event that deals with reportinng on a crisis
     *
     * @param event The event type fired, we do not need it's details
     */
    @FXML
    void bttnReportCrisis_OnClick(ActionEvent event) {
    	reportOnCrisis();
    }

    /**
     * Button event that deals with validating an alert
     *
     * @param event The event type fired, we do not need it's details
     */
    @FXML
    void bttnValidateAlert_OnClick(ActionEvent event) {
    	validateAlert();
    }
    
    /*
     * These are other classes accessed by this controller
     */
	
	/** The user controller, for this GUI it's the coordinator controller and allows access to coordinator functions like reporting on crises. */
	private CoordinatorController userController;
	
	/*
	 * Other things created for this controller
	 */
	//NOTHING HERE
	
	/*
	 * Methods used within the GUI
	 */
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.HasTables#setUpTables()
	 */
	public void setUpTables(){
		setUpMessageTables(tblvwCoordMessages);
		setUpCrisesTables(tblvwCrisis);
		setUpAlertTables(tblvwAlerts);
		cmbbxCrisisStatus.setItems( FXCollections.observableArrayList( EtCrisisStatus.values()));
		cmbbxAlertStatus.setItems( FXCollections.observableArrayList( EtAlertStatus.values()));
	}
	
	/**
	 * Populates the tableview with a list of crisis that have the same status as the one provided.
	 */
	private void populateCrisis(){
		try {
			userController.oeGetCrisisSet(cmbbxCrisisStatus.getValue());
		} catch (ServerOfflineException | ServerNotBoundException e) {
			showServerOffLineMessage(e);
		}
	}
	
	/**
	 * Populates the tableview with a list of alerts that have the same status as the one provided.
	 */
	private void populateAlerts(){
		try {
			userController.oeGetAlertSet(cmbbxAlertStatus.getValue());
		} catch (ServerOfflineException | ServerNotBoundException e) {
			showServerOffLineMessage(e);
		}
	}
	
	/**
	 * Runs the function that will allow the current user to handle the selected crisis.
	 */
	private void handleCrisis(){
		CtCrisis crisis = (CtCrisis)getObjectFromTableView(tblvwCrisis);
		if (crisis != null){
			try {
				if (!userController.handleCrisis(crisis.id.value.getValue()).getValue())
					showWarningMessage("Unable to handle crisis", "Unable to handle crisis, please try again");
			} catch (ServerOfflineException | ServerNotBoundException e) {
				showServerOffLineMessage(e);
			} catch (IncorrectFormatException e) {
				showWarningIncorrectInformationEntered(e);
			}
		}
		populateCrisis();
	}
	
	/**
	 * Runs the function that will allow the current user to close the selected crisis.
	 */
	private void closeCrisis(){
		CtCrisis crisis = (CtCrisis)getObjectFromTableView(tblvwCrisis);
		if (crisis != null)
		{
			try {
				userController.closeCrisis(crisis.id.value.getValue());
			} catch (ServerOfflineException | ServerNotBoundException e) {
				showServerOffLineMessage(e);
			} catch (IncorrectFormatException e) {
				showWarningIncorrectInformationEntered(e);
			}
		}
		populateCrisis();
	}
	
	/**
	 * Runs the function that will allow the current user to report on the selected crisis.
	 */
	private void reportOnCrisis(){
		CtCrisis crisis = (CtCrisis)getObjectFromTableView(tblvwCrisis);
		if (crisis != null)
		{
			Dialog<PtBoolean> dialog = new Dialog<PtBoolean>();
			TextField txtfldCtCrisisID = new TextField();
			txtfldCtCrisisID.setText(crisis.id.value.getValue());
			txtfldCtCrisisID.setDisable(true);
			TextArea txtarCtCrisisReport = new TextArea();
			txtarCtCrisisReport.setPromptText("Enter in report");
			ButtonType bttntypReportOK = new ButtonType("Report", ButtonData.OK_DONE);
			ButtonType bttntypeReportCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
			GridPane grdpn = new GridPane();
			grdpn.add(txtfldCtCrisisID, 1, 1);
			grdpn.add(txtarCtCrisisReport, 1, 2);
			dialog.getDialogPane().setContent(grdpn);
			dialog.getDialogPane().getButtonTypes().add(bttntypeReportCancel);
			dialog.getDialogPane().getButtonTypes().add(bttntypReportOK);
			dialog.setResultConverter(new Callback<ButtonType, PtBoolean>(){

				@Override
				public PtBoolean call(ButtonType param) {
					if (param.getButtonData() == ButtonData.OK_DONE && checkIfAllDialogHasBeenFilledIn(grdpn)){
						try {
							return userController.reportOnCrisis(crisis.id.value.getValue(), txtarCtCrisisReport.getText());
						} catch (ServerOfflineException | ServerNotBoundException e) {
							showServerOffLineMessage(e);
						} catch (IncorrectFormatException e) {
							showWarningIncorrectInformationEntered(e);
						}
					}
					//User cancelled the dialog
					return new PtBoolean(true);
				}
			});
			dialog.initOwner(window);
			dialog.initModality(Modality.WINDOW_MODAL);
			Optional<PtBoolean> result = dialog.showAndWait();
			if (result.isPresent()){
				if (!result.get().getValue())
					showWarningMessage("Unable to report on crisis", "Unable to report on the crisis, please try again");
			}
		}
		populateCrisis();
	}
	
	/**
	 * Runs the function that will allow the current user to change the selected crisis' status.
	 */
	private void changeCrisisStatus(){
		CtCrisis crisis = (CtCrisis)getObjectFromTableView(tblvwCrisis);
		if (crisis != null){
			Dialog<PtBoolean> dialog = new Dialog<PtBoolean>();
			dialog.setTitle("Change the crisis status");
			TextField txtfldCtCrisisID = new TextField();
			txtfldCtCrisisID.setText(crisis.id.value.getValue());
			txtfldCtCrisisID.setDisable(true);
			ComboBox<EtCrisisStatus> cmbbx = new ComboBox<EtCrisisStatus>();
			cmbbx.setItems( FXCollections.observableArrayList( EtCrisisStatus.values()));
			cmbbx.setValue(crisis.status);
			ButtonType bttntypOK = new ButtonType("Change status", ButtonData.OK_DONE);
			ButtonType bttntypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
			GridPane grdpn = new GridPane();
			grdpn.add(txtfldCtCrisisID, 1, 1);
			grdpn.add(cmbbx, 1, 2);
			dialog.getDialogPane().setContent(grdpn);
			dialog.getDialogPane().getButtonTypes().add(bttntypeCancel);
			dialog.getDialogPane().getButtonTypes().add(bttntypOK);
			dialog.setResultConverter(new Callback<ButtonType, PtBoolean>(){
				@Override
				public PtBoolean call(ButtonType param) {
					if (param.getButtonData() == ButtonData.OK_DONE && checkIfAllDialogHasBeenFilledIn(grdpn)){
						try {
							return userController.changeCrisisStatus(crisis.id.value.getValue(), cmbbx.getValue());
						} catch (ServerOfflineException | ServerNotBoundException e) {
							showServerOffLineMessage(e);
						} catch (IncorrectFormatException e) {
							showWarningIncorrectInformationEntered(e);
						}
					}
					//User cancelled the dialog
					return new PtBoolean(true);
				}
			});
			dialog.initOwner(window);
			dialog.initModality(Modality.WINDOW_MODAL);
			Optional<PtBoolean> result = dialog.showAndWait();
			if (result.isPresent()){
				if (!result.get().getValue())
					showWarningMessage("Unable to change status of crisis", "Unable to change status of crisis, please try again");
			}
		}
		populateCrisis();
	}
	
	/**
	 * Runs the function that will allow the current user to validate the selected alert.
	 */
	private void validateAlert(){
		CtAlert alert = (CtAlert)getObjectFromTableView(tblvwAlerts);
		if (alert != null)
			try {
				userController.validateAlert(alert.id.value.getValue());
			} catch (ServerOfflineException | ServerNotBoundException e) {
				showServerOffLineMessage(e);
			} catch (IncorrectFormatException e) {
				showWarningIncorrectInformationEntered(e);
			}
		populateAlerts();
		sendFamilyNotification();
	}
	
	private void sendFamilyNotification() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Runs the function that will allow the current user to invalidate the selected alert.
	 */
	private void invalidateAlert(){
		CtAlert alert = (CtAlert)getObjectFromTableView(tblvwAlerts);
		if (alert != null)
			try {
				if (!userController.invalidateAlert(alert.id.value.getValue()).getValue())
					showWarningMessage("Unable to invalidate alert", "Unable to invalidate alert, please try again");
			} catch (ServerOfflineException | ServerNotBoundException e) {
				showServerOffLineMessage(e);
			} catch (IncorrectFormatException e) {
				showWarningIncorrectInformationEntered(e);
			}
		populateAlerts();
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.AbstractAuthGUIController#logon()
	 */
	@Override
	public void logon() {
		if(txtfldCoordLogonUserName.getText().length() > 0 && psswrdfldCoordLogonPassword.getText().length() > 0){
			try {
				if (userController.oeLogin(txtfldCoordLogonUserName.getText(), psswrdfldCoordLogonPassword.getText()).getValue()){
					if (userController.getUserType() == UserType.Coordinator){
						logonShowPanes(true);
					}
				}
			}
			catch (ServerOfflineException | ServerNotBoundException e) {
				showExceptionErrorMessage(e);
			}
    	}
    	else
    		showWarningNoDataEntered();
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.AbstractAuthGUIController#logoff()
	 */
	@Override
	public void logoff() {
		try {
			if (userController.oeLogout().getValue()){
				logonShowPanes(false);
			}
		} catch (ServerOfflineException | ServerNotBoundException e) {
			showExceptionErrorMessage(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.AbstractAuthGUIController#logonShowPanes(boolean)
	 */
	protected void logonShowPanes(boolean loggedOn){
		tbpnMain.setVisible(loggedOn);
		bttnCoordLogoff.setDisable(!loggedOn);
		pnLogon.setVisible(!loggedOn);
		bttnCoordLogon.setDefaultButton(!loggedOn);
		if (loggedOn){
			tbpnMain.getSelectionModel().selectFirst();
			cmbbxAlertStatus.setValue(EtAlertStatus.pending);
			cmbbxCrisisStatus.setValue(EtCrisisStatus.pending);
		}
		else{
			txtfldCoordLogonUserName.setText("");
			psswrdfldCoordLogonPassword.setText("");
			txtfldCoordLogonUserName.requestFocus();
			
		}
	}

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.AbstractGUIController#closeForm()
	 */
	@Override
	public void closeForm() {
		try {
			userController.removeAllListeners();
		} catch (ServerOfflineException | ServerNotBoundException e) {
			showExceptionErrorMessage(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setUpTables();
		cmbbxAlertStatus.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				populateAlerts();
			}
		});	
		cmbbxCrisisStatus.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				populateCrisis();
			}
		});
		tbpnMain.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
			@Override
			public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
				if (newValue == tbCoordAlerts)
					populateAlerts();
				else if (newValue == tbCoordCrisis)
					populateCrisis();
			}
		});
		logonShowPanes(false);
	}

	@Override
	public PtBoolean setActor(JIntIsActor actor) {
		try { 
			if (actor instanceof ActCoordinator)
			{
				try{
					userController = new CoordinatorController((ActCoordinator)actor);
					try{
						userController.getAuthImpl().listOfMessages.addListener(new ListChangeListener<Message>() {
							@Override
							public void onChanged(ListChangeListener.Change<? extends Message> c) {
								addMessageToTableView(tblvwCoordMessages, c.getList());
							}
						});
						((ActProxyCoordinatorImpl)userController.getAuthImpl()).MapOfCtAlerts.addListener(new MapChangeListener<String, CtAlert>(){
				
							@Override
							public void onChanged(
									javafx.collections.MapChangeListener.Change<? extends String, ? extends CtAlert> change) {
								addAlertsToTableView(tblvwAlerts, change.getMap().values());
							}
						});
						((ActProxyCoordinatorImpl)userController.getAuthImpl()).MapOfCtCrisis.addListener(new MapChangeListener<String, CtCrisis>(){
							
							@Override
							public void onChanged(
									javafx.collections.MapChangeListener.Change<? extends String, ? extends CtCrisis> change) {
								addCrisesToTableView(tblvwCrisis, change.getMap().values());
							}
						});
					} catch (Exception e){
						showExceptionErrorMessage(e);
					}
				} catch (RemoteException e){
					Log4JUtils.getInstance().getLogger().error(e);
					throw new ServerOfflineException();
				} catch (NotBoundException e){
					Log4JUtils.getInstance().getLogger().error(e);
					throw new ServerNotBoundException();
				}
			}
			else
				throw new IncorrectActorException(actor, ActCoordinator.class);
		} catch (IncorrectActorException | ServerOfflineException | ServerNotBoundException e) {
			showExceptionErrorMessage(e);
			return new PtBoolean(false);
		}
		return new PtBoolean(true);
	}
}
