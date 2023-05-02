package com.leafbodhi.nostr.handler;

import jakarta.websocket.Session;

public interface IUnsubscribeHandler {
	
	public void handle(Session session,String subscriptionId);

}
