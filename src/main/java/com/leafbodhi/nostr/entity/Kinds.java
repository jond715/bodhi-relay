package com.leafbodhi.nostr.entity;

/**
 * 
 * @author jond
 *
 */
public interface Kinds {
	public static final int UNDEFINED = -1;

	/** NIP-01 */
	public static final int SET_METADATA = 0;
	/** NIP-01 */
	public static final int TEXT_NOTE = 1;
	/** NIP-01 */
	public static final int RECOMMEND_SERVER = 2;
	/** NIP-02 */
	public static final int CONTACT_LIST = 3;
	/** NIP-04 */
	public static final int ENCRYPTED_DIRECT_MESSAGE = 4;
	/** NIP-09 */
	public static final int DELETION = 5;
	/** NIP-18 */
	public static final int REPOSTS = 6;
	/** NIP-25 */
	public static final int REACTION = 7;
	/** NIP-58 */
	public static final int BADGE_AWARD = 8;

	// Channels
	/** NIP-28 */
	public static final int CHANNEL_CREATION = 40;
	/** NIP-28 */
	public static final int CHANNEL_METADATA = 41;
	/** NIP-28 */
	public static final int CHANNEL_MESSAGE = 42;
	/** NIP-28 */
	public static final int CHANNEL_HIDE_MESSAGE = 43;
	/** NIP-28 */
	public static final int CHANNEL_MUTE_USER = 44;
	public static final int CHANNEL_RESERVED_FIRST = 45;
	public static final int CHANNEL_RESERVED_LAST = 49;

	// Relay-only
	public static final int RELAY_INVITE = 50;
	public static final int INVOICE_UPDATE = 402;

	// Regular Events
	/** NIP-16 */
	public static final int REGULAR_FIRST = 1000;
	/** NIP-16 */
	public static final int REGULAR_LAST = 9999;
	// Replaceable events
	/** NIP-16 */
	public static final int REPLACEABLE_FIRST = 10000;
	/** NIP-16 */
	public static final int REPLACEABLE_LAST = 19999;
	// Ephemeral events
	/** NIP-16 */
	public static final int EPHEMERAL_FIRST = 20000;
	/** NIP-16 */
	public static final int EPHEMERAL_LAST = 29999;

	// Parameterized replaceable events
	/** NIP-33 */
	public static final int PARAMETERIZED_REPLACEABLE_FIRST = 30000;
	/** NIP-33 */
	public static final int PARAMETERIZED_REPLACEABLE_LAST = 39999;

	public static final int USER_APPLICATION_FIRST = 40000;
	public static final int USER_APPLICATION_LAST = Integer.MAX_VALUE;

}
