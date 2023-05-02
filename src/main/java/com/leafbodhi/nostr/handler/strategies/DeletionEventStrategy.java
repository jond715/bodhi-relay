package com.leafbodhi.nostr.handler.strategies;

import java.util.ArrayList;
import java.util.List;

import com.leafbodhi.nostr.entity.Event;
import com.leafbodhi.nostr.handler.IEventStrategy;
import com.leafbodhi.nostr.service.IEventService;

public class DeletionEventStrategy extends AbstractStrategy implements IEventStrategy {

	public DeletionEventStrategy(Event event,IEventService eventService) {
		super(event,eventService);
	}

	@Override
	public int excute() {
		List<String> eventIdsToDelete =  new ArrayList<>();
		getEvent().getTags().stream().forEach(e->{
			eventIdsToDelete.add(e.getTagValue());
		});
		return getEventService().deleteByPubkeyAndIds(getEvent().getPubkey(),eventIdsToDelete);
	}

}
