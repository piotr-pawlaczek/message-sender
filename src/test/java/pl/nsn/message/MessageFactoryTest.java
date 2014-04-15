/*
 * Copyright (c) 2013 Nokia Siemens Networks. All rights reserved.
 */
package pl.nsn.message;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


/**
 * @author piotr.pawlaczek
 * 
 */
public class MessageFactoryTest {
	@Test
	public void testCreateMailMessage() {
		Message msg = MessageFactory.createMessage(MessageType.MAIL);
		assertEquals(MessageType.MAIL, msg.getType());
	}

	@Test
	public void testCreateSMSMessage() {
		Message msg = MessageFactory.createMessage(MessageType.SMS);
		assertEquals(MessageType.SMS, msg.getType());
	}
}
