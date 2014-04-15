/*
 * Copyright (c) 2013 Nokia Siemens Networks. All rights reserved.
 */
package pl.nsn.message;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import com.google.common.collect.Lists;

import pl.nsn.message.MailMessage;
import pl.nsn.message.Message;
import pl.nsn.message.MessageBean;
import pl.nsn.message.SMSMessage;
import pl.nsn.message.dao.MessageDao;


/**
 * @author piotr.pawlaczek
 * 
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({
	MessageBean.class })
public class MessageBeanTest {
	private MessageBean bean;
	
	@Before
	public void setUp() {
		this.bean = new MessageBean();
	}
	
	@Test
	public void testList() {
		Message msg1 = new MailMessage("Mailrec1", "text1");
		Message msg2 = new MailMessage("Mailrec2", "text2");
		Message msg3 = new SMSMessage("rec1", "text1");
		Message msg4 = new SMSMessage("rec1", "text1");
		
		MessageDao daoMock = PowerMock.createMock(MessageDao.class);
		daoMock.list();
		EasyMock.expectLastCall().andReturn(Lists.newArrayList(msg1, msg2, msg3, msg4));
		
		PowerMock.replayAll();
		Whitebox.setInternalState(this.bean, daoMock);
		
		List<Message> messageList = this.bean.getAllMessages();
		assertEquals(4, messageList.size());
		assertEquals(msg1, messageList.get(3));
		assertEquals(msg2, messageList.get(2));
		assertEquals(msg3, messageList.get(1));
		assertEquals(msg4, messageList.get(0));
		
		PowerMock.verifyAll();
	}
	
	@Test
	public void testSaveMessage() {
		Message msg = new MailMessage();
		MessageDao daoMock = PowerMock.createMock(MessageDao.class);
		daoMock.saveMessage(msg);
		PowerMock.expectLastCall();
		
		PowerMock.replayAll();
		Whitebox.setInternalState(this.bean, daoMock);
		this.bean.save(msg);
		
		PowerMock.verifyAll();
	}
}
