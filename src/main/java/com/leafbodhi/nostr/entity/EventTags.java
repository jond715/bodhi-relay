package com.leafbodhi.nostr.entity;

public interface EventTags {
	/**coordinates to an event	*/
	public static final String Coordinates = "a";
	
	public static final String Identifier = "d";
	/** event id (hex) */
	public static final String Event = "e";
	
	public static final String Geohash = "g";
	
	public static final String Identity = "i";
	
	/** pubkey (hex) */
	public static final String Pubkey = "p";
	
	/** a reference (URL, etc)	*/
	public static final String Reference = "r";
	public static final String Hashtag = "t";
	/** millisats */
	public static final String Amount = "amount";
	/** bolt11 invoice	 */
	public static final String Bolt11 = "bolt11";
	/** challenge string */
	public static final String Challenge = "challenge";
	/** warning reason */
	public static final String ContentWarning = "content-warning";
	/** pubkey, conditions, delegation token */
	public static final String Delegation = "delegation";
	/** badge description or invoice description                                                                                                                                                                 */
	public static final String Description = "description";
	/** unix timestamp (string) */
	public static final String Expiration = "expiration";
	
	public static final String Image = "image";
	/** bech32 encoded lnurl */
	public static final String Lnurl = "lnurl";
	/** badge name */
	public static final String Name = "name";
	/** random nonce */
	public static final String Nonce = "nonce";
	/** hash of bolt11 invoice */
	public static final String Preimage = "preimage";
	/** unix timestamp (string) */
	public static final String PublishedAt = "published_at";
	/** relay url */
	public static final String Relay = "relay";
	
	/** relay list */
	public static final String Relays = "relays";

	public static final String Subject = "subject";
	/** article summary */
	public static final String Summary = "summary";
	/** badge thumbnail	*/
	public static final String Thumb = "thumb";
	/** article title */
	public static final String Title = "title";
	/** profile name */
	public static final String Zap = "zap";

	
	public static final String Deduplication = "d";
	

}
