package com.leafbodhi.nostr.entity;
/**
 * @author Jond
 */
public interface IMessage {
	/**
	 * Encoded into the json format supported by nostr
	 * @return
	 * ["OK", "b1a649ebe8b435ec71d3784793f3bbf4b93e64e17568a741aecd4c7ddeafce30", true, ""]
	 * ["EOSE","1234123131"]
	 * ...
	 */
	String encode();

}
