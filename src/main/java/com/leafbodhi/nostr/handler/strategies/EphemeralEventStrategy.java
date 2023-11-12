package com.leafbodhi.nostr.handler.strategies;

import com.leafbodhi.nostr.entity.Event;
import com.leafbodhi.nostr.handler.IEventStrategy;
import com.leafbodhi.nostr.db.service.IEventService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EphemeralEventStrategy extends AbstractStrategy implements IEventStrategy {

	public EphemeralEventStrategy(Event event,IEventService eventService) {
		super(event, eventService);
	}

	@Override
	public int execute() {
		log.debug("received ephemeral event: {}", getEvent());
		//No store
		return 1;
	}

}
