package com.leafbodhi.nostr.handler.factory;

import com.leafbodhi.nostr.entity.Event;
import com.leafbodhi.nostr.handler.IEventStrategy;
import com.leafbodhi.nostr.handler.strategies.BasicEventStrategy;
import com.leafbodhi.nostr.handler.strategies.DeletionEventStrategy;
import com.leafbodhi.nostr.handler.strategies.EphemeralEventStrategy;
import com.leafbodhi.nostr.handler.strategies.ParameterizedReplaceableEventStrategy;
import com.leafbodhi.nostr.handler.strategies.ReplaceableEventStrategy;
import com.leafbodhi.nostr.db.service.IEventService;

public class EventStrategyFactory {
	
	public static IEventStrategy createStrategy(Event event, IEventService eventService) {
		
		if(event.isDeleteEvent()) {
			return new DeletionEventStrategy(event,eventService);
		}else if(event.isReplaceableEvent()) {
			return new ReplaceableEventStrategy(event,eventService);
		}else if(event.isEphemeralEvent()) {
			return new EphemeralEventStrategy(event,eventService);
		}else if(event.isParameterizedReplaceableEvent()) {
			return new ParameterizedReplaceableEventStrategy(event,eventService);
		}
		
		return new BasicEventStrategy(event,eventService);
	}

}
