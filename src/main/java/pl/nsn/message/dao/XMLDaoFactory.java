/*
 * Copyright (c) 2013 Nokia Siemens Networks. All rights reserved.
 */
package pl.nsn.message.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * Factory for creating XML DAO objects
 * 
 * @author piotr.pawlaczek
 * 
 */
public class XMLDaoFactory extends DaoFactory {

	private File messagesFile = new File("D:/messages.xml");

	@Override
	public MessageDao getMessageDAO() {
		boolean flag = this.messagesFile.exists();
		if (!flag) {
			fillFile();
		}
		return new MessageXMLDao(this.messagesFile);
	}

	private void fillFile() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();

			Element messegesElement = doc.createElement("messages");
			doc.appendChild(messegesElement);

			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			transformer.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(this.messagesFile)));
		} catch (Exception e) {
			throw new RuntimeException("Cannot access ", e);
		}
	}

}
