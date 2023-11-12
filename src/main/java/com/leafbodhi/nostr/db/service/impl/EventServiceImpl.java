package com.leafbodhi.nostr.db.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leafbodhi.nostr.entity.Event;
import com.leafbodhi.nostr.entity.EventTags;
import com.leafbodhi.nostr.entity.Filter;
import com.leafbodhi.nostr.handler.wrapper.EventWrapper;
import com.leafbodhi.nostr.db.mapper.EventMapper;
import com.leafbodhi.nostr.db.model.EventModel;
import com.leafbodhi.nostr.db.service.IEventService;

@Component
public class EventServiceImpl implements IEventService {

	@Autowired
	private EventMapper eventMapper;

	@Override
	public Integer save(Event event) {

		EventModel model = EventWrapper.objToModel(event);

		var queryWrapper = new QueryWrapper<EventModel>();
		queryWrapper.eq("event_id", model.getEventId());

		int result = saveEventAndTag(event, model, queryWrapper);

		return result;
	}

	@Override
	public Integer removeById(Integer id) {
		return eventMapper.deleteById(id);
	}

	@Override
	public EventModel getById(Integer id) {
		return eventMapper.selectById(id);
	}

	@Override
	public List<EventModel> list(Wrapper<EventModel> queryWrapper) {
		return eventMapper.selectList(queryWrapper);
	}

	@Override
	public IPage<EventModel> page(Integer current, Integer size) {
		Page<EventModel> p = new Page<>();
		p.setCurrent(current);
		p.setSize(size);
		return eventMapper.selectPage(p, new QueryWrapper<>());
	}

	@Override
	public Integer updateById(EventModel event) {
		return eventMapper.updateById(event);
	}

	@Override
	public Integer saveOrUpdate(Event event) {
		EventModel model = EventWrapper.objToModel(event);

		var queryWrapper = new QueryWrapper<EventModel>();
		queryWrapper.eq("pubkey", event.getPubkey()).eq("kind", event.getKind());

		int result = saveEventAndTag(event, model, queryWrapper);

		return result;
	}

	private int saveEventAndTag(Event event, EventModel model, QueryWrapper<EventModel> queryWrapper) {
		EventModel tempEvent = eventMapper.selectOne(queryWrapper);
		int result = 0;
		if (tempEvent == null) {
			result = eventMapper.insert(model);
		} else {
			model.setId(tempEvent.getId());
			result = eventMapper.updateById(model);
		}
		return result;
	}

	@Override
	public Integer removeByEvent(EventModel event) {
		return eventMapper.deleteById(event);
	}

	@Override
	public List<EventModel> findByFilters(Filter filter) {

		QueryWrapper<EventModel> filterWrapper = new QueryWrapper<EventModel>();
		if (filter.getIds() != null && filter.getIds().size() > 0) {
			filterWrapper.in("event_id", filter.getIds());
		}
		if (filter.getAuthors() != null && filter.getAuthors().size() > 0) {
			filterWrapper.in("pubkey", filter.getAuthors());
		}
		if (filter.getKinds() != null && filter.getKinds().size() > 0) {
			filterWrapper.in("kind", filter.getKinds());
		}
		if (filter.getSince() != null && filter.getSince() > 0) {
			filterWrapper.ge("created_at", filter.getSince());
		}
		if (filter.getUntil() != null && filter.getUntil() > 0) {
			filterWrapper.le("created_at", filter.getUntil());
		}

		if (filter.getTags() != null && filter.getTags().size() > 0) {
			for (String tagKey : filter.getTags().keySet()) {
				if (EventTags.Event.equals(tagKey) || EventTags.Pubkey.equals(tagKey)
						|| EventTags.Reference.equals(tagKey) || EventTags.Deduplication.equals(tagKey)) {
					List<String> tagValues = filter.getTags().get(tagKey);
					filterWrapper.and(wrapper -> {
						tagValues.stream().forEach(t -> {
							String tagStr = "[\"" + tagKey + "\",\"" + t + "\"]";
							wrapper.like("tags", tagStr).or();
						});
					});
				} else {
					// TODO
				}
			}
		}

		if (filter.getLimit() != null && filter.getLimit() > 0) {
			filterWrapper.last("limit " + filter.getLimit());
		} else {
			filterWrapper.last("limit 500");
		}

		List<EventModel> result = eventMapper.selectList(filterWrapper);

		return result;
	}

	@Override
	public int deleteByPubkeyAndIds(String pubkey, List<String> eventIdsToDelete) {
		QueryWrapper<EventModel> eventWrapper = new QueryWrapper<>();
		eventWrapper.eq("pubkey", pubkey);
		eventWrapper.in("event_id", eventIdsToDelete);

		EventModel model = new EventModel();
		model.setDeletedAt(System.currentTimeMillis());

		int result = eventMapper.update(model, eventWrapper);

		return result;
	}

}
