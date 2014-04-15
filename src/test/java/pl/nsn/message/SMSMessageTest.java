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
@PrepareForTest({Logger.class})
public class SMSMessageTest {
		private static final String MESSAGE_TEXT = "Message text";
		private static final String RECIPIENT_MAIL_ADDRESS_OK = "nsn@nsn.com";
		private static final String SENT_LOG = "SMS message sent.";
		
		Message message;
		
		@Before
		public void setUp() {
			this.message = new SMSMessage();
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
			assertEquals(MessageType.SMS, this.message.getType());
		}
		
		@Test
		public void testSend() {
			Logger loggerMock = PowerMock.createMock(Logger.class);
			loggerMock.info(SENT_LOG);
			EasyMock.expectLastCall();
					
			replayAll();
			Whitebox.setInternalState(this.message, loggerMock);
			
			this.message.send();
			verifyAll();
		}
	}

