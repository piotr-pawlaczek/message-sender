/*
 * Copyright (c) 2013 Nokia Siemens Networks. All rights reserved.
 */
package pl.nsn.message.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileOutputStream;

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
import org.powermock.reflect.Whitebox;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * @author piotr.pawlaczek
 * 
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({
		DocumentBuilderFactory.class, TransformerFactory.class, File.class, XMLDaoFactory.class })
public class XMLDaoFactoryTest {
	@Test
	public void testGetMessageXMLDao() {
		File fileMock = PowerMock.createStrictMock(File.class);
		fileMock.exists();
		PowerMock.expectLastCall().andReturn(true);

		DaoFactory factory = DaoFactory.getDAOFactory(DaoType.XML);

		PowerMock.replayAll();
		Whitebox.setInternalState(factory, "messagesFile", fileMock);
		MessageDao dao = factory.getMessageDAO();
		PowerMock.verifyAll();

		assertTrue(dao instanceof MessageXMLDao);
		assertEquals(fileMock, Whitebox.getInternalState(dao, File.class));
	}

	@Test
	public void testGetMessageXMLDaoWhenXMLFoleNotExists() throws Exception {
		File fileMock = PowerMock.createStrictMock(File.class);
		EasyMock.expect(fileMock.exists()).andReturn(false);

		DaoFactory factory = DaoFactory.getDAOFactory(DaoType.XML);
		Whitebox.setInternalState(factory, fileMock);

		Element messageMock = PowerMock.createStrictMock(Element.class);

		Document documentMock = PowerMock.createStrictMock(Document.class);
		EasyMock.expect(documentMock.createElement("messages")).andReturn(messageMock);
		documentMock.appendChild(messageMock);
		EasyMock.expectLastCall().andReturn(null);

		FileOutputStream fileOutputStreamMock = PowerMock
				.createStrictMockAndExpectNew(FileOutputStream.class, fileMock);

		StreamResult streamResultMock = PowerMock
				.createStrictMockAndExpectNew(StreamResult.class, fileOutputStreamMock);

		DOMSource domSourceMock = PowerMock.createStrictMockAndExpectNew(DOMSource.class, documentMock);

		Transformer transformerMock = PowerMock.createStrictMock(Transformer.class);
		transformerMock.setOutputProperty(OutputKeys.INDENT, "yes");
		EasyMock.expectLastCall();
		transformerMock.transform(domSourceMock, streamResultMock);

		TransformerFactory transformerFactoryMock = PowerMock.createStrictMock(TransformerFactory.class);
		EasyMock.expect(transformerFactoryMock.newTransformer()).andReturn(transformerMock);

		PowerMock.mockStatic(TransformerFactory.class);
		TransformerFactory.newInstance();
		PowerMock.expectLastCall().andReturn(transformerFactoryMock);

		DocumentBuilder builderMock = PowerMock.createStrictMock(DocumentBuilder.class);
		EasyMock.expect(builderMock.newDocument()).andReturn(documentMock);

		DocumentBuilderFactory factoryMock = PowerMock.createStrictMock(DocumentBuilderFactory.class);
		EasyMock.expect(factoryMock.newDocumentBuilder()).andReturn(builderMock);

		PowerMock.mockStatic(DocumentBuilderFactory.class);
		DocumentBuilderFactory.newInstance();
		PowerMock.expectLastCall().andReturn(factoryMock);

		PowerMock.replayAll();
		factory.getMessageDAO();
		PowerMock.verifyAll();
	}
}
