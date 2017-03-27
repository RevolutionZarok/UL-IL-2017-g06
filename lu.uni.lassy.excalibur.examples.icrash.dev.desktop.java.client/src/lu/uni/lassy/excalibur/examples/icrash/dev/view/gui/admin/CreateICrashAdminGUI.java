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
package lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.admin;
import java.net.URL;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActAdministrator;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.CreatedWindows;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * The Class CreateICrashAdminGUI that deals with starting up the Admin iCrashSystem.
 */
public class CreateICrashAdminGUI implements CreatedWindows {
	
	/**
	 * Generates and shows the window on the screen
	 *
	 * @param aActAdmin the actor to be associated with this window
	 * @param x the x position of where to put the window on the screen
	 * @param y the y position of where to put the window on the screen
	 */
	public CreateICrashAdminGUI(ActAdministrator aActAdmin, double x, double y){
		start(aActAdmin, x, y);
	}
	
	/** The stage that will host the form. */
	private Stage stage;
	
	/**
	 * Generates and shows the window on the screen
	 *
	 * @param aActAdmin the actor to be associated with this window
	 * @param xPosition the x position of where to put the window on the screen
	 * @param yPosition the y position of where to put the window on the screen
	 */
	private void start(ActAdministrator aActAdmin, double xPosition, double yPosition){
		try {
			URL url = this.getClass().getResource("ICrashAdminGUI.fxml");
			FXMLLoader loader = new FXMLLoader(url);
			Parent root = (Parent)loader.load();
            stage = new Stage();
            stage.setTitle("iCrash Admin");
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent e) {
                   Platform.exit();
                   System.exit(0);
                }
             });
            stage.setX(xPosition);
            stage.setY(yPosition);
            stage.show();
            ((ICrashAdminGUIController)loader.getController()).setWindow(stage);
            ((ICrashAdminGUIController)loader.getController()).setActor(aActAdmin);
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					((ICrashAdminGUIController)loader.getController()).closeForm();
				}
			});
            Log4JUtils.getInstance().getLogger().info("Java version: " + com.sun.javafx.runtime.VersionInfo.getRuntimeVersion());
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
