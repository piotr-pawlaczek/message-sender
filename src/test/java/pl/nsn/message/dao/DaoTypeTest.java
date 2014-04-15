/*
 * Copyright (c) 2013 Nokia Siemens Networks. All rights reserved.
 */
package pl.nsn.message.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * @author piotr.pawlaczek
 *
 */
public class DaoTypeTest {

	@Test
	public void testPersistenceTypeSize() {
		assertEquals(1, DaoType.values().length);
	}
	
	@Test
	public void testXMLPersistanceExists() {
		for(DaoType value : DaoType.values()) {
			if(value == DaoType.XML) {
				return;
			}
		}
		fail();
	}
}
