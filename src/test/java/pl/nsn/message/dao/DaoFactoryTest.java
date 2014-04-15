/*
 * Copyright (c) 2013 Nokia Siemens Networks. All rights reserved.
 */
package pl.nsn.message.dao;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author piotr.pawlaczek
 *
 */
public class DaoFactoryTest {

	@Test
	public void testCreateXMLDaoFactory() {
		DaoFactory factory = DaoFactory.getDAOFactory(DaoType.XML);
		assertTrue(factory instanceof XMLDaoFactory);
	}
}
