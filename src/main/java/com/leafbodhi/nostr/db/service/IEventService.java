package com.leafbodhi.nostr.db.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.leafbodhi.nostr.entity.Event;
import com.leafbodhi.nostr.entity.Filter;
import com.leafbodhi.nostr.db.model.EventModel;

import java.util.List;

/**
 * @author jond
 */
public interface IEventService {
	
    Integer saveOrUpdate(Event event);

    Integer save(Event event);

    /**
     * 通过 主键id 删除
     * @param id 
     * @return 成功标志，新增成功返回值为 1
     */
    Integer removeById(Integer id);

    /**
     * 通过主键 id 查询一条记录
     * @param id
     * @return event信息
     */
    EventModel getById(Integer id);

    /**
     * 通过 条件 查询符合的Event记录
     * @param queryWrapper 查询条件
     * @return List<User> 用户列表
     */
    List<EventModel> list(Wrapper<EventModel> queryWrapper);

    /**
     * 分页查询记录
     * @param current 当前页数
     * @param size 每页展示数目
     * @return IPage<User> event列表
     */
    IPage<EventModel> page(Integer current, Integer size);

    /**
     * @return 成功标志，新增成功返回值为 1
     */
    Integer updateById(EventModel event);
    
    /**
     * 通过 主键id 删除, 并且删除对应的Tags
     * @param event
     * @return 成功标志，新增成功返回值为 1
     */
    Integer removeByEvent(EventModel event);
    
    List<EventModel> findByFilters(Filter filter);
    
    int deleteByPubkeyAndIds(String pubkey, List<String> eventIdsToDelete);
}
