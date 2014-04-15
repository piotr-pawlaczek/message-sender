package pl.nsn.message;

import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.apache.log4j.Logger;

import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.verifyAll;;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Logger.class, System.class})
public class MailMessageTest {
	private static final String MESSAGE_TEXT = "Message text";
	private static final String RECIPIENT_MAIL_ADDRESS_OK = "nsn@nsn.com";
	
	Message message;
	
	@Before
	public void setUp() {
		this.message = new MailMessage();
	}
	
	@Test
	public void testGetText() {
		this.message.setText(MESSAGE_TEXT);
		assertEquals(MESSAGE_TEXT, this.message.getText());
	}
	
	@Test
	public void testGetRecipient() {
		this.message.setRecipient(RECIPIENT_MAIL_ADDRESS_OK);
		assertEquals(RECIPIENT_MAIL_ADDRESS_OK, this.message.getRecipient());
	}
	
	@Test
	public void testMessageType() {
		assertEquals(MessageType.MAIL, this.message.getType());
		message.send();
	}
	
	@Test
	public void testSend() {
		Logger loggerMock = PowerMock.createMock(Logger.class);
		loggerMock.info("Mail message sent.");
		EasyMock.expectLastCall();
				
		replayAll();
		Whitebox.setInternalState(this.message, loggerMock);
		
		this.message.send();
		verifyAll();
	}
	
	
}
