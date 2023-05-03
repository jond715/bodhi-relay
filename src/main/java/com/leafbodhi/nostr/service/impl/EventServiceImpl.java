package com.leafbodhi.nostr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leafbodhi.nostr.entity.Event;
import com.leafbodhi.nostr.entity.Filter;
import com.leafbodhi.nostr.handler.wrapper.EventWrapper;
import com.leafbodhi.nostr.mapper.EventMapper;
import com.leafbodhi.nostr.model.EventModel;
import com.leafbodhi.nostr.service.IEventService;

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
		if (filter.getETags() != null && filter.getETags().size() > 0) {

			filterWrapper.and(wrapper -> {
				filter.getETags().stream().forEach(e -> {
					String etag = "[\"e\",\"" + e + "\"]";
					wrapper.like("tags", etag).or();
				});
			});
		}

		if (filter.getPTags() != null && filter.getPTags().size() > 0) {
			filterWrapper.and(wrapper -> {
				filter.getPTags().stream().forEach(p -> {
					String ptag = "[\"p\",\"" + p + "\"]";
					wrapper.like("tags", ptag).or();
				});
			});
		}
		
		if (filter.getRTags() != null && filter.getRTags().size() > 0) {
			filterWrapper.and(wrapper -> {
				filter.getRTags().stream().forEach(r -> {
					String rtag = "[\"r\",\"" + r + "\"]";
					wrapper.like("tags", rtag).or();
				});
			});
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
