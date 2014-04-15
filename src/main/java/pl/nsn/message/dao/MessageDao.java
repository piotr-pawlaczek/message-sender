/*
 * Copyright (c) 2013 Nokia Siemens Networks. All rights reserved.
 */
package pl.nsn.message.dao;

import java.util.List;

import pl.nsn.message.Message;

/**
 * 
 * @author piotr.pawlaczek
 *
 */
public interface MessageDao {
	/**
	 * Save massage
	 * 
	 * @param message
	 *            massage to save
	 */
	void saveMessage(Message message);

	/**
	 * Get all messages
	 * 
	 * @return all previously saved messages
	 */
	List<Message> list();
}
