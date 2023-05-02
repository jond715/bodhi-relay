package com.leafbodhi.nostr.message;

import com.leafbodhi.nostr.entity.IMessage;
import com.leafbodhi.nostr.entity.MessageType;

public class EOSEMessage implements IMessage {

	private final String messageType;
	private final String subscriptionId;

	public EOSEMessage(String subscriptionId) {
		this.messageType = MessageType.EOSE.name();
		this.subscriptionId = subscriptionId;
	}

	@Override
	public String toString() {
		return "[\"" + messageType + "\",\"" + subscriptionId + "\"]";
	}
	
}
