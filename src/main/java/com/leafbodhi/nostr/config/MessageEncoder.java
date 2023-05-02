package com.leafbodhi.nostr.config;

import com.leafbodhi.nostr.entity.IMessage;

import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageEncoder implements Encoder.Text<IMessage> {
	
	@Override
    public String encode(IMessage message) throws EncodeException {
		log.debug("response message:" + message.toString());
        return message.toString();
    }

    @Override
    public void init(EndpointConfig config) {
        System.out.println("Encoder Init");
    }

    @Override
    public void destroy() {
        System.out.println("destroy");
    }

}
