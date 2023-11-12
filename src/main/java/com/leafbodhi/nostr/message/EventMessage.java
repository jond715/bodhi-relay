package com.leafbodhi.nostr.message;

import com.leafbodhi.nostr.entity.Event;
import com.leafbodhi.nostr.entity.IMessage;

public class EventMessage implements IMessage {

	private final String messageType;
	private final String subscriptionId;
	private final Event event;

	public EventMessage(String messageType, String subscriptionId, Event event) {
		this.messageType = messageType;
		this.subscriptionId = subscriptionId;
		this.event = event;
	}

	@Override
	public String encode() {
		return "[\"" + messageType + "\",\"" + subscriptionId + "\"," + event.toJson() + "]";
	}
}
