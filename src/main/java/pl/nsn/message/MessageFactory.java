/*
 * Copyright (c) 2013 Nokia Siemens Networks. All rights reserved.
 */
package pl.nsn.message;

/**
 * Factory class for different messages types
 * 
 * @author piotr.pawlaczek
 * 
 */
public class MessageFactory {
	/**
	 * Create message for a given type
	 * 
	 * @param type
	 *            message type
	 * @return message object
	 */
	public static Message createMessage(MessageType type) {
		switch (type) {
			case MAIL:
				return new MailMessage();
			case SMS:
				return new SMSMessage();
			default:
				throw new IllegalArgumentException("No message type for a given value: " + type);
		}
	}

}
