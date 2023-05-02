package com.leafbodhi.nostr.handler.factory;

import com.leafbodhi.nostr.entity.Event;
import com.leafbodhi.nostr.handler.IEventStrategy;
import com.leafbodhi.nostr.handler.strategies.BasicEventStrategy;
import com.leafbodhi.nostr.handler.strategies.EphemeralEventStrategy;
import com.leafbodhi.nostr.service.IEventService;

public class DelegatedEventStrategyFactory {

	public static IEventStrategy createStrategy(Event event, IEventService eventService) {

		if (event.isEphemeralEvent()) {
			return new EphemeralEventStrategy(event, eventService);
		} else if (event.isReplaceableEvent() || event.isDeleteEvent()) {
			return null;
		}

		return new BasicEventStrategy(event, eventService);
	}

}
