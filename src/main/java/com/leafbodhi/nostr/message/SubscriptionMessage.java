package com.leafbodhi.nostr.message;

import com.leafbodhi.nostr.entity.Filter;
import com.leafbodhi.nostr.entity.IMessage;
import com.leafbodhi.nostr.entity.MessageType;

/**
 * @author Jond
 */
public class SubscriptionMessage implements IMessage {
	private final String messageType;
	private final String subscriptionId;
	private final Filter  filter;

	public SubscriptionMessage(String subscriptionId, Filter  filter) {
		this.messageType = MessageType.REQ.name();
		this.subscriptionId = subscriptionId;
		this.filter = filter;
	}

	@Override
	public String encode() {
		return "[\"" + messageType + "\",\"" + subscriptionId + ",\"" + filter.toJsonString() + "\"]";
	}
}
