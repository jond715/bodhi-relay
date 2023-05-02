package com.leafbodhi.nostr.message;

import com.leafbodhi.nostr.entity.IMessage;
import com.leafbodhi.nostr.entity.MessageType;

public class OkMessage implements IMessage {

	private final String messageType;
	private final String eventId;
	private final Boolean flag;
	private final String message;

	public OkMessage(String eventId, Boolean flag, String message) {
		this.messageType = MessageType.OK.name();
		this.eventId = eventId;
		this.flag = flag;
		this.message = message;
	}

	@Override
	public String toString() {
		return "[\"" + messageType + "\",\"" + eventId + "\"," + flag + ",\"" + message + "\"]";
	}
	
}
