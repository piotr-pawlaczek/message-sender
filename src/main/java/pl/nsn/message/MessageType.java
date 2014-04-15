/*
 * Copyright (c) 2013 Nokia Siemens Networks. All rights reserved.
 */
package pl.nsn.message;

/**
 * Enum representing messages types
 * 
 * @author piotr.pawlaczek
 * 
 */
public enum MessageType {
	MAIL("E-Mail"), SMS("SMS");

	private String name;

	private MessageType(String name) {
		this.name = name;
	}

	/**
	 * Gets type name
	 * 
	 * @return type name
	 */
	public String getName() {
		return this.name;
	}

	@Override
	public String toString() {
		return this.name;
	}

	/**
	 * Returns enum for a given name
	 * 
	 * @param name
	 *            enum name
	 * @return enum type
	 */
	public static MessageType fromName(String name) {
		for (MessageType value : MessageType.values()) {
			if (value.getName().equals(name)) {
				return value;
			}
		}
		throw new IllegalArgumentException("No enum for parameter: " + name);
	}
}
