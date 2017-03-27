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
package lu.uni.lassy.excalibur.examples.icrash.dev.model;

/**
 * A class for storing messages received from the server for the actors
 */
public class Message {
	
	/**
	 * The enumeration of the different message types.
	 */
	public enum MessageType{
		
		/** The ie message. */
		ieMessage,
		
		/** The ie send an alert. */
		ieSendAnAlert,
		
		/** The ie send a crisis. */
		ieSendACrisis,
		
		/** The ie coordinator added. */
		ieCoordinatorAdded,
		
		/** The ie coordinator deleted. */
		ieCoordinatorDeleted,
		
		/** The ie coordinator updated. */
		ieCoordinatorUpdated,
		
		/** The ie sms send. */
		ieSmsSend
	}
	
	/**
	 * Creates a new message that has a type and a message string attached to it
	 *
	 * @param type The type of the message
	 * @param message The string of the message attached
	 */
	public Message(MessageType type, String message){
		this._type = type;
		this._message = message;
	}
	
	/**
	 * Creates a new message that only has a type
	 * The message part is initated with an empty string value
	 *
	 * @param type The type of the message
	 */
	public Message(MessageType type){
		this(type, "");
	}
	
	/** The type of the message. */
	private MessageType _type;
	
	/** The message inside the class. */
	private String _message;
	
	/**
	 * Gets the message type.
	 *
	 * @return the type of the message
	 */
	public MessageType getMessageType(){
		return _type;
	}
	
	/**
	 * Gets the message inside this class.
	 *
	 * @return the message inside the class
	 */
	public String getMessage(){
		return _message;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	 
	@Override
	public boolean equals(Object obj){
		if (!(obj instanceof Message))
			return false;
		Message compare = (Message)obj;
		if (!compare._type.equals(this._type))
			return false;
		if (!compare._message.equals(this._message))
			return false;
		return true;
	}
	
}
