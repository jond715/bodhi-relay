package com.leafbodhi.nostr.handler.strategies;

import com.leafbodhi.nostr.entity.Event;
import com.leafbodhi.nostr.handler.IEventStrategy;
import com.leafbodhi.nostr.db.service.IEventService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReplaceableEventStrategy extends AbstractStrategy implements IEventStrategy {

	public ReplaceableEventStrategy(Event event, IEventService eventService) {
		super(event, eventService);
	}

	@Override
	public int execute() {
		log.debug("received replaceable event: {}", this.getEvent());
		return getEventService().saveOrUpdate(this.getEvent());
	}

}
