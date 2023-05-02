package com.leafbodhi.nostr.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@Data
@JsonPropertyOrder(value = {"type", "subscriptionId", "filter"})
@JsonFormat(shape=JsonFormat.Shape.ARRAY)
public class SubscribeEventsIn implements IncomingMessage {

    private static ObjectMapper mapper = new ObjectMapper();

    private String type;
    private String subscriptionId;
    private Filter filter;

    public SubscribeEventsIn() {}
    public SubscribeEventsIn(String type, String subscriptionId, Filter filter) {
        this.type = type;
        this.subscriptionId = subscriptionId;
        this.filter = filter;
    }

    public static SubscribeEventsIn fromJson(String json) throws JsonProcessingException  {
        return mapper.readValue(json, SubscribeEventsIn.class);
    }

    @Override
    public String toString() {
        return "SubscribeEventsIn [type=" + type + ", subscriptionId=" + subscriptionId + ", filter=" + filter + "]";
    }
}
