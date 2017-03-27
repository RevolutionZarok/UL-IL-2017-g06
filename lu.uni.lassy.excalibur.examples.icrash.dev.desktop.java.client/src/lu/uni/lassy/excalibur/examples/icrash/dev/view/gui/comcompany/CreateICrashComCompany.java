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
package lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.comcompany;

import java.net.URL;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.environment.actors.ActComCompany;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;
import lu.uni.lassy.excalibur.examples.icrash.dev.view.gui.abstractgui.CreatedWindows;

/**
 * The Class MainComCompanyGUI that starts up the window used to test sending of alerts by communication companies.
 * Normally in a real life system they would use an API, but this is a way to test the scenario
 */
public class CreateICrashComCompany implements CreatedWindows {
	
	/**
	 * Generates and shows the window on the screen
	 *
	 * @param aActComCompany the actor to be associated with this window
	 * @param xPosition the x position of where to put the window on the screen
	 * @param yPosition the y position of where to put the window on the screen
	 */
	public CreateICrashComCompany(ActComCompany aActComCompany, double xPosition, double yPosition){
		start(aActComCompany, xPosition, yPosition);
	}
	
	/** The stage that will host the form. */
	private Stage stage;
	
	/**
	 * Generates and shows the window on the screen
	 *
	 * @param aActComCompany the actor to be associated with this window
	 * @param xPosition the x position of where to put the window on the screen
	 * @param yPosition the y position of where to put the window on the screen
	 */
	public void start(ActComCompany aActComCompany, double xPosition, double yPosition){
		try {
			URL url = this.getClass().getResource("CommunicationCompanyGUI.fxml");
			FXMLLoader loader = new FXMLLoader(url);
			Parent root = (Parent)loader.load();
            stage = new Stage();
            stage.setTitle("iCrash Communication Company - " + aActComCompany.getName());
            stage.setScene(new Scene(root));
            stage.setY(yPosition);
            stage.setX(xPosition);
            stage.show();
            ((CommunicationCompanyGUIController)loader.getController()).setWindow(stage);
            ((CommunicationCompanyGUIController)loader.getController()).setActor(aActComCompany);
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					((CommunicationCompanyGUIController)loader.getController()).closeForm();
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
