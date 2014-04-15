/*
 * Copyright (c) 2013 Nokia Siemens Networks. All rights reserved.
 */
package pl.nsn.message;

/**
 * Class representing SMS message
 * @author piotr.pawlaczek
 *
 */
public class SMSMessage extends Message {
	
	SMSMessage() {
		this.messageType = MessageType.SMS;
	}
	
	/**
	 * @param string
	 * @param string2
	 */
	public SMSMessage(String recipient, String text) {
		super(recipient, text);
		this.messageType = MessageType.SMS;
	}

	@Override
	public MessageType getType() {
		return this.messageType;
	}

	@Override
	public String send() {
		this.logger.info("SMS message sent.");
		return "SMS message sent.";
	}

}
