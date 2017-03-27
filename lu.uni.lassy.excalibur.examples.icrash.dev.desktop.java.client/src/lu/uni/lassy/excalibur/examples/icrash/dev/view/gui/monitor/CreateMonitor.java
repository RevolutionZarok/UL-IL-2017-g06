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

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.CreatedWindows;

/**
 * The Class MainMonitorGUI for starting up the Monitor window.
 * This is a test function to see what data is stored in the server at the moment.
 * It should mirror the database, but will point to errors if there is an inconsistency
 */
public class CreateMonitor implements CreatedWindows{

	/**
	 * Generates and shows the window on the screen
	 *
	 * @param xPosition the x position of where to put the window on the screen
	 * @param yPosition the y position of where to put the window on the screen
	 */
	public CreateMonitor(double xPosition, double yPosition){
		start(xPosition, yPosition);
	}
	/** The stage that will host the form. */
	private Stage stage;
	
	
	/**
	 * Generates and shows the window on the screen
	 *
	 * @param xPosition the x position of where to put the window on the screen
	 * @param yPosition the y position of where to put the window on the screen
	 */
	public void start(double xPosition, double yPosition){
		try {
			URL url = this.getClass().getResource("MonitorGUI.fxml");
			FXMLLoader loader = new FXMLLoader(url);
			Parent root = (Parent)loader.load();
            stage = new Stage();
            stage.setTitle("System Monitor");
            stage.setScene(new Scene(root));
            stage.setX(xPosition);
            stage.setY(yPosition);
            stage.show();
            ((MonitorGUIController)loader.getController()).setWindow(stage);
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					((MonitorGUIController)loader.getController()).closeForm();
				}
			});
		} catch(Exception e) {
			Log4JUtils.getInstance().getLogger().error(e);
		}
	}


	/* (non-Javadoc)
	 * @see lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.CreatedWindows#closeWindow()
	 */
	@Override
	public void closeWindow() {
		if (stage != null)
			stage.close();
	}
}
