package com.leafbodhi.nostr.message;

import com.leafbodhi.nostr.entity.IMessage;
import com.leafbodhi.nostr.entity.MessageType;

public class NoticeMessage implements IMessage {

	private final String messageType;
	private final String message;

	public NoticeMessage(String message) {
		this.messageType = MessageType.NOTICE.name();
		this.message = message;
	}

	@Override
	public String toString() {
		return "[\"" + messageType + ",\"" + message + "\"]";
	}

}
