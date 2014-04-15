/*
 * Copyright (c) 2013 Nokia Siemens Networks. All rights reserved.
 */
package pl.nsn.message;

import java.util.List;

import com.google.common.collect.Lists;

import pl.nsn.message.dao.DaoFactory;
import pl.nsn.message.dao.MessageDao;
import pl.nsn.message.dao.DaoType;


/**
 * Bean for persist message operations
 * 
 * @author piotr.pawlaczek
 * 
 */
public class MessageBean {

	private MessageDao messageDao;

	/**
	 * Default constructor which creates message dao object
	 */
	public MessageBean() {
		this.messageDao = DaoFactory.getDAOFactory(DaoType.XML).getMessageDAO();
	}

	/**
	 * Persists message
	 * 
	 * @param message
	 *            message to persist
	 */
	public void save(Message message) {
		this.messageDao.saveMessage(message);
	}

	/**
	 * Gets all messages from chosen source
	 * 
	 * @return all previously saved messages
	 */
	public List<Message> getAllMessages() {
		return Lists.reverse(this.messageDao.list());
	}
}
