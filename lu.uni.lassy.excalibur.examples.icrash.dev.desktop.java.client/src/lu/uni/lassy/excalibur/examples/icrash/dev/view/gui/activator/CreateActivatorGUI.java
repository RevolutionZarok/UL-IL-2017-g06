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

import java.net.URL;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActActivator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.CreatedWindows;

/**
 * The Class MainActivatorGUI that starts the activator window.
 * The activator window is used for forcing a change in date and time
 * In a normal system, this would be automatic
 */
public class CreateActivatorGUI implements CreatedWindows{
	
	/**
	 * Creates the activator window in the position defined.
	 *
	 * @param aActActivator The actor activator that is associated with this window
	 * @param x the x position of where to put the window on the screen
	 * @param y the y position of where to put the window on the screen
	 */
	public CreateActivatorGUI(ActActivator aActActivator, double x, double y){
		start(aActActivator, x, y);
	}
	
	/** The window that is created. */
	private Stage stage;
	
	/**
	 * Creates the activator window in the position defined.
	 *
	 * @param aActActivator The actor activator that is associated with this window
	 * @param xPosition the x position of where to put the window on the screen
	 * @param yPosition the y position of where to put the window on the screen
	 */
	private void start(ActActivator aActActivator, double xPosition, double yPosition){
		try {
			URL url = this.getClass().getResource("ActivatorGUI.fxml");
			FXMLLoader loader = new FXMLLoader(url);
			Parent root = (Parent)loader.load();
            stage = new Stage();
            stage.setTitle("iCrash Activator");
            stage.setScene(new Scene(root));
            stage.setX(xPosition);
            stage.setY(yPosition);
            stage.show();
            ((ActivatorGUIController)loader.getController()).setActor(aActActivator);
            ((ActivatorGUIController)loader.getController()).setWindow(stage);
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					((ActivatorGUIController)loader.getController()).closeForm();
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
