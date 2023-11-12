package com.leafbodhi.nostr.handler.handlers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.leafbodhi.nostr.config.NostrConfig;
import com.leafbodhi.nostr.entity.Event;
import com.leafbodhi.nostr.entity.Subscription;
import com.leafbodhi.nostr.handler.IEventStrategy;
import com.leafbodhi.nostr.handler.factory.DelegatedEventStrategyFactory;
import com.leafbodhi.nostr.message.OkMessage;
import com.leafbodhi.nostr.db.service.IEventService;

import jakarta.websocket.EncodeException;
import jakarta.websocket.Session;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DelegatedEventMessageHandler extends AbstractEventHandler {

	@Autowired
	private IEventService eventService;
	@Autowired
	private NostrConfig nostrConfig;

	@Override
	public void handle(Session session, Event event, Map<Session, List<Subscription>> subscribers) {

		try {
			String reason = isDelegatedEventValid(event);

			if (StringUtils.hasLength(reason)) {
				log.debug("event {} rejected: {}", event.getId(), reason);
				session.getBasicRemote().sendObject(new OkMessage(event.getId(), false, reason));
				return;
			}
			if (isRateLimited(event)) {
				log.debug("event {} rejected: rate-limited", event.getId());
				session.getBasicRemote().sendObject(new OkMessage(event.getId(), false, "rate-limited: slow down"));
				return;
			}
			reason = canAcceptEvent(event);
			if (StringUtils.hasLength(reason)) {
				log.debug("event {} rejected: {}", event.getId(), reason);
				session.getBasicRemote().sendObject(new OkMessage(event.getId(), false, reason));
				return;
			}

			reason = isUserAdmitted(event);
			if (StringUtils.hasLength(reason)) {
				log.debug("event {} rejected: {}", event.getId(), reason);
				session.getBasicRemote().sendObject(new OkMessage(event.getId(), false, reason));
				return;
			}

			IEventStrategy eventStrategy = DelegatedEventStrategyFactory.createStrategy(event, eventService);
			int count = 0;
			try {
				count = eventStrategy.execute();
			} catch (Exception e) {
				e.printStackTrace();
				log.error("error handling message", e.getMessage());
				session.getBasicRemote()
						.sendObject(new OkMessage(event.getId(), false, "error: unable to process event"));
			}

			reason = count == 1 ? "" : "duplicate:";
			session.getBasicRemote().sendObject(new OkMessage(event.getId(), true, reason));

			sendEventToSubcriber(event, subscribers);

		} catch (IOException | EncodeException e) {
			log.error("handle delegate event error", e);
		}
	}

	private String isDelegatedEventValid(Event event) {
		String reason = isEventValid(event);
		if (StringUtils.hasLength(reason)) {
			return reason;
		}

		if (!event.isDelegatedEventValid()) {
			return "invalid: delegation verification failed";
		}
		return "";
	}
	@Override
	public NostrConfig getConfig() {
		return nostrConfig;
	}

}
