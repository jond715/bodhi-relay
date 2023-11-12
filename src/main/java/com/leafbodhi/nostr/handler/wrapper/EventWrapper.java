package com.leafbodhi.nostr.handler.wrapper;

import com.leafbodhi.nostr.entity.Event;
import com.leafbodhi.nostr.db.model.EventModel;

public class EventWrapper {

	public static Event modelToObj(EventModel model) {
		Event event = new Event();
		event.setId(model.getEventId());
		event.setKind(model.getKind());
		event.setPubkey(model.getPubkey());
		event.setCreatedAt(model.getCreatedAt());
		event.setContent(model.getContent());
		event.setTags(model.getTagsObject());
		event.setSig(model.getSig());
		return event;
	}
	
	public static EventModel objToModel(Event event) {
		EventModel model = new EventModel();
		model.setEventId(event.getId());
		model.setKind(event.getKind());
		model.setPubkey(event.getPubkey());
		model.setCreatedAt(event.getCreatedAt());
		model.setContent(event.getContent());
		model.setTags(new TagListWrapper(event.getTags()).marshall());
		model.setSig(event.getSig());
		model.setExpiresAt(event.getExpirationAt());
		model.setCreateTime(System.currentTimeMillis());
		return model;
	}

}
