package com.leafbodhi.nostr.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@Data
@JsonPropertyOrder(value = { "type", "subscriptionId", "filters" })
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
public class SubscribeEventsIn implements IncomingMessage {

	private static ObjectMapper mapper = new ObjectMapper();

	private String type;
	private String subscriptionId;
	private List<Filter> filters;

	public SubscribeEventsIn() {
	}

	public SubscribeEventsIn(String type, String subscriptionId, List<Filter> filters) {
		this.type = type;
		this.subscriptionId = subscriptionId;
		this.filters = filters;
	}

	public static SubscribeEventsIn fromJson(String json) throws JsonProcessingException {
		SubscribeEventsIn in = new SubscribeEventsIn();
		JsonNode node = mapper.readTree(json);
		in.setType(node.get(0).asText());
		in.setSubscriptionId(node.get(1).asText());
		
		List<Filter> tempFilters = new ArrayList<>();

		for (int i = 2; i < node.size(); i++) {
			Filter filter = Filter.jsonToObj(node.get(i));
			tempFilters.add(filter);
		}
		in.setFilters(tempFilters);
		return in;
	}

	@Override
	public String toString() {
		return "SubscribeEventsIn [type=" + type + ", subscriptionId=" + subscriptionId + ", filter=" + filters + "]";
	}
}
