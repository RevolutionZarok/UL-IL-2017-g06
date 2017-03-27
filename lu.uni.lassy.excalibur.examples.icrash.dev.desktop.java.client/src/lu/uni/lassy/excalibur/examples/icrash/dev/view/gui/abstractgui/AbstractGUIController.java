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
package lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Window;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;
import javafx.util.StringConverter;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.design.JIntIsActor;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtAdministrator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtAlert;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtCrisis;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtHuman;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.CtState;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtAlertStatus;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.EtCrisisStatus;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtDate;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.DtTime;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.model.Message;
import lu.uni.lassy.excalibur.examples.icrash.dev.model.Message.MessageType;
import lu.uni.lassy.excalibur.examples.icrash.dev.controller.exceptions.IncorrectFormatException;

/**
 * The Class AbstractGUIController, this has some generic functions used across several GUI windows.
 */
public abstract class AbstractGUIController implements Initializable {
	
	/**
	 * The enumeration of the SliderType.
	 */
	public enum SliderType{
		
		/**  The slider will be used to control the number of hour to be sent to the method. */
		Hour,
		
		/**  The slider will be used to control the number of minute to be sent to the method. */
		Minute,
		
		/**  The slider will be used to control the number of seconds to be sent to the method. */
		Second
	}
	
	/**  The system window that this controller deals with, this helps making sure dialog boxes appear in front on the window and not center screen. */
	protected Window window;
	
	/**
	 * Sets the window details, which allows dialogs to appear in the correct place when created
	 * @param The window that this form belongs to
	 */
	public void setWindow(Window window){
		this.window = window;
	}
	
	/**
	 * The actor associated with this contoller
	 */
	protected JIntIsActor actor;
	
	/**
	 * Sets the actor that is associated with this controller. This actor is used to access the server and confirm they are logged in with the correct credentials
	 * @param actor The actor that is associated with this controller
	 * @return The success of setting the actor. If the incorrect actor type was passed, it will error
	 */
	public abstract PtBoolean setActor(JIntIsActor actor);
	
