package com.leafbodhi.nostr.handler.handlers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.leafbodhi.nostr.config.NostrConfig.CreatedAtLimits;
import com.leafbodhi.nostr.entity.Event;
import com.leafbodhi.nostr.entity.MessageType;
import com.leafbodhi.nostr.entity.Subscription;
import com.leafbodhi.nostr.handler.IMessageHandler;
import com.leafbodhi.nostr.message.EventMessage;

import jakarta.websocket.EncodeException;
import jakarta.websocket.Session;

/**
 * basic event: 0:set_metadata 1:text_note 2:recommend_server
 * 
 * @author jond
 *
 */
public abstract class AbstractEventHandler implements IMessageHandler {

	/**
	 * broadcast event to subscribes
	 * 
	 * @throws EncodeException
	 * @throws IOException
	 */
	protected void sendEventToSubcriber(Event event, Map<Session, List<Subscription>> subscribers)
			throws IOException, EncodeException {

		for (Session session : subscribers.keySet()) {
			var subscriptions = subscribers.get(session);
			subscriptions.parallelStream().forEach(sub -> {
				var filters = sub.getFilters();

				filters.parallelStream().forEach(filter -> {
					if (filter.isMatch(event)) {
						session.getAsyncRemote()
								.sendObject(new EventMessage(MessageType.EVENT.name(), sub.getSubscriptionId(), event));
					}
				});
			});
		}
	}

	/**
	 * True return empty string False return reason
	 * 
	 * @param event
	 * @return
	 */
	protected String isEventValid(Event event) {
		if (!event.isEventIdValid()) {
			return "invalid: event id does not match";
		}
		if (!event.isEventSignatureValid()) {
			return "invalid: event signature verification failed";
		}
		return "";

	}

	protected boolean isRateLimited(Event event) {
		// TODO
		return false;
	}

	protected String canAcceptEvent(Event event) {
		// TODO Event created_at Limits
		CreatedAtLimits createdAtLimits = getConfig().getLimits().getEventLimits().getCreatedAtLimits();
		long now = System.currentTimeMillis() / 1000;
		if (createdAtLimits.getMaxPositiveDelta() > 0
				&& event.getCreatedAt().longValue() > now + createdAtLimits.getMaxPositiveDelta()) {
			return "rejected: created_at is more than " + createdAtLimits.getMaxPositiveDelta()
					+ " seconds in the future";
		}
		if (createdAtLimits.getMaxNegativeDelta() > 0
				&& event.getCreatedAt().longValue() < now - createdAtLimits.getMaxNegativeDelta()) {

			return "rejected: created_at is more than " + createdAtLimits.getMaxNegativeDelta()
					+ " seconds in the past";
		}

		// TODO blacklist whitelist limit
		return "";
	}

	protected String isUserAdmitted(Event event) {
		// TODO fee whitelists
		return "";
	}

}
