package com.leafbodhi.nostr.handler.handlers;

import org.springframework.stereotype.Component;

import com.leafbodhi.nostr.handler.IUnsubscribeHandler;

import jakarta.websocket.Session;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UnsubscribeHandler implements IUnsubscribeHandler {

	@Override
	public void handle(Session session, String subscriptionId) {
		// TODO Auto-generated method stub
		log.info("unsubscribe event subscriptionId:{}",subscriptionId);
	}

}