	/**
	 * Generates a slider that will be used to allow the user to select a date and time.
	 *
	 * @param width The width the slider will be
	 * @param type The type of slider, this could be hour, minute or second
	 * @param txtFldToUpdate The text field that will update the slider or be updated by the slider changing
	 * @param time The current system time, these are the default values to set the slider when the user sees the window for the first time
	 * @return The finished slider. An event will also be attached to the textfield provided
	 */
	public Slider getSlider(int width, SliderType type, TextField txtFldToUpdate, DtTime time){
		Slider slider = new Slider();
		slider.setMinWidth(width);
		txtFldToUpdate.setMinWidth(50);
		txtFldToUpdate.setMaxWidth(50);
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);
		slider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				String value = String.format("%02d", newValue.intValue());
				if (value != txtFldToUpdate.getText())
					txtFldToUpdate.setText(value);
			}
		});
		switch(type){
		case Hour:
			slider.setMin(0);
			slider.setMax(23);
			slider.setMajorTickUnit(2);
			slider.setMinorTickCount(1);
			slider.setBlockIncrement(1);
			slider.setTooltip(new Tooltip("Hour"));
			slider.setValue(time.hour.value.getValue());
			break;
		case Minute:
		case Second:
			slider.setMin(0);
			slider.setMax(59);
			slider.setMajorTickUnit(10);
			slider.setMinorTickCount(5);
			slider.setBlockIncrement(5);
			slider.setTooltip(new Tooltip(type == SliderType.Minute ? "Minute" : "Second"));
			slider.setValue(type == SliderType.Minute ? time.minute.value.getValue(): time.second.value.getValue());
			break;
		}
		
		ChangeListener<String> textChanger = new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				txtFldToUpdate.textProperty().removeListener(this);
				if (newValue.length() > 0){
					try{
						int number =Integer.parseInt(newValue);
						if (number > slider.getMax() || number < slider.getMin())
							txtFldToUpdate.setText(oldValue);
						else{
							if (slider.getValue() != number)
								slider.setValue(number);
						}
					}
					catch(NumberFormatException e){
						txtFldToUpdate.setText(oldValue);
					}
				}
				else
					txtFldToUpdate.setText(oldValue);
				txtFldToUpdate.textProperty().addListener(this);
			}
		};
		if (txtFldToUpdate.getText().isEmpty())
			txtFldToUpdate.setText("00");
		txtFldToUpdate.textProperty().addListener(textChanger);
		return slider;
	}
	
	/**
	 * Creates a date picker that keeps the same formatting between forms.
	 *
	 * @param defaultDate The default date to set the date picker to
	 * @param width The width to set for the date picker
	 * @return Returns the created datepicker
	 */
	public DatePicker getDatePicker(DtDate defaultDate, int width){
		DatePicker dtpckr = new DatePicker();
		dtpckr.setMaxWidth(width);
		dtpckr.setMinWidth(width);
		dtpckr.setConverter(new StringConverter<LocalDate>() {
			private DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("yyyy/MM/dd");
			@Override
			public String toString(LocalDate localDate) {
				if(localDate==null)
		            return "";
		        return dateTimeFormatter.format(localDate);
			}
			@Override
			public LocalDate fromString(String dateString) {
				if(dateString==null || dateString.trim().isEmpty())
		            return null;
				try{
					return LocalDate.parse(dateString,dateTimeFormatter);
				}
				catch(Exception e){
					//Bad date value entered
					return null;
				}
			}
		});
		dtpckr.setPromptText("yyyy/MM/dd");
		dtpckr.setValue(LocalDate.parse(defaultDate.toString(), DateTimeFormatter.ofPattern("yyyy/MM/dd")));
		//This deals with the bug located here where the datepicker value is not updated on focus lost
		//https://bugs.openjdk.java.net/browse/JDK-8092295?page=com.atlassian.jira.plugin.system.issuetabpanels:all-tabpanel
		dtpckr.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue){
					dtpckr.setValue(dtpckr.getConverter().fromString(dtpckr.getEditor().getText()));
				}
			}
		});
		return dtpckr;
	}

	/**
	 * Shows a message box of the type information, this will show with a blue circle icon with a white i.
	 *
	 * @param title The title to show in a message box title bar
	 * @param message The message to show inside the message box
	 */
	public void showOKMessage(String title, String message){
		showMessage(title, message, AlertType.INFORMATION);
	}
	
	/**
	 * A generic method to show message boxes, this makes sure all message boxes behave the same way.
	 *
	 * @param title The title to show in a message box title bar
	 * @param message The message to show inside the message box
	 * @param type The type of information box to show, an example would be information
	 */
	private void showMessage(String title, String message, AlertType type){
		Alert alert = new Alert(type);
		alert.setHeaderText(title);
		alert.setContentText(message);
		alert.initOwner(window);
		alert.initModality(Modality.WINDOW_MODAL);
		alert.showAndWait();
	}
	
	/**
	 * Show a dialog box indicating there was an issue, this is of type warning. This shows a yellow warning triangle with a white exclamation mark
	 *
	 * @param title The title to display on the message box
	 * @param message The message to display inside the message box
	 */
	public void showWarningMessage(String title, String message){
		showMessage(title, message, AlertType.WARNING);
	}
	
	/**
	 * Show the default warning if not all information has been entered by the user.
	 */
	public void showWarningNoDataEntered(){
		showWarningIncorrectData("Not all information was entered, please try again");
	}
	
	/**
	 * Show the default warning if incorrect information has been entered, it requires an IncorrectFormatException class to be passed.
	 *
	 * @param ex The exception to get the message details from
	 */
	public void showWarningIncorrectInformationEntered(IncorrectFormatException ex){
		showWarningIncorrectData(ex.getMessage());
	}
	
	/**
	 * Show the default warning if a user has selected more than one item, when they should select only one item.
	 */
	public void showWarningSelectOnlyOneItem(){
		showWarningIncorrectData("Please select one item and try again");		
	}
	
	/**
	 * Shows a warning dialog box with the title Incorrect data and shows the message provided.
	 *
	 * @param message The message to show inside the dialog box
	 */
	public void showWarningIncorrectData(String message){
		showWarningMessage("Incorrect data", message);
	}
	
	/**
	 * Show a dialog box of type error. This shows a red square icon with a white x in it
	 *
	 * @param title The title to display on the message box
	 * @param message The message to display inside the message box
	 */
	public void showErrorMessage(String title, String message){
		showMessage(title, message, AlertType.ERROR);
	}
	
	/**
	 * Show a dialog box of type error, whilst using exceptions as the message type.
	 *
	 * @param e the exception of which to get the message from
	 */
	public void showExceptionErrorMessage(Exception e){
		showErrorMessage("Error", "An error occurred, the error message was:\n" + e.getMessage());
	}
	
	/**
	 * Shows the default server offline message to the user.
	 *
	 * @param e The exception that was thrown, we get the message from inside it and display it to the user
	 */
	public void showServerOffLineMessage(Exception e){
		showErrorMessage("Server offline", "The server has gone offline, you have been logged off.\nThe error message was:\n" + e.getMessage());
	}
	
	/**
	 * Shows the default warning message that the user cancelled the action.
	 */
	public void showUserCancelledActionPopup() {
		showWarningMessage("Canceled by user", "The operation was cancelled or not all information was entered");
	}

	/**
	 * Sets up the coord tableviews with the correct columns.
	 *
	 * @param tblvw The tableview to add the columns to
	 * @param showPassword If true, we show the password in the table, otherwise we do not
	 */
	public void setUpCoordTables(TableView<CtCoordinator> tblvw, boolean showPassword){
		TableColumn<CtCoordinator, String> idCol = new TableColumn<CtCoordinator, String>("ID");
		TableColumn<CtCoordinator, String> nameCol = new TableColumn<CtCoordinator, String>("Username");
		TableColumn<CtCoordinator, String> passwordCol = new TableColumn<CtCoordinator, String>("Password");
		idCol.setCellValueFactory(new Callback<CellDataFeatures<CtCoordinator, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<CtCoordinator, String> coord) {
				return new ReadOnlyObjectWrapper<String>(coord.getValue().id.value.getValue());
			}
		});
		nameCol.setCellValueFactory(new Callback<CellDataFeatures<CtCoordinator, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<CtCoordinator, String> coord) {
				return new ReadOnlyObjectWrapper<String>(coord.getValue().login.value.getValue());
			}
		});
		passwordCol.setCellValueFactory(new Callback<CellDataFeatures<CtCoordinator, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<CtCoordinator, String> coord) {
				return new ReadOnlyObjectWrapper<String>(coord.getValue().pwd.value.getValue());
			}
		});
		tblvw.getColumns().add(idCol);
		tblvw.getColumns().add(nameCol);
		if (showPassword)
			tblvw.getColumns().add(passwordCol);
		setColumnsSameWidth(tblvw);
	}
	
	/**
	 * Sets up the admin tableviews with the correct columns.
	 *
	 * @param tblvw The tableview to add the columns to
	 * @param showPassword If true, we show the password in the table, otherwise we do not
	 */
	public void setUpAdminTables(TableView<CtAdministrator> tblvw, boolean showPassword){
		TableColumn<CtAdministrator, String> nameCol = new TableColumn<CtAdministrator, String>("Username");
		TableColumn<CtAdministrator, String> passwordCol = new TableColumn<CtAdministrator, String>("Password");
		nameCol.setCellValueFactory(new Callback<CellDataFeatures<CtAdministrator, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<CtAdministrator, String> admin) {
				return new ReadOnlyObjectWrapper<String>(admin.getValue().login.value.getValue());
			}
		});
		passwordCol.setCellValueFactory(new Callback<CellDataFeatures<CtAdministrator, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<CtAdministrator, String> admin) {
				return new ReadOnlyObjectWrapper<String>(admin.getValue().pwd.value.getValue());
			}
		});
		tblvw.getColumns().add(nameCol);
		if (showPassword)
			tblvw.getColumns().add(passwordCol);
		setColumnsSameWidth(tblvw);
	}
	
	/**
	 * Sets up the alert tableviews with the correct columns.
	 *
	 * @param tblvw The tableview to add the columns to
	 */
	public void setUpAlertTables(TableView<CtAlert> tblvw){
		TableColumn<CtAlert, String> idCol = new TableColumn<CtAlert, String>("ID");
		TableColumn<CtAlert, String> dateCol = new TableColumn<CtAlert, String>("Date");
		TableColumn<CtAlert, String> timeCol = new TableColumn<CtAlert, String>("Time");
		TableColumn<CtAlert, Double> longitudeCol = new TableColumn<CtAlert, Double>("Longitude");
		TableColumn<CtAlert, Double> latitudeCol = new TableColumn<CtAlert, Double>("Latitude");
		TableColumn<CtAlert, String> commentCol = new TableColumn<CtAlert, String>("Comment");
		TableColumn<CtAlert, String> statusCol = new TableColumn<CtAlert, String>("Status");
		idCol.setCellValueFactory(new Callback<CellDataFeatures<CtAlert, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<CtAlert, String> alert) {
				return new ReadOnlyObjectWrapper<String>(alert.getValue().id.value.getValue());
			}
		});
		dateCol.setCellValueFactory(new Callback<CellDataFeatures<CtAlert, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<CtAlert, String> alert) {
				return new ReadOnlyObjectWrapper<String>(alert.getValue().instant.date.toString());
			}
		});
		timeCol.setCellValueFactory(new Callback<CellDataFeatures<CtAlert, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<CtAlert, String> alert) {
				return new ReadOnlyObjectWrapper<String>(alert.getValue().instant.time.toString());
			}
		});
		longitudeCol.setCellValueFactory(new Callback<CellDataFeatures<CtAlert, Double>, ObservableValue<Double>>() {
			public ObservableValue<Double> call(CellDataFeatures<CtAlert, Double> alert) {
				return new ReadOnlyObjectWrapper<Double>(alert.getValue().location.longitude.value.getValue());
			}
		});
		latitudeCol.setCellValueFactory(new Callback<CellDataFeatures<CtAlert, Double>, ObservableValue<Double>>() {
			public ObservableValue<Double> call(CellDataFeatures<CtAlert, Double> alert) {
				return new ReadOnlyObjectWrapper<Double>(alert.getValue().location.latitude.value.getValue());
			}
		});
		commentCol.setCellValueFactory(new Callback<CellDataFeatures<CtAlert, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<CtAlert, String> alert) {
				return new ReadOnlyObjectWrapper<String>(alert.getValue().comment.value.getValue());
			}
		});
		statusCol.setCellValueFactory(new Callback<CellDataFeatures<CtAlert, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<CtAlert, String> alert) {
				return new ReadOnlyObjectWrapper<String>(alert.getValue().status.name());
			}
		});		
		tblvw.getColumns().add(idCol);
		tblvw.getColumns().add(dateCol);
		tblvw.getColumns().add(timeCol);
		tblvw.getColumns().add(longitudeCol);
		tblvw.getColumns().add(latitudeCol);
		tblvw.getColumns().add(commentCol);
		tblvw.getColumns().add(statusCol);
		setColumnsSameWidth(tblvw);
	}
	
	/**
	 * * Sets up the crisis tableviews with the correct columns.
	 *
	 * @param tblvw The tableview to add the columns to
	 */
	public void setUpCrisesTables(TableView<CtCrisis>tblvw){
		TableColumn<CtCrisis, String> idCol = new TableColumn<CtCrisis, String>("ID");
		TableColumn<CtCrisis, String> dateCol = new TableColumn<CtCrisis, String>("Date");
		TableColumn<CtCrisis, String> timeCol = new TableColumn<CtCrisis, String>("Time");
		TableColumn<CtCrisis, String> typeCol = new TableColumn<CtCrisis, String>("Type");
		TableColumn<CtCrisis, Double> longitudeCol = new TableColumn<CtCrisis, Double>("Longitude");
		TableColumn<CtCrisis, Double> latitudeCol = new TableColumn<CtCrisis, Double>("Latitude");
		TableColumn<CtCrisis, String> commentCol = new TableColumn<CtCrisis, String>("Comment");
		TableColumn<CtCrisis, String> statusCol = new TableColumn<CtCrisis, String>("Status");
		idCol.setCellValueFactory(new Callback<CellDataFeatures<CtCrisis, String>, ObservableValue<String>>() {
			
			public ObservableValue<String> call(CellDataFeatures<CtCrisis, String> crisis) {
				return new ReadOnlyObjectWrapper<String>(crisis.getValue().id.value.getValue());
			}
		});
		dateCol.setCellValueFactory(new Callback<CellDataFeatures<CtCrisis, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<CtCrisis, String> crisis) {
				return new ReadOnlyObjectWrapper<String>(crisis.getValue().instant.date.toString());
			}
		});
		timeCol.setCellValueFactory(new Callback<CellDataFeatures<CtCrisis, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<CtCrisis, String> crisis) {
				return new ReadOnlyObjectWrapper<String>(crisis.getValue().instant.time.toString());
			}
		});
		longitudeCol.setCellValueFactory(new Callback<CellDataFeatures<CtCrisis, Double>, ObservableValue<Double>>() {
			public ObservableValue<Double> call(CellDataFeatures<CtCrisis, Double> crisis) {
				return new ReadOnlyObjectWrapper<Double>(crisis.getValue().location.longitude.value.getValue());
			}
		});
		latitudeCol.setCellValueFactory(new Callback<CellDataFeatures<CtCrisis, Double>, ObservableValue<Double>>() {
			public ObservableValue<Double> call(CellDataFeatures<CtCrisis, Double> crisis) {
				return new ReadOnlyObjectWrapper<Double>(crisis.getValue().location.latitude.value.getValue());
			}
		});
		commentCol.setCellValueFactory(new Callback<CellDataFeatures<CtCrisis, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<CtCrisis, String> crisis) {
				return new ReadOnlyObjectWrapper<String>(crisis.getValue().comment.value.getValue());
			}
		});
		statusCol.setCellValueFactory(new Callback<CellDataFeatures<CtCrisis, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<CtCrisis, String> crisis) {
				return new ReadOnlyObjectWrapper<String>(crisis.getValue().status.name());
			}
		});
		typeCol.setCellValueFactory(new Callback<CellDataFeatures<CtCrisis, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<CtCrisis, String> crisis) {
				return new ReadOnlyObjectWrapper<String>(crisis.getValue().type.name());
			}
		});
		tblvw.getColumns().add(idCol);
		tblvw.getColumns().add(dateCol);
		tblvw.getColumns().add(timeCol);
		tblvw.getColumns().add(typeCol);
		tblvw.getColumns().add(longitudeCol);
		tblvw.getColumns().add(latitudeCol);
		tblvw.getColumns().add(commentCol);
		tblvw.getColumns().add(statusCol);
		setColumnsSameWidth(tblvw);
	}
	
	/**
	 * Sets up the human tableviews with the correct columns.
	 *
	 * @param tblvw The tableview to add the columns to
	 */
	public void setUpHumansTables(TableView<CtHuman> tblvw){
		TableColumn<CtHuman, String> idCol = new TableColumn<CtHuman, String>("Phone Number");
		TableColumn<CtHuman, String> kindCol = new TableColumn<CtHuman, String>("Type");
		idCol.setCellValueFactory(new Callback<CellDataFeatures<CtHuman, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<CtHuman, String> human) {
				return new ReadOnlyObjectWrapper<String>(human.getValue().id.value.getValue());
			}
		});
		kindCol.setCellValueFactory(new Callback<CellDataFeatures<CtHuman, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<CtHuman, String> human) {
				return new ReadOnlyObjectWrapper<String>(human.getValue().kind.name());
			}
		});
		tblvw.getColumns().add(idCol);
		tblvw.getColumns().add(kindCol);
		setColumnsSameWidth(tblvw);
	}
	
	/**
	 * Sets up the communication company tableviews with the correct columns.
	 *
	 * @param tblvw The tableview to add the columns to
	 */
	public void setUpComCompaniesTables(TableView<String> tblvw){
		TableColumn<String, String> nameCol = new TableColumn<String, String>("Name");
		nameCol.setCellValueFactory(new Callback<CellDataFeatures<String, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<String, String> comComp) {
				return new ReadOnlyObjectWrapper<String>(comComp.getValue());
			}
		});
		tblvw.getColumns().add(nameCol);
		setColumnsSameWidth(tblvw);
	}
	
	/**
	 * Sets up the message (Notification) tableviews with the correct columns.
	 *
	 * @param tblvw The tableview to add the columns to
	 */
	public void setUpMessageTables(TableView<Message> tblvw){
		TableColumn<Message, MessageType> typeCol = new TableColumn<Message, MessageType>("Input event");
		TableColumn<Message, String> messageCol = new TableColumn<Message, String>("Message");
		typeCol.setCellValueFactory(new Callback<CellDataFeatures<Message, MessageType>, ObservableValue<MessageType>>() {
			@Override
			public ObservableValue<MessageType> call(CellDataFeatures<Message, MessageType> param) {
				return new ReadOnlyObjectWrapper<MessageType>(param.getValue().getMessageType());
			}
		});
		messageCol.setCellValueFactory(new Callback<CellDataFeatures<Message, String>, ObservableValue<String>>(){

			@Override
			public ObservableValue<String> call(CellDataFeatures<Message, String> param) {
				return  new ReadOnlyObjectWrapper<String>(param.getValue().getMessage());
			}
			
		});
		tblvw.getColumns().add(typeCol);
		tblvw.getColumns().add(messageCol);
		typeCol.setPrefWidth(150);
		messageCol.setPrefWidth(500);		
	}
	
	/**
	 * Sets a table up to have columns to show the current system state, this includes things like server date and time, last reminder date and time, and so on.
	 *
	 * @param tblvw A table view of type CtState to show the system state.
	 */
	public void setUpStateTables(TableView<CtState> tblvw){
		TableColumn<CtState, String> dateCol = new TableColumn<CtState, String>("Date");
		TableColumn<CtState, String> timeCol = new TableColumn<CtState, String>("Time");
		TableColumn<CtState, String> crisisReminderCol = new TableColumn<CtState, String>("Crisis Reminder");
		TableColumn<CtState, String> maxCrisisReminderCol = new TableColumn<CtState, String>("Max Crisis Reminder");
		TableColumn<CtState, String> lastReminderCol = new TableColumn<CtState, String>("Last Reminder");
		TableColumn<CtState, Boolean> startedCol = new TableColumn<CtState, Boolean>("Started");
		dateCol.setCellValueFactory(new Callback<CellDataFeatures<CtState, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<CtState, String> param) {
				return new ReadOnlyObjectWrapper<String>(param.getValue().clock.date.toString());
			}
		});
		timeCol.setCellValueFactory(new Callback<CellDataFeatures<CtState, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<CtState, String> param) {
				return new ReadOnlyObjectWrapper<String>(param.getValue().clock.time.toString());
			}
		});
		crisisReminderCol.setCellValueFactory(new Callback<CellDataFeatures<CtState, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<CtState, String> param) {
				return new ReadOnlyObjectWrapper<String>(param.getValue().crisisReminderPeriod.toString());
			}
		});
		maxCrisisReminderCol.setCellValueFactory(new Callback<CellDataFeatures<CtState, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<CtState, String> param) {
				return new ReadOnlyObjectWrapper<String>(param.getValue().maxCrisisReminderPeriod.toString());
			}
		});
		lastReminderCol.setCellValueFactory(new Callback<CellDataFeatures<CtState, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<CtState, String> param) {
				return new ReadOnlyObjectWrapper<String>(param.getValue().vpLastReminder.toString());
			}
		});
		startedCol.setCellValueFactory(new Callback<CellDataFeatures<CtState, Boolean>, ObservableValue<Boolean>>() {
			@Override
			public ObservableValue<Boolean> call(CellDataFeatures<CtState, Boolean> param) {
				return new ReadOnlyObjectWrapper<Boolean>(param.getValue().vpStarted.getValue());
			}
		});
		dateCol.prefWidthProperty().bind(tblvw.widthProperty());
		timeCol.prefWidthProperty().bind(tblvw.widthProperty());
		crisisReminderCol.prefWidthProperty().bind(tblvw.widthProperty());
		maxCrisisReminderCol.prefWidthProperty().bind(tblvw.widthProperty());
		lastReminderCol.prefWidthProperty().bind(tblvw.widthProperty());
		startedCol.prefWidthProperty().bind(tblvw.widthProperty());
		tblvw.getColumns().add(dateCol);
		tblvw.getColumns().add(timeCol);
		tblvw.getColumns().add(crisisReminderCol);
		tblvw.getColumns().add(maxCrisisReminderCol);
		tblvw.getColumns().add(lastReminderCol);
		tblvw.getColumns().add(startedCol);
		setColumnsSameWidth(tblvw);
	}
	
	/**
	 * Sets the columns to have the same width, they will auto increase in size to fill the tableview.
	 *
	 * @param tblvw The tableview to apply the property to
	 */
	private void setColumnsSameWidth(TableView<?> tblvw){
		for(TableColumn<?,?> col : tblvw.getColumns()){
			col.prefWidthProperty().bind(tblvw.widthProperty().divide( tblvw.getColumns().size()));
		}
	}
	
	/**
	 * Adds the provided list of coordinators to the tableview.
	 *
	 * @param tblvw The tableview to add the data to
	 * @param listOfItems the list of items to add to the tableview
	 */
	public void addCoordsToTableView(TableView<CtCoordinator> tblvw, List<CtCoordinator> listOfItems){
		tblvw.getItems().clear();
		tblvw.getItems().addAll(listOfItems);
	}
	
	/**
	 * Adds the provided list of admins to the tableview.
	 *
	 * @param tblvw The tableview to add the data to
	 * @param listOfItems the list of items to add to the tableview
	 */
	public void addAdminsToTableView(TableView<CtAdministrator> tblvw, List<CtAdministrator> listOfItems){
		tblvw.getItems().clear();
		tblvw.getItems().addAll(listOfItems);
	}
	
	/**
	 * Adds the provided list of alerts to the tableview.
	 *
	 * @param tblvw The tableview to add the data to
	 * @param collection the list of items to add to the tableview
	 */
	public void addAlertsToTableView(TableView<CtAlert> tblvw, Collection<? extends CtAlert> collection){
		tblvw.getItems().clear();
		tblvw.getItems().addAll(collection);
	}
	
	/**
	 * Adds alerts to a table of a specific status.
	 *
	 * @param tblvw The table to add the alerts to
	 * @param collection The list of alerts to add to the table
	 * @param aEtAlertStatus The status to filter the alert by
	 */
	public void addAlertsToTableView(TableView<CtAlert> tblvw, Collection<? extends CtAlert> collection, EtAlertStatus aEtAlertStatus){
		tblvw.getItems().clear();
		tblvw.getItems().addAll(collection.stream().filter(t-> t.status.equals(aEtAlertStatus)).collect(Collectors.toList()));
	}
	
	/**
	 * Adds the provided list of crises to the tableview.
	 *
	 * @param tblvw The tableview to add the data to
	 * @param collection the list of items to add to the tableview
	 */
	public void addCrisesToTableView(TableView<CtCrisis> tblvw, Collection<? extends CtCrisis> collection){
		tblvw.getItems().clear();
		tblvw.getItems().addAll(collection);
	}
	
	/**
	 * Adds crises to a table of a specific status.
	 *
	 * @param tblvw The table to add the alerts to
	 * @param collection The list of crises to add to the table
	 * @param aEtCrisisStatus The status to filter the crisis by
	 */
	public void addCrisesToTableView(TableView<CtCrisis> tblvw, Collection<? extends CtCrisis> collection, EtCrisisStatus aEtCrisisStatus){
		tblvw.getItems().clear();
		tblvw.getItems().addAll(collection.stream().filter(t-> t.status.equals(aEtCrisisStatus)).collect(Collectors.toList()));
	}
	
	/**
	 * Adds the provided list of humans to the tableview.
	 *
	 * @param tblvw The tableview to add the data to
	 * @param listOfItems the list of items to add to the tableview
	 */
	public void addHumansToTableView(TableView<CtHuman> tblvw, ArrayList<CtHuman> listOfItems){
		tblvw.getItems().clear();
		tblvw.getItems().addAll(listOfItems);
	}
	
	/**
	 * Adds the provided list of communication company to the tableview.
	 *
	 * @param tblvw The tableview to add the data to
	 * @param listOfItems the list of items to add to the tableview
	 */
	public void addComCompaniesToTableView(TableView<String> tblvw, ArrayList<String> listOfItems){
		tblvw.getItems().clear();
		tblvw.getItems().addAll(listOfItems);
	}
	
	/**
	 * Adds the provided list of messages (Notifications) to the tableview.
	 *
	 * @param tblvw The tableview to add the data to
	 * @param observableList the list of items to add to the tableview
	 */
	public void addMessageToTableView(TableView<Message> tblvw, ObservableList<? extends Message> observableList){
		tblvw.getItems().clear();
		tblvw.getItems().addAll(observableList);
		//We want to scroll to the bottom of the list, to show the user the most recent one, but we are currently on a background thread.
		//Using the method Platform.runLater(), we can call methods to be ran at a later date, but on the main thread. This allows us to interact
		//with GUI elements that are on the main thread
		Platform.runLater(() -> tblvw.scrollTo(observableList.size()-1) );
	}
	
	/**
	 * Adds the class type state to table view.
	 *
	 * @param tblvw the tableview to add the CtState to
	 * @param aCtState the class type state of the server's information
	 */
	public void addStateToTableView(TableView<CtState> tblvw, CtState aCtState){
		tblvw.getItems().clear();
		tblvw.getItems().add(aCtState);
	}
	/**
	 * Gets a single object from table view that has been selected by the user.
	 *
	 * @param tblvw The tableview to get the single item from
	 * @return The object retrieved from the tableview, it will be an object so needs casting at the other end
	 */
	public Object getObjectFromTableView(TableView<?> tblvw){
		if(tblvw.getSelectionModel().getSelectedItems().size() != 1){
			showWarningSelectOnlyOneItem();
			return null;
		}
		return tblvw.getSelectionModel().getSelectedItem();
	}
	
	/**
	 * Checks the gridpane and all the controls inside and sees if they have data entered.
	 * Currently checks the following controls:
	 * Textfield
	 * ComboBox
	 * TextArea
	 * DatePicker
	 *
	 * @param grdpn The girdpane and it's controls to check
	 * @return Returns true if all values checked have been filled in
	 */
	public boolean checkIfAllDialogHasBeenFilledIn(GridPane grdpn){
		boolean success = true;
		ObservableList<Node> nodes = grdpn.getChildren();
		for(Node n : nodes){
			if (n instanceof TextField)
				success = (((TextField)n).getText().length() != 0);
			else if (n instanceof ComboBox)
				success = (((ComboBox<?>)n).getValue() != null);
			else if (n instanceof TextArea)
				success = (((TextArea)n).getText().length() != 0);
			else if (n instanceof DatePicker)
				success = (((DatePicker)n).getValue() != null);
			if (!success)
				return success;
		}
		return success;
	}

	/**
	 * This is the method to call upon the window closing, an example of what it does is that it will have methods inside that remove any listeners associated.
	 */
	public abstract void closeForm(); 
}
