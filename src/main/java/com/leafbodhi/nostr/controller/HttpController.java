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
	@Autowired
	private ServerInfo serverInfo;
	
	private static ObjectMapper OBJECTMAPPER = new ObjectMapper();

	private final String DEFAULT_ACCEPT = "application/nostr+json";

	@GetMapping("/")
	public String index() throws IOException {
		String reqAccept = req.getHeader("Accept");
		System.out.println("Accept:" + reqAccept);

		if (DEFAULT_ACCEPT.equals(reqAccept)) {
			resp.setHeader("Access-Control-Allow-Origin", "*");
			resp.setHeader("Access-Control-Allow-Headers", "*");
			resp.setHeader("Access-Control-Allow-Methods", "GET, POST");

			String response = "";

			try {
				response = OBJECTMAPPER.writeValueAsString(serverInfo);
			} catch (JsonProcessingException e) {
				log.warn("Error during response serialization.", e);
				response = "Nostr reley error.";
			}

			return response;
		} else {
			return "Please use a Nostr client to connect.";
		}
	}

}
