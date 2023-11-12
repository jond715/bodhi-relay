package com.leafbodhi.nostr.handler.strategies;

import com.leafbodhi.nostr.entity.Event;
import com.leafbodhi.nostr.handler.IEventStrategy;
import com.leafbodhi.nostr.db.service.IEventService;

public class BasicEventStrategy extends AbstractStrategy implements IEventStrategy {

	public BasicEventStrategy(Event event,IEventService eventService) {
		super(event,eventService);
	}

	@Override
	public int execute() {
		return getEventService().save(this.getEvent());
	}

}
