package com.leafbodhi.nostr.handler.handlers;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.leafbodhi.nostr.entity.Event;
import com.leafbodhi.nostr.entity.Filter;
import com.leafbodhi.nostr.entity.MessageType;
import com.leafbodhi.nostr.entity.Subscription;
import com.leafbodhi.nostr.handler.ISubscribeHandler;
import com.leafbodhi.nostr.handler.wrapper.EventWrapper;
import com.leafbodhi.nostr.message.EOSEMessage;
import com.leafbodhi.nostr.message.EventMessage;
import com.leafbodhi.nostr.message.OkMessage;
import com.leafbodhi.nostr.model.EventModel;
import com.leafbodhi.nostr.service.IEventService;

import jakarta.websocket.EncodeException;
import jakarta.websocket.Session;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * filter is a JSON object that determines what events will be sent in that
 * subscription, it can have the following attributes: { "ids": <a list of event
 * ids or prefixes>, "authors": <a list of pubkeys or prefixes, the pubkey of an
 * event must be one of these>, "kinds": <a list of a kind numbers>, "#e": <a
 * list of event ids that are referenced in an "e" tag>, "#p": <a list of
 * pubkeys that are referenced in a "p" tag>, "since": <an integer unix
 * timestamp, events must be newer than this to pass>, "until": <an integer unix
 * timestamp, events must be older than this to pass>, "limit": <maximum number
 * of events to be returned in the initial query> }
 * 
 * @author Jond
 *
 */
@Slf4j
@Component
public class SubscribeHandler implements ISubscribeHandler {

	@Autowired
	IEventService eventService;

	@Override
	public void handle(Session session, String subscriptionId, Filter filter,
			Map<Session, List<Subscription>> subscribers) {

		String reason = canSubscribe(session, subscriptionId, filter, subscribers);
		if (StringUtils.hasLength(reason)) {
			log.debug("subscription {} with {} rejected: {}", subscriptionId, filter, reason);
			try {
				session.getBasicRemote()
						.sendObject(new OkMessage(subscriptionId, false, "Subscription rejected:" + reason));
			} catch (IOException | EncodeException e) {
				log.error("send subscrie error", e);
			}
			return;
		}

		fetchAndSend(session, subscriptionId, filter);

	}

	private void fetchAndSend(Session session, String subscriptionId, Filter filter) {
		log.debug("fetching events for subscription {} with filters {}", subscriptionId, filter);

		try {
			List<EventModel> findEvents = eventService.findByFilters(filter);

			if (findEvents != null && findEvents.size() > 0) {

				List<Event> eventList = findEvents.stream().parallel().filter(e -> e != null).map(e -> {
					return EventWrapper.modelToObj(e);
				}).collect(Collectors.toList());

				for (Event event : eventList) {
					session.getAsyncRemote()
							.sendObject(new EventMessage(MessageType.EVENT.name(), subscriptionId, event));
				}

			} else {
				session.getBasicRemote().sendObject(new EOSEMessage(subscriptionId));
			}
		} catch (IOException | EncodeException e) {
			log.error("fetchAndSend " + subscriptionId, e);
		}

		// TODO broadcast
	}

	private String canSubscribe(Session session, String subscriptionId, Filter filter,
			Map<Session, List<Subscription>> subscribers) {
		List<Subscription> existingSubscription = subscribers.get(session);

		if (existingSubscription.size() > 0 && existingSubscription.stream().filter(s -> s.getFilter().equals(filter))
				.collect(Collectors.toList()).size() > 0) {
			return "Duplicate subscription " + subscriptionId + ": Ignorning";
		}

		subscribers.get(session).add(new Subscription(subscriptionId, filter));
		
		// TODO check limit

		return "";
	}

}
