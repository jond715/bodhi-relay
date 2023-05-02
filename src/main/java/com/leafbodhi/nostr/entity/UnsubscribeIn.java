package com.leafbodhi.nostr.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@Data
@JsonPropertyOrder(value = {"type", "subscriptionId"})
@JsonFormat(shape=JsonFormat.Shape.ARRAY)
public class UnsubscribeIn implements IncomingMessage {
    
    private static ObjectMapper mapper = new ObjectMapper();
    
    private String type;
    private String subscriptionId;

    public UnsubscribeIn() {}
    public UnsubscribeIn(String type, String subscriptionId) {
        this.type = type;
        this.subscriptionId = subscriptionId;
    }

    public static UnsubscribeIn fromJson(String json) throws JsonProcessingException  {
        return mapper.readValue(json, UnsubscribeIn.class);
    }

    @Override
    public String toString() {
        return "UnsubscribeIn [type=" + type + ", subscriptionId=" + subscriptionId + "]";
    }
}
