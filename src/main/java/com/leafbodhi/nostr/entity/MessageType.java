package com.leafbodhi.nostr.entity;


/**
 * 
 * Clients can send 3 types of messages, which must be JSON arrays, according to the following patterns:
 *	["EVENT", <event JSON as defined above>], used to publish events.
 *	["REQ", <subscription_id>, <filters JSON>...], used to request events and subscribe to new updates.
 *	["CLOSE", <subscription_id>], used to stop previous subscriptions.
 * 
 * Relays can send 2 types of messages, which must also be JSON arrays, according to the following patterns:
 *	["EVENT", <subscription_id>, <event JSON as defined above>], used to send events requested by clients.
 *	["NOTICE", <message>], used to send human-readable error messages or other things to clients.
 *
 * #Extension:
 * 	
 *
 * @author jond
 *
 */
public enum MessageType {
	

	EVENT("EVENT","IN/OUT"),
	
	REQ("REQ","IN"),
    
	CLOSE("CLOSE","IN"),
    
    NOTICE("NOTICE","OUT"),
    /** nip-15 */
    EOSE("EOSE","OUT"),
    /** nip-20 */
    OK("OK","OUT"),
    /** nip-42 */
    AUTH("AUTH", "IN/OUT"),
    /** nip-45 */
    COUNT("COUNT","IN/OUT`");
    
    private final String messageType;
    private final String description;
    private MessageType(String messageType,String description) {
        this.messageType = messageType;
        this.description = description;
    }
    public String getValue() {
        return messageType;
    }
    public String getDescription() {
    	return description;
    }

}
