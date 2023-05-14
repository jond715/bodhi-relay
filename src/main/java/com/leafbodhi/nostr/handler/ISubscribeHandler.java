package com.leafbodhi.nostr.handler;

import java.util.List;
import java.util.Map;

import com.leafbodhi.nostr.entity.Filter;
import com.leafbodhi.nostr.entity.Subscription;

import jakarta.websocket.Session;

public interface ISubscribeHandler {
	
	public void handle(Session session,String subscriptionId, List<Filter> filters,Map<Session, List<Subscription>> subscribers);

}
