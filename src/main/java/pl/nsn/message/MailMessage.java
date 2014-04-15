/*
 * Copyright (c) 2013 Nokia Siemens Networks. All rights reserved.
 */
package pl.nsn.message;

/**
 * Class representing mail message
 * 
 * @author piotr.pawlaczek
 * 
 */
public class MailMessage extends Message {
	public MailMessage() {
		this.messageType = MessageType.MAIL;
	}

	public MailMessage(String recipient, String text) {
		super(recipient, text);
		this.messageType = MessageType.MAIL;
	}

	@Override
	public MessageType getType() {
		return this.messageType;
	}

	@Override
	public String send() {
		logger.info("Mail message sent.");
		return "Mail message sent.";
	}

}
