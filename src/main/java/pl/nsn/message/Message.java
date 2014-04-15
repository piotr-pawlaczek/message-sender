/*
 * Copyright (c) 2013 Nokia Siemens Networks. All rights reserved.
 */
package pl.nsn.message;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


/**
 * Generic class for different messages
 * 
 * @author piotr.pawlaczek
 * 
 */
public abstract class Message {
	private String recipient;
	private String text;
	protected Logger logger = Logger.getLogger(Message.class);
	protected MessageType messageType;

	/**
	 * Default constructor
	 */
	public Message() {
		PropertyConfigurator.configure("D:/log4j.properties");
	}
	
	public Message(String recipient, String text) {
		this.recipient = recipient;
		this.text = text;
	}

	/**
	 * Gets message recipient
	 * 
	 * @return message recipient
	 */
	public String getRecipient() {
		return this.recipient;
	}

	/**
	 * Sets message recipient
	 * 
	 * @param recipient
	 *            message recipient
	 */
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	/**
	 * Gets message text
	 * 
	 * @return message text
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * Sets message text
	 * 
	 * @param text
	 *            message text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Gets message type
	 * 
	 * @return message type
	 */
	public abstract MessageType getType();

	/**
	 * Send message
	 * 
	 * @return info about sent message
	 */
	public abstract String send();
}
