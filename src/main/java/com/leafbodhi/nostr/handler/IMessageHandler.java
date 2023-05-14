package com.leafbodhi.nostr.handler;

import java.util.List;
import java.util.Map;

import com.leafbodhi.nostr.config.NostrConfig;
import com.leafbodhi.nostr.entity.Event;
import com.leafbodhi.nostr.entity.Subscription;

import jakarta.websocket.Session;

public interface IMessageHandler {
	
	public void handle(Session session,Event event,Map<Session, List<Subscription>> subscribers);

	public NostrConfig getConfig();
}
