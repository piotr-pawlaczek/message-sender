/*
 * Copyright (c) 2013 Nokia Siemens Networks. All rights reserved.
 */
package pl.nsn.message.dao;

/**
 * Factory for data access object
 * 
 * @author piotr.pawlaczek
 * 
 */
public abstract class DaoFactory {
	public abstract MessageDao getMessageDAO();

	public static DaoFactory getDAOFactory(DaoType type) {
		switch (type) {
			case XML:
				return new XMLDaoFactory();
			default:
				throw new IllegalArgumentException("Invalid type: " + type);
		}
	}

}