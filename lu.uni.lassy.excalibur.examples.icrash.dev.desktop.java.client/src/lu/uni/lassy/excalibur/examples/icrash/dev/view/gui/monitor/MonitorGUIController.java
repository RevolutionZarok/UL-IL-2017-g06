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
package lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.monitor;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.AlertController;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.CrisisController;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.HumanController;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.SystemStateController;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.NullValueException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.ServerNotBoundException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.ServerOfflineException;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIsActor;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtAdministrator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtAlert;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtCrisis;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtHuman;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtState;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.AbstractGUIController;
import lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.HasTables;
/*
 * This is the import section to be replaced by modifications in the ICrash.fxml document from the sample skeleton controller
 */
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;


/*
 * This is the end of the import section to be replaced by modifications in the ICrash.fxml document from the sample skeleton controller
 */

/**
 * The Class MonitorGUIController, which is used to control the GUI of the monitor window. This is a window created for observing data in the server.
 * If there is a difference between the database and what the monitor says, there could be an issue
 */
public class MonitorGUIController extends AbstractGUIController implements HasTables {
	/*
	* This section of controls and methods is to be replaced by modifications in the ICrash.fxml document from the sample skeleton controller
	* When replacing, remember to reassign the correct methods to the button event methods and set the correct types for the tableviews
	*/
	/** The tab pane of the monitor holding all the tableviews */
	@FXML
    private TabPane tbpnMonitor;
	
	/** The tableview of the alerts in the system. */
	@FXML
    private TableView<CtAlert> tblvwAlerts;

	/** The tableview of the crises in the system. */
    @FXML
    private TableView<CtCrisis> tblvwCrises;

    /** The tableview of the humans in the system. */
    @FXML
    private TableView<CtHuman> tblvwHumans;

    /** The tableview of the administrators in the system. */
    @FXML
    private TableView<CtAdministrator> tblvwAdministrators;

    /** The tableview of the coordinators in the system. */
    @FXML
    private TableView<CtCoordinator> tblvwCoordinators;

    /** The tableview of the communication companies in the system. */
    @FXML
    private TableView<String> tblvwComCompany;

    /** The tableview of the state of the system. */
    @FXML
    private TableView<CtState> tblvwCtState;

    /** The label showing the date and time this formwas last updated last updated. */
    @FXML
    private Label lblLastUpdated;

    /** The button that allows the user to refresh the data in the tables. */
    @FXML
    private Button bttnRefresh;

    /**
     * Button refresh event that runs on the click event.     
     * 
     * @param event The event fired by the button being clicked
     */
    @FXML
    void bttnRefresh_OnClick(ActionEvent event) {
    	populateTables();
    }
    /*
     * These are other classes accessed by this controller
     */
    /** The alert controller, which allows alert specific functions, like getting a list of alerts. */
    private AlertController alertController;
    
    /** The human controller, which allows human specific functions, like getting a list of humans.*/
    private HumanController humanController;
    
    /** The crisis controller, which allows crisis specific functions, like getting a list of crises. */
    private CrisisController crisisController;
    
    /** The system state controller, which allows state specific functions, like getting the current server date and time. */
    private SystemStateController systemStateController;
	
    /*
	 * Other things created for this controller
	 */
	/** The default text for the label showing the last time this GUI was updated. */
    private final String _textForLabelLastUpdated = "Last updated: ";
    
    /*
	 * Methods used within the GUI
	 */

    /**
     * Populates the monitor tables with the data.
     */
    public void populateTables(){
    	try {		
    		addStateToTableView(tblvwCtState, systemStateController.getServerState());
			addComCompaniesToTableView(tblvwComCompany, systemStateController.getListOfComCompaniesNames());
			addAlertsToTableView(tblvwAlerts, alertController.getListOfAlerts());
			addHumansToTableView(tblvwHumans, humanController.getAllHumans());
			addCrisesToTableView(tblvwCrises, crisisController.getAllCtCrises());
			//Moved these to the bottom, as most likely to throw the null pointer exception error
			addAdminsToTableView(tblvwAdministrators, systemStateController.getAllAdministrators());
			addCoordsToTableView(tblvwCoordinators, systemStateController.getAllCoordinators());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			lblLastUpdated.setText(_textForLabelLastUpdated + LocalDateTime.now().format(formatter));
		} catch (ServerOfflineException | ServerNotBoundException e) {
			showExceptionErrorMessage(e);
		} catch (NullPointerException e){
			Log4JUtils.getInstance().getLogger().error(e);
			showExceptionErrorMessage(new NullValueException(this.getClass()));
		}
    }
    
    /* (non-Javadoc)
     * @see lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.HasTables#setUpTables()
     */
    public void setUpTables(){
    	setUpAdminTables(tblvwAdministrators, true);
    	setUpAlertTables(tblvwAlerts);
    	setUpComCompaniesTables(tblvwComCompany);
    	setUpCoordTables(tblvwCoordinators, true);
    	setUpCrisesTables(tblvwCrises);
    	setUpHumansTables(tblvwHumans);
    	setUpStateTables(tblvwCtState);
    }

	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.AbstractGUIController#closeForm()
	 */
	@Override
	public void closeForm() {
		systemStateController.closeServerConnection();
		alertController.closeServerConnection();
		humanController.closeServerConnection();
		crisisController.closeServerConnection();
		humanController.closeServerConnection();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		systemStateController = new SystemStateController();
    	crisisController = new CrisisController();
    	alertController = new AlertController();
    	humanController = new HumanController();
    	setUpTables();
    	populateTables();
    	tbpnMonitor.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
			@Override
			public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
				populateTables();
			}
		});
	}

	@Override
	public PtBoolean setActor(JIntIsActor actor) {
		return new PtBoolean(true);
	}
}
