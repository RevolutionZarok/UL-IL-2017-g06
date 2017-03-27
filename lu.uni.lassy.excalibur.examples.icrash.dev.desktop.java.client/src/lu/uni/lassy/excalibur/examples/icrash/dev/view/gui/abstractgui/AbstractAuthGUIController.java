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

/**
 * The Class AbstractAuthGUIController.
 * Used to specify a GUI controller should allow a user to logon and logoff
 */
public abstract class AbstractAuthGUIController extends AbstractGUIController implements HasTables {
	
	/**
	 * A method that allows the user to logon to the sever. Will often call a function from inside the user's specific controller class.
	 */
	public abstract void logon();
	
	/**
	 * A method that allows the user to logoff the sever. Will often call a function from inside the user's specific controller class.
	 */
	public abstract void logoff();
	/**
	 * Runs the function to show the logon pane or hide it depending on the boolean passed.
	 * If true, will hide the logon pane otherwise will show the logon pane
	 *
	 * @param loggedOn Is the user logged onto the system?
	 */
	protected abstract void logonShowPanes(boolean loggedOn);
	
}
