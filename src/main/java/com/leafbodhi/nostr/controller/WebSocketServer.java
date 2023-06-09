package com.leafbodhi.nostr.controller;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leafbodhi.nostr.config.MessageEncoder;
import com.leafbodhi.nostr.entity.Event;
import com.leafbodhi.nostr.entity.MessageType;
import com.leafbodhi.nostr.entity.PublishEventIn;
import com.leafbodhi.nostr.entity.SubscribeEventsIn;
import com.leafbodhi.nostr.entity.Subscription;
import com.leafbodhi.nostr.entity.UnsubscribeIn;
import com.leafbodhi.nostr.handler.ISubscribeHandler;
import com.leafbodhi.nostr.handler.IUnsubscribeHandler;
import com.leafbodhi.nostr.handler.handlers.BasicEventHandler;
import com.leafbodhi.nostr.handler.handlers.DelegatedEventMessageHandler;
import com.leafbodhi.nostr.handler.handlers.SubscribeHandler;
import com.leafbodhi.nostr.handler.handlers.UnsubscribeHandler;
import com.leafbodhi.nostr.message.EOSEMessage;

import jakarta.websocket.EncodeException;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ServerEndpoint(value = "/", encoders = { MessageEncoder.class })
public class WebSocketServer {

	private static BasicEventHandler basicEventHandler;
	private static DelegatedEventMessageHandler delegatedEventMessageHandler;
	private static ISubscribeHandler subscribeHandler;
	private static IUnsubscribeHandler unsubscribeHandler;

	public static final ByteBuffer HEARTBEAT = ByteBuffer.wrap("heartbeat".getBytes());

	private static ObjectMapper OBJECTMAPPER = new ObjectMapper();

	private Map<Session, List<Subscription>> subscribers = new ConcurrentHashMap<>();

	public static void setApplicationContext(ApplicationContext applicationContext) {
		basicEventHandler = applicationContext.getBean(BasicEventHandler.class);
		delegatedEventMessageHandler = applicationContext.getBean(DelegatedEventMessageHandler.class);

		subscribeHandler = applicationContext.getBean(SubscribeHandler.class);
		unsubscribeHandler = applicationContext.getBean(UnsubscribeHandler.class);
	}

	@OnOpen
	public void onOpen(Session session) {
		session.getContainer();
		session.getProtocolVersion();
		log.info("WebSocketServer receive new connection: " + session.getId() + ", message.toString:"
				+ session.toString());
		subscribers.put(session, new ArrayList<Subscription>());
	}

	@OnClose
	public void onClose(Session session) {
		log.info("connection close!");
		subscribers.remove(session);
	}

	@OnError
	public void onError(Session session, Throwable throwable) {
		log.error(throwable.getMessage());
		subscribers.remove(session);
	}

	@OnMessage
	public void onMessage(Session session, String message) throws IOException, EncodeException {
		log.info("NostrRelay get connect：" + session.getId() + "，get message：" + message);
		JsonNode node;
		try {
			node = OBJECTMAPPER.readTree(message);
		} catch (JsonProcessingException e) {
			log.error("message is not a valid JSON format", e);
			return;
		}

		if (!node.isArray() && node.size() >= 2) {
			log.error("message is not a valid JSON array");
			return;
		}

		String messageType = node.get(0).asText();
		try {
			switch (MessageType.valueOf(messageType)) {
			case EVENT -> handleEvent(session, PublishEventIn.fromJson(node.toString()));
			case REQ -> handleRequest(session, SubscribeEventsIn.fromJson(node.toString()));
			case CLOSE -> handleClose(session, UnsubscribeIn.fromJson(node.toString()));
			default -> handleUnknown(session, node.toString());
			}
		} catch (JsonProcessingException e) {
			log.error("error on deserialization of JSON array", e);
		}
	}

	private void handleEvent(Session session, PublishEventIn message) throws IOException, EncodeException {
		Event event = message.event;
		if (isDelegatedEvent(event)) {
			delegatedEventMessageHandler.handle(session, event, this.subscribers);
		} else {
			basicEventHandler.handle(session, event, this.subscribers);
		}
	}

	private boolean isDelegatedEvent(Event event) {
		// TODO
		return false;
	}

	private void handleRequest(Session session, SubscribeEventsIn message) throws IOException, EncodeException {
		log.info(message.toString());
		subscribeHandler.handle(session, message.getSubscriptionId(), message.getFilters(), subscribers);

	}

	private void handleClose(Session session, UnsubscribeIn message) {
		unsubscribeHandler.handle(session, message.getSubscriptionId());// empty

		log.info(message.toString());
		List<Subscription> list = subscribers.get(session);
		for (Iterator<Subscription> iterator = list.iterator(); iterator.hasNext();) {
			Subscription subscription = iterator.next();
			if (subscription.getSubscriptionId().equals(message.getSubscriptionId())) {
				subscribers.get(session).remove(subscription);
			}
		}

		try {
			session.getBasicRemote().sendObject(new EOSEMessage(message.getSubscriptionId()));
		} catch (IOException | EncodeException e) {
			log.error("server send EOSE message error", e);
		}
	}

	private void handleUnknown(Session session, String message) {
		// ignore
		log.error("ignore unknown message: {}" ,message);
	}

}