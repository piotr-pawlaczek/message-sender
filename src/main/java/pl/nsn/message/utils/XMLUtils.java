/*
 * Copyright (c) 2013 Nokia Siemens Networks. All rights reserved.
 */
package pl.nsn.message.utils;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;


/**
 * XML utils class
 * 
 * @author piotr.pawlaczek
 */
public class XMLUtils {

	/**
	 * Updates existing file with Document object
	 * 
	 * @param doc
	 *            xml document
	 * @param file
	 *            xml file to update
	 * @throws TransformerFactoryConfigurationError
	 */
	public static void updateXMLFile(Document doc, File file) throws TransformerFactoryConfigurationError {
		try {
			DOMSource source = new DOMSource(doc);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);
		} catch (Exception e) {
			throw new RuntimeException("Exception while saving message to xml file", e);
		}
	}

	/**
	 * Gets Document representation of xml file
	 * 
	 * @param file
	 *            xml file
	 * @return Document object
	 */
	public static Document getXmlDocument(File file) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(file);
			return doc;
		} catch (Exception e) {
			throw new RuntimeException("Could not parse XML document", e);
		}
	}
}