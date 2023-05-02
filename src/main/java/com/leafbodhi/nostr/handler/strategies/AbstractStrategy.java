package com.leafbodhi.nostr.handler.strategies;

import com.leafbodhi.nostr.entity.Event;
import com.leafbodhi.nostr.handler.IEventStrategy;
import com.leafbodhi.nostr.service.IEventService;

import lombok.Data;

@Data
public abstract class AbstractStrategy implements IEventStrategy {
	
	private Event event;
	private IEventService eventService;
	
	public AbstractStrategy(Event event,IEventService eventService) {
		this.event = event;
		this.eventService = eventService;
	}

	@Override
	public int excute() {
		return 0;
	}

}
