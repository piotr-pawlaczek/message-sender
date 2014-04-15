package pl.nsn.message;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;


public class MessageTypeTest {
	private static final int MESSAGE_TYPE_SIZE = 2;

	private static final String MAIL_TYPE_NAME = "E-Mail";
	private static final String SMS_TYPE_NAME = "SMS";

	@Test
	public void testMessageTypeSize() {
		assertEquals(MESSAGE_TYPE_SIZE, MessageType.values().length);
	}

	@Test
	public void testGetMessageTypeName() {
		assertEquals(MAIL_TYPE_NAME, MessageType.MAIL.getName());
		assertEquals(SMS_TYPE_NAME, MessageType.SMS.getName());
	}

	@Test
	public void testToString() {
		assertEquals(MAIL_TYPE_NAME, MessageType.MAIL.toString());
		assertEquals(SMS_TYPE_NAME, MessageType.SMS.toString());
	}

	@Test
	public void testFromFile() {
		assertEquals(MessageType.SMS, MessageType.fromName("SMS"));
		assertEquals(MessageType.MAIL, MessageType.fromName("E-Mail"));

		try {
			MessageType.fromName("not_exists");
			fail();
		} catch (Exception e) {
		}
	}

}
