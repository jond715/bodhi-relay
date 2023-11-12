package com.leafbodhi.nostr.entity;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.io.JsonStringEncoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leafbodhi.nostr.crypto.Schnorr;
import com.leafbodhi.nostr.handler.wrapper.TagListWrapper;
import com.leafbodhi.nostr.utils.NostrUtil;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class Event {

	private final static ObjectMapper MAPPER = new ObjectMapper();

	public Event() {
	}

	public Event(String id, String pubkey, long createdAt, int kind, String content, String sig) {
		this.id = id;
		this.pubkey = pubkey;
		this.createdAt = createdAt;
		this.kind = kind;
		this.content = content;
		this.sig = sig;
	}

	/**
	 * 32-bytes sha256 of the the serialized event data
	 */
	private String id;

	/**
	 * 32-bytes hex-encoded public key of the event creator
	 */
	private String pubkey;

	/**
	 * unix timestamp in seconds
	 */
	@JsonProperty("created_at")
	private Long createdAt;

	/**
	 * number of the event kind
	 */
	private Integer kind;

	/**
	 * arbitrary string
	 */
	private String content;

	/**
	 * 64-bytes signature of the sha256 hash of the serialized event data, which is
	 * the same as the "id" field
	 */
	private String sig;

	/**
	 * list of tags
	 */
	private List<Tag> tags = new ArrayList<>();

	private String serialize() {
		var sb = new StringBuilder();
		sb.append("[");
		sb.append("0").append(",\"");
		sb.append(this.pubkey).append("\",");
		sb.append(this.createdAt).append(",");
		sb.append(this.kind).append(",");

		sb.append(new TagListWrapper(tags).marshall());
		sb.append(",\"");
		sb.append(JsonStringEncoder.getInstance().quoteAsString(this.content));
		sb.append("\"]");

		return sb.toString();
	}

	@JsonIgnore
	public byte[] getSerializeEvent() {
		return this.serialize().getBytes(StandardCharsets.UTF_8);
	}

	public String toJson() {

		try {
			return MAPPER.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			log.error("event to json error", e);
			return "{}";
		}
	}

	@JsonIgnore
	private String getEventHash() {
		try {
			return NostrUtil.bytesToHex(NostrUtil.sha256(getSerializeEvent()));
		} catch (NoSuchAlgorithmException e) {
			log.error("hash event error", e);
			return "";
		}
	}

	@JsonIgnore
	public boolean isEventIdValid() {
		return getId().equals(getEventHash());

	}

	@JsonIgnore
	public boolean isEventSignatureValid() {
		try {
			System.out.println(serialize());
			return Schnorr.verify(NostrUtil.sha256(getSerializeEvent()), NostrUtil.hexToBytes(getPubkey()),
					NostrUtil.hexToBytes(getSig()));
		} catch (Exception e) {
			log.error("sign verify error", e);
			return false;
		}
	}

	@JsonIgnore
	public boolean isExpiredEvent() {
		if (this.tags.size() > 0) {
			return false;
		}
		Long expirationTime = this.getExpirationAt();

		if (expirationTime == 0) {
			return false;
		}
		return expirationTime.longValue() <= System.currentTimeMillis() / 1000;
	}

	@JsonIgnore
	public boolean isReplaceableEvent() {
		return this.getKind() == Kinds.SET_METADATA || this.getKind() == Kinds.CONTACT_LIST
				|| this.getKind() == Kinds.CHANNEL_METADATA
				|| (this.getKind() >= Kinds.REPLACEABLE_FIRST && this.getKind() <= Kinds.REPLACEABLE_LAST);
	}

	@JsonIgnore
	public boolean isEphemeralEvent() {
		return this.getKind() >= Kinds.EPHEMERAL_FIRST && this.getKind() <= Kinds.EPHEMERAL_LAST;
	}

	@JsonIgnore
	public boolean isDeleteEvent() {
		return this.getKind() == Kinds.DELETION;
	}

	@JsonIgnore
	public boolean isParameterizedReplaceableEvent() {
		return this.getKind() >= Kinds.PARAMETERIZED_REPLACEABLE_FIRST
				&& this.getKind() <= Kinds.PARAMETERIZED_REPLACEABLE_LAST;
	}

	@JsonIgnore
	public boolean isDelegatedEventValid() {
		boolean result = this.tags.stream()
				.anyMatch(tag -> tag.size() == 4 && tag.getTagKey().equals(EventTags.Delegation));

		if (!result) {
			return false;
		}
		// TODO verify first delegation event
		Tag detegationTag = this.tags.get(0);
		String serializedDelegationTag = "nostr:" + detegationTag.get(0) + ":" + getPubkey() + ":"
				+ detegationTag.get(2);

		try {
			byte[] token = NostrUtil.sha256(serializedDelegationTag.getBytes());
			result = Schnorr.verify(detegationTag.get(3).getBytes(), token, detegationTag.get(1).getBytes());
		} catch (Exception e) {
			log.error("verify delegation event error", e);
			result = false;
		}

		return result;
	}

	@JsonIgnore
	public Long getExpirationAt() {
		Optional<Tag> opt = tags.stream().filter(t -> t.size() >= 2 && t.get(0).equals(EventTags.Expiration))
				.findFirst();
		if (opt.isPresent()) {
			return Long.valueOf(opt.get().get(1));
		} else {
			return 0L;
		}
	}

}
