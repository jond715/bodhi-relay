package com.leafbodhi.nostr;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.leafbodhi.nostr.handler.handlers.BasicEventHandler;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestBasicEventHandler {
	
	@Autowired
	BasicEventHandler basicEventHandler;

	@Test
	void testEventRequest() {
		//TODO mock session
	}

}
