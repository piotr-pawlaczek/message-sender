package pl.nsn.message.dao;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import pl.nsn.message.MailMessage;
import pl.nsn.message.Message;
import pl.nsn.message.MessageType;
import pl.nsn.message.utils.XMLUtils;


public class MessageXMLDaoTest {
	private static final String MAIL_MESSAGE_RECIPIENT = "Tomek";
	private static final String MAIL_MESSAGE_TEXT = "Don't forget me this weekend!";

	private static final String SMS_MESSAGE_RECIPIENT = "Monika";
	private static final String SMS_MESSAGE_TEXT = "I promise I won't!";

	private static final String RECIPIENT = "recipient1";
	private static final String TEXT = "test text";

	private MessageDao messagedao;

	@Test
	public void testList() {
		this.messagedao = new MessageXMLDao(new File("src/test/resources/messagesTest.xml"));
		List<Message> messages = this.messagedao.list();
		assertEquals(3, messages.size());

		assertEquals(MessageType.MAIL, messages.get(0).getType());
		assertEquals(MAIL_MESSAGE_RECIPIENT, messages.get(0).getRecipient());
		assertEquals(MAIL_MESSAGE_TEXT, messages.get(0).getText());

		assertEquals(MessageType.SMS, messages.get(2).getType());
		assertEquals(SMS_MESSAGE_RECIPIENT, messages.get(2).getRecipient());
		assertEquals(SMS_MESSAGE_TEXT, messages.get(2).getText());
	}

	@Test
	public void testSave() {
		File destinationFile = new File("src/test/resources/destinationTestMessages.xml");

		this.messagedao = new MessageXMLDao(destinationFile);
		Message mailMessage = new MailMessage(RECIPIENT, TEXT);
		this.messagedao.saveMessage(mailMessage);

		List<Message> list = this.messagedao.list();
		assertEquals(1, list.size());
		assertEquals(MessageType.MAIL, list.get(0).getType());
		assertEquals(RECIPIENT, list.get(0).getRecipient());
		assertEquals(TEXT, list.get(0).getText());

		Document doc = removeMessages(destinationFile);
		XMLUtils.updateXMLFile(doc, destinationFile);
	}

	private Document removeMessages(File xmlFile) {
		Document doc = XMLUtils.getXmlDocument(xmlFile);
		Element rootElement = doc.getDocumentElement();
		NodeList messages = rootElement.getChildNodes();
		while (messages.getLength() > 0) {
			Node node = messages.item(0);
			node.getParentNode().removeChild(node);
		}
		return doc;
	}
}
