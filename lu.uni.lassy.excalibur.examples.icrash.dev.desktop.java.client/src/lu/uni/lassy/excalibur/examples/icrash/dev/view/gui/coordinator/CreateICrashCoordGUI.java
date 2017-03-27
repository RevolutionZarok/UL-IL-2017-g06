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
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.system.types.primary.DtCoordinatorID;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.CreatedWindows;

import java.net.URL;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * The Class MainICrashGUI that allows creation of a coordinator iCrash window.
 */
public class CreateICrashCoordGUI implements CreatedWindows {
	
	/**
	 * Instantiates a new window for the coordinator to use iCrash.
	 *
	 * @param aDtCoordinatorID the ID of the coordinator associated with this window
	 * @param aActCoordinator the actor associated with this window
	 */
	public CreateICrashCoordGUI(DtCoordinatorID aDtCoordinatorID, ActCoordinator aActCoordinator) {
		this._aDtCoordinatorID = aDtCoordinatorID;
		start(aActCoordinator);
	}
	
	/** The Coordinator ID that was used to create this window, it's used to work out which windows to close when a coordinator has been deleted. */
	private DtCoordinatorID _aDtCoordinatorID;
	
	/**
	 * Gets the data type of the coordinator's ID.
	 *
	 * @return the datatype of the coordinator's ID
	 */
	public DtCoordinatorID getDtCoordinatorID(){
		return this._aDtCoordinatorID;
	}
	
	/** The stage that the form will be held in. */
	private Stage stage;
	
	/**
	 * Starts the system and opens the window up (If no exceptions have been thrown).
	 *
	 * @param aActCoordinator the actor associated with this window
	 */
	private void start(ActCoordinator aActCoordinator) {
		try {
			URL url = this.getClass().getResource("ICrashCoordGUI.fxml");
			FXMLLoader loader = new FXMLLoader(url);
			Parent root = (Parent)loader.load();
            stage = new Stage();
            stage.setTitle("iCrash Coordinator - " + aActCoordinator.getLogin().value.getValue());
            stage.setScene(new Scene(root));
            stage.show();
            ((ICrashCoordGUIController)loader.getController()).setActor(aActCoordinator);
            ((ICrashCoordGUIController)loader.getController()).setWindow(stage);
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					((ICrashCoordGUIController)loader.getController()).closeForm();
				}
			});
		} catch(Exception e) {
			Log4JUtils.getInstance().getLogger().error(e);
		}
	}
	
	/**
	 * Allows the other screens to force this window to close. Will be used if the coordinator has been deleted
	 */
	@Override
	public void closeWindow(){
		if (stage != null)
			stage.close();
	}
}
