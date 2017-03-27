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
package lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.activator;

import lu.uni.lassy.excalibur.examples.icrash.dev.controller.SystemStateController;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.ServerNotBoundException;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.ServerOfflineException;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIsActor;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtDateAndTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.ICrashUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.AbstractGUIController;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
/*
 * This is the import section to be replaced by modifications in the ICrash.fxml document from the sample skeleton controller
 */
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
/*
 * This is the end of the import section to be replaced by modifications in the ICrash.fxml document from the sample skeleton controller
 */
/**
 * The Class ActivatorGUIController, that deals with crisis handling solicitation and setting date and time of the server. 
 */
public class ActivatorGUIController extends AbstractGUIController {
	/*
	* This section of controls and methods is to be replaced by modifications in the ICrash.fxml document from the sample skeleton controller
	* When replacing, remember to reassign the correct methods to the button event methods and set the correct types for the tableviews
	*/
	/** The Anchor pane which will hold the controls for updating the system date and time. */
	@FXML
	private AnchorPane anchrpnActivator;
    /*
     * These are other classes accessed by this controller
     */
	/** The system state controller, that is used for changing date and time and solicitation of crisis handling. */
	private SystemStateController systemstateController;
	/*
	 * Other things created for this controller
	 */
		
	/*
	 * Methods used within the GUI
	 */
	
	/**
	 * Creates the pane for setting the date and time of the server.
	 * Also has functions for dealing with the user response and submitting to the server if applicable
	 */
	public void createSetDateAndTimeControls(){
		DtDateAndTime currentDateAndTime;
		try {
			currentDateAndTime = systemstateController.getServerDateTime();
			/*
			 * Creating controls to be used on the form
			 */
			int width = 150;
			DatePicker dtpckr = getDatePicker(currentDateAndTime.date, width);
			Label lblDate = new Label("Date:");
			Label lblTimeHour = new Label("Hour:");
			Label lblTimeMinute = new Label("Minute:");
			Label lblTimeSecond = new Label("Second:");
			TextField txtfldCurrentSetSecond = new TextField();
			TextField txtfldCurrentSetMinute = new TextField();
			TextField txtfldCurrentSetHour = new TextField();
			Slider sldrHourPicker = getSlider(width, SliderType.Hour, txtfldCurrentSetHour, currentDateAndTime.time);
			Slider sldrMinutePicker = getSlider(width, SliderType.Minute, txtfldCurrentSetMinute, currentDateAndTime.time);
			Slider sldrSecondPicker = getSlider(width, SliderType.Second, txtfldCurrentSetSecond, currentDateAndTime.time);
			/*
			 * Adding a grid pane to keep controls in correct place and aligned correctly
			 */
			GridPane grdpn = new GridPane();
			grdpn.add(lblDate, 1, 1);
			grdpn.add(dtpckr, 2, 1, 2,1);
			grdpn.add(lblTimeHour, 1, 2);
			grdpn.add(sldrHourPicker, 2, 2, 2 ,1);
			grdpn.add(txtfldCurrentSetHour, 4, 2);
			grdpn.add(lblTimeMinute, 1, 3);
			grdpn.add(sldrMinutePicker, 2, 3, 2, 1);
			grdpn.add(txtfldCurrentSetMinute, 4, 3);
			grdpn.add(lblTimeSecond, 1, 4);
			grdpn.add(sldrSecondPicker, 2, 4, 2, 1);
			grdpn.add(txtfldCurrentSetSecond, 4, 4);
			/*
			 * Creating buttons for user to press
			 */
			Button bttnOK = new Button("Change date and time");
			bttnOK.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					try {
						if (checkIfAllDialogHasBeenFilledIn(grdpn)){
							if (systemstateController.oeSetClock(ICrashUtils.setDateAndTime(dtpckr.getValue().getYear(), dtpckr.getValue().getMonthValue(), dtpckr.getValue().getDayOfMonth(),
									(int)Math.floor(sldrHourPicker.getValue()), (int)Math.floor(sldrMinutePicker.getValue()), (int)Math.floor(sldrSecondPicker.getValue()))).getValue())
								showOKMessage("Updated", "Date and time was updated successfully");
							else
								showErrorMessage("Error", "There was an error updating the date and time");
						}
						else
							showUserCancelledActionPopup();
					} catch (ServerOfflineException | ServerNotBoundException e) {
						showExceptionErrorMessage(e);
					}
				}
			});
			bttnOK.setDefaultButton(true);
			grdpn.add(bttnOK, 2, 5, 3, 1);
			Button bttnSetNow = new Button("Now");
			bttnSetNow.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					dtpckr.setValue(LocalDate.now());
					LocalTime time = LocalTime.now();
					sldrHourPicker.setValue(time.getHour());
					sldrMinutePicker.setValue(time.getMinute());
					sldrSecondPicker.setValue(time.getSecond());
				}
			});
			grdpn.add(bttnSetNow, 4, 1);
			/*
			 * End of creating controls to be used on the form
			 */
			anchrpnActivator.getChildren().add(grdpn);
			AnchorPane.setTopAnchor(grdpn, 0.0);
			AnchorPane.setRightAnchor(grdpn, 0.0);
			AnchorPane.setLeftAnchor(grdpn, 0.0);
			AnchorPane.setBottomAnchor(grdpn, 0.0);
		} catch (ServerOfflineException | ServerNotBoundException e) {
			showExceptionErrorMessage(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.AbstractGUIController#closeForm()
	 */
	@Override
	public void closeForm() {
		systemstateController.closeServerConnection();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		systemstateController = new SystemStateController();
		createSetDateAndTimeControls();
	}

	@Override
	public PtBoolean setActor(JIntIsActor actor) {
		return new PtBoolean(true);
	}
}
