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
package lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.creator;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
/*
 * Replace the next section with the imports from the controller skeleton from the FXML file
 */
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/*
 * 
 */
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.SystemStateController;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActComCompany;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIsActor;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtInteger;
import lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.AbstractGUIController;
import lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.CreatedWindows;
import lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.activator.CreateActivatorGUI;
import lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.admin.CreateICrashAdminGUI;
import lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.comcompany.CreateICrashComCompany;
import lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.monitor.CreateMonitor;
import javafx.stage.Screen;
import javafx.stage.Stage;


/**
 * The Class CreatorGUIController, used for creatin the initial main window.
 */
public class CreatorGUIController extends AbstractGUIController{
	
	/*
	 * Replace the next section with the controls and events from the  controller skeleton from the FXML file
	 */
	/** The textfield that will take the number of communication companies the user wants to create. */
    @FXML
    private TextField txtfldNumberOfComComp;

    /** The button that upon clicking will create the system and environment. */
    @FXML
    private Button bttnCreateSystemAndEnvir;

    /**
     * The button event that upon clicking will create the system and environment.
     *
     * @param event The actionevent passed, we ignore this
     */
    @FXML
    void bttnCreateSystemAndEnvir_OnClick(ActionEvent event) {
    	createSystemAndEnvrio();
    }
    /*
     * 
     */
    /** The system state controller, this is used to grant access to the MSR creator actor to start the system up */
    private SystemStateController systemStatecontroller = new SystemStateController();
    
    /** The list of windows created by this window. This assists with calling a shut down if this window is closed. */
    private ArrayList<CreatedWindows> listOfWindows = new ArrayList<CreatedWindows>();
    
    /**
     * Calls the method to create the system and environment and will then create a window for each actor in the system (Plus a monitor window for debugging).
     */
    private void createSystemAndEnvrio(){
			try {
				int numberOfCompCompanies = Integer.parseInt(txtfldNumberOfComComp.getText());
				if (numberOfCompCompanies < 1){
					showWarningIncorrectData("Communication companies must be more than 0");
				}else{
					if (systemStatecontroller.createSystemAnEnviroment(new PtInteger(numberOfCompCompanies)).getValue()){
						Screen screen = Screen.getPrimary();
						Rectangle2D bounds = screen.getVisualBounds();
						double rangeY = bounds.getMaxY() - bounds.getMinY();
						listOfWindows.add(new CreateICrashAdminGUI(systemStatecontroller.getActAdministrator(), bounds.getMinX(), bounds.getMinY()));
						double percent = 0.1;
						//Dealing with com company window placement
						ArrayList<ActComCompany> listOfComCompanies = systemStatecontroller.getActComCompany();
						//525 is the manual set width in the CommunicationCompanyGUI.fxml and 20 is the guestimate border size
						double widthOfForm = 525 + 20;
						//140 is the manual set height in the CommunicationCompanyGUI.fxml and 40 is the guestimate border size with title bar
						double heightOfForm = 140 + 40;
						double x = bounds.getWidth() - widthOfForm;
						double y = 0;
						for(ActComCompany aActComCompany: listOfComCompanies){
							listOfWindows.add(new CreateICrashComCompany(aActComCompany, x, y));
							y += heightOfForm;
							if (y + heightOfForm > bounds.getHeight()){
								y = 0;
								x -= widthOfForm;
								if (x < 0)
									x = bounds.getWidth() - widthOfForm;
							}
						}
						//Activator window placement
						percent = 0.5;
						listOfWindows.add(new CreateActivatorGUI(systemStatecontroller.getActActivator(), (bounds.getMinX()), (bounds.getMinY() + rangeY * percent)));
						percent += 0.25;
						listOfWindows.add(new CreateMonitor((bounds.getMinX()), (bounds.getMinY() + rangeY * percent)));
						bttnCreateSystemAndEnvir.setDisable(true);
						txtfldNumberOfComComp.setDisable(true);
						//Minimises the window
						((Stage)window).setIconified(true);
					}
					else
						throw new Exception("Unable to create system and environment, please try again");
				}
			} catch (Exception e) {
				showExceptionErrorMessage(e);
			}
    }
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.AbstractGUIController#closeForm()
	 */
	@Override
	public void closeForm() {
		for(CreatedWindows cw : listOfWindows)
			cw.closeWindow();
		systemStatecontroller.closeServerConnection();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Nothing needed here
	}

	@Override
	public PtBoolean setActor(JIntIsActor actor) {
		return new PtBoolean(true);
	}
}

