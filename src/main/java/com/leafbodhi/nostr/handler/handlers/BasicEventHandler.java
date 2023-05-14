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
import com.leafbodhi.nostr.handler.factory.EventStrategyFactory;
import com.leafbodhi.nostr.message.OkMessage;
import com.leafbodhi.nostr.service.IEventService;

import jakarta.websocket.EncodeException;
import jakarta.websocket.Session;
import lombok.extern.slf4j.Slf4j;

/**
 * basic event: 0:set_metadata 1:text_note 2:recommend_server
 * 
 * @author jond
 *
 */
@Slf4j
@Component
public class BasicEventHandler extends AbstractEventHandler {

	@Autowired
	private IEventService eventService;
	
	@Autowired
	private NostrConfig nostrConfig;

	@Override
	public void handle(Session session, Event event, Map<Session, List<Subscription>> subscribers) {

		try {
			String reason = isEventValid(event);
			if (StringUtils.hasLength(reason)) {
				log.debug("event {} rejected: {}", event.getId(), reason);
				session.getBasicRemote().sendObject(new OkMessage(event.getId(), false, reason));
				return;
			}

			if (event.isExpiredEvent()) {
				log.debug("event {} rejected: expired", event.getId());
				session.getBasicRemote().sendObject(new OkMessage(event.getId(), false, "event is expired"));
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

			IEventStrategy eventStrategy = EventStrategyFactory.createStrategy(event, eventService);
			int count = 0;
			try {
				count = eventStrategy.excute();
			} catch (Exception e) {
				e.printStackTrace();
				log.error("error handling message", e.getLocalizedMessage());
				session.getBasicRemote()
						.sendObject(new OkMessage(event.getId(), false, "error: unable to process event"));
			}

			reason = count == 1 ? "" : "duplicate:";
			session.getBasicRemote().sendObject(new OkMessage(event.getId(), true, reason));
			
			if(count>0) {
				sendEventToSubcriber(event, subscribers);
			}

		} catch (IOException | EncodeException e) {
			log.error("handle event error", e);
		}
	}
	@Override
	public NostrConfig getConfig() {
		return nostrConfig;
	}
	
	

}
