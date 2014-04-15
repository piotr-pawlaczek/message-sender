/*
 * Copyright (c) 2013 Nokia Siemens Networks. All rights reserved.
 */
package pl.nsn.message.utils;

import static org.junit.Assert.assertEquals;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.w3c.dom.Document;


/**
 * @author piotr.pawlaczek
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({
	DocumentBuilderFactory.class, XMLUtils.class, TransformerFactory.class })
public class XMLUtilsTest {
	@Test
	public void testGetXmlDocument() throws Exception {
		File fileMock = PowerMock.createStrictMock(File.class);
		Document documentMock = PowerMock.createStrictMock(Document.class);
		
		DocumentBuilder builderMock = PowerMock.createStrictMock(DocumentBuilder.class);
		EasyMock.expect(builderMock.parse(fileMock)).andReturn(documentMock);
		
		DocumentBuilderFactory factoryMock = PowerMock.createStrictMock(DocumentBuilderFactory.class);
		EasyMock.expect(factoryMock.newDocumentBuilder()).andReturn(builderMock);
		
		PowerMock.mockStatic(DocumentBuilderFactory.class);
		DocumentBuilderFactory.newInstance();
		PowerMock.expectLastCall().andReturn(factoryMock);
		
		PowerMock.replayAll();
		XMLUtils.getXmlDocument(fileMock);		
		PowerMock.verifyAll();
	}
	
	@Test
	public void testGetXmlDocumentThrowsException() throws Exception {
		File fileMock = PowerMock.createStrictMock(File.class);
		
		DocumentBuilder builderMock = PowerMock.createStrictMock(DocumentBuilder.class);
		EasyMock.expect(builderMock.parse(fileMock)).andThrow(new IllegalArgumentException());
		
		DocumentBuilderFactory factoryMock = PowerMock.createStrictMock(DocumentBuilderFactory.class);
		EasyMock.expect(factoryMock.newDocumentBuilder()).andReturn(builderMock);
		
		PowerMock.mockStatic(DocumentBuilderFactory.class);
		DocumentBuilderFactory.newInstance();
		PowerMock.expectLastCall().andReturn(factoryMock);
		
		PowerMock.replayAll();
		try{
			XMLUtils.getXmlDocument(fileMock);	
		} catch(Exception e) {
			assertEquals("Could not parse XML document", e.getMessage());
		}
		PowerMock.verifyAll();
	}
	
	@Test
	public void testUpdateXMLFile() throws Exception {
		File fileMock = PowerMock.createStrictMock(File.class);
		Document documentMock = PowerMock.createStrictMock(Document.class);
		
		DOMSource domSourceMock = PowerMock.createStrictMockAndExpectNew(DOMSource.class, documentMock);
		Transformer transformerMock = PowerMock.createStrictMock(Transformer.class);
		transformerMock.setOutputProperty(OutputKeys.INDENT, "yes");
		PowerMock.expectLastCall();

		StreamResult resultMock = PowerMock.createStrictMockAndExpectNew(StreamResult.class, fileMock);
		transformerMock.transform(domSourceMock, resultMock);
		PowerMock.expectLastCall();
		
		TransformerFactory transformerFactoryMock = PowerMock.createStrictMock(TransformerFactory.class);
		EasyMock.expect(transformerFactoryMock.newTransformer()).andReturn(transformerMock);
		
		
		PowerMock.mockStatic(TransformerFactory.class);
		TransformerFactory.newInstance();
		PowerMock.expectLastCall().andReturn(transformerFactoryMock);
		
		PowerMock.replayAll();
		XMLUtils.updateXMLFile(documentMock, fileMock);		
		PowerMock.verifyAll();
	}

}
