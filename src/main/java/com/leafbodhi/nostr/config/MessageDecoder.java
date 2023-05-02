package com.leafbodhi.nostr.config;

import com.leafbodhi.nostr.entity.IMessage;

import jakarta.websocket.DecodeException;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class MessageDecoder implements Decoder.Text<IMessage> {

	@Override
	public void init(EndpointConfig config) {
		System.out.println("Decoding incoming message...");
	}

	@Override
	public void destroy() {
		System.out.println("Incoming message decoding finished");
	}

	@Override
	public IMessage decode(String s) throws DecodeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean willDecode(String s) {
		// TODO Auto-generated method stub
		return false;
	}
}
