package com.leafbodhi.nostr.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leafbodhi.nostr.entity.Tag;

import lombok.Data;

@Data
@TableName(value = "relay_event")
public class EventModel implements Serializable {

	private static final long serialVersionUID = 1L;

	public EventModel() {
	}

	public EventModel(String eventId, String pubkey, Long createdAt, Integer kind, String content, String sig) {
		this.eventId = eventId;
		this.pubkey = pubkey;
		this.createdAt = createdAt;
		this.kind = kind;
		this.content = content;
		this.sig = sig;
	}
	
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	/**
	 * 32-bytes sha256 of the the serialized event data
	 */
	@TableField("event_id")
	private String eventId;

	/**
	 * 32-bytes hex-encoded public key of the event creator
	 */
	@TableField("pubkey")
	private String pubkey;

	/**
	 * unix timestamp in seconds
	 */
	@JsonProperty("created_at")
	@TableField("created_at")
	private Long createdAt;

	/**
	 * number of the event kind
	 */
	@TableField("kind")
	private Integer kind;

	/**
	 * arbitrary string
	 */
	@TableField("content")
	private String content;

	/**
	 * 64-bytes signature of the sha256 hash of the serialized event data, which is
	 * the same as the "id" field
	 */
	@TableField("sig")
	private String sig;

	/**
	 * list of tags
	 */
	@TableField("tags")
	private String tags;
	
	@TableField("create_time")
	private Long createTime;
	
	@TableField("delegator")
	private String delegator;
	
	@TableField("deduplication")
	private String deduplication;
	
	@TableField("first_seen")
	private Long firstSeen;
	
	@TableField("deleted_at")
	private Long deletedAt;
	
	@TableField("expires_at")
	private Long expiresAt;
	
	@SuppressWarnings("unchecked")
	public List<Tag> getTagsObject() {
		ObjectMapper mapper = new ObjectMapper();
		List<Tag> tagList = new ArrayList<>();
		try {
			tagList = mapper.readValue(this.tags, ArrayList.class);
		} catch (JsonProcessingException e) { }
		return tagList;
	}
	

}
