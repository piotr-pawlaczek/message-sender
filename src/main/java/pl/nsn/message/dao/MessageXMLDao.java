/*
 * Copyright (c) 2013 Nokia Siemens Networks. All rights reserved.
 */
package pl.nsn.message.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import com.google.common.collect.Lists;

import pl.nsn.message.Message;
import pl.nsn.message.MessageFactory;
import pl.nsn.message.MessageType;
import pl.nsn.message.utils.XMLUtils;


/**
 * DAO class for saving and reading messages from xml file
 * 
 * @author piotr.pawlaczek
 * 
 */
public class MessageXMLDao implements MessageDao {
	private File file;

	public MessageXMLDao(File file) {
		this.file = file;
	}

	@Override
	public void saveMessage(Message message) {
		Document doc = XMLUtils.getXmlDocument(this.file);

		Element messageElement = createMessageTag(doc, createMessageChilds(doc, message));
		Element rootElement = doc.getDocumentElement();
		rootElement.appendChild(messageElement);

		XMLUtils.updateXMLFile(doc, this.file);
	}

	private List<Element> createMessageChilds(Document doc, Message message) {
		Element typeElement = createTag(message.getType().name(), doc, "type");
		Element recipientElement = createTag(message.getRecipient(), doc, "recipient");
		Element bodyElement = createTag(message.getText(), doc, "body");
		return Lists.newArrayList(typeElement, recipientElement, bodyElement);
	}

	private Element createTag(String textValue, Document doc, String tagName) {
		Element typeElement = doc.createElement(tagName);
		Text typeText = doc.createTextNode(textValue);
		typeElement.appendChild(typeText);
		return typeElement;
	}

	private Element createMessageTag(Document doc, List<Element> elements) {
		Element messageElement = doc.createElement("message");
		for (Element element : elements) {
			messageElement.appendChild(element);
		}
		return messageElement;
	}

	@Override
	public List<Message> list() {
		Document doc = XMLUtils.getXmlDocument(this.file);
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath path = xpathFactory.newXPath();
		return createMessages(doc, path, getMessagesCount(doc, path));
	}

	private List<Message> createMessages(Document doc, XPath path, int messagesCount) {
		List<Message> list = new ArrayList<Message>();
		for (int i = 1; i <= messagesCount; i++) {
			try {
				Node messageNode = (Node) path.evaluate(prepareMessageQuery(i), doc, XPathConstants.NODE);
				list.add(createMessage(messageNode, path));
			} catch (XPathExpressionException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	private String prepareMessageQuery(int id) {
		return "messages/message[" + id + "]";
	}

	private Message createMessage(Node messageNode, XPath path) {
		try {
			String messageType = (String) path.evaluate("type", messageNode, XPathConstants.STRING);
			String recipient = (String) path.evaluate("recipient", messageNode, XPathConstants.STRING);
			String text = (String) path.evaluate("body", messageNode, XPathConstants.STRING);

			Message message = MessageFactory.createMessage(MessageType.valueOf(messageType));
			message.setRecipient(recipient);
			message.setText(text);
			return message;
		} catch (XPathExpressionException e) {
			throw new RuntimeException("Error while parsing <message> tag");
		}
	}

	private int getMessagesCount(Document doc, XPath path) {
		try {
			return ((Number) path.evaluate("count(/messages/message)", doc, XPathConstants.NUMBER)).intValue();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return 0;
	}
}