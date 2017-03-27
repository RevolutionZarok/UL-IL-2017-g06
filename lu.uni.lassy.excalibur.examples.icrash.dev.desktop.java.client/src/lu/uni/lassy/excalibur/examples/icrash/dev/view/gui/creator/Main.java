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

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lu.uni.lassy.excalibur.examples.icrash.dev.java.utils.Log4JUtils;

/**
 * The Class Main for launching the system. Hold the main method.
 */
public class Main extends Application{

	/**
	 * The main method to start the client system up.
	 *
	 * @param args the arguments passed by a user, we ignore these
	 */
	public static void main(String[] args) {
		Application.launch(Main.class, (java.lang.String[])null);
	}

	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("CreatorGUI.fxml"));
			Parent root = (Parent)loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("iCrash MSR Creator");
            primaryStage.show();
            ((CreatorGUIController)loader.getController()).setWindow(primaryStage);
            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					((CreatorGUIController)loader.getController()).closeForm();
					Platform.exit();
					System.exit(0);
				}
			});
            Log4JUtils.getInstance().getLogger().info("Java version: " + com.sun.javafx.runtime.VersionInfo.getRuntimeVersion());
		} catch(Exception e) {
			Log4JUtils.getInstance().getLogger().error(e);
		}
	}

}
