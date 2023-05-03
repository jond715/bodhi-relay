package com.leafbodhi.nostr.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.leafbodhi.nostr.config.ServerInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class HttpController {
	@Autowired
	private HttpServletRequest req;
	@Autowired
	private HttpServletResponse resp;

	private final String DEFAULT_ACCEPT = "application/nostr+json";

	private static final String DEFAULT_RESPONSE = """
			        {
			            "name": "bodhi-relay",
			            "description": "A Nostr Relay written in Java",
			            "pubkey": "45bd25c8648da487c573144f481db102ca23fd502e0b503ec90eb7ba451e327b",
			            "contact": "lzq715@gmail.com",
			            "supported_nips": [1, 11],
			            "software": "https://github.com/jond715/bodhi-relay",
			            "version": "0.8.1"
			        }
			""";

	@GetMapping("/")
	public String index() throws IOException {
		String reqAccept = req.getHeader("Accept");
		System.out.println("Accept:" + reqAccept);

		if (DEFAULT_ACCEPT.equals(reqAccept)) {
			resp.setHeader("Access-Control-Allow-Origin", "*");
			resp.setHeader("Access-Control-Allow-Headers", "*");
			resp.setHeader("Access-Control-Allow-Methods", "GET, POST");

			String response = "";

			var objectMapper = new ObjectMapper();
			try {
				response = objectMapper.writeValueAsString(ServerInfo.getIntance());
			} catch (JsonProcessingException e) {
				log.warn("Error during response serialization.", e);
				log.warn("Using default NIP-11 response.");
				response = DEFAULT_RESPONSE;
			}

			return response;
		} else {
			return "Please use a Nostr client to connect.";
		}
	}

}
