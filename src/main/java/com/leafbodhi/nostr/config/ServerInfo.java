package com.leafbodhi.nostr.config;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ServerInfo {

	@JsonProperty
	private String name = "bodhi-relay";
	@JsonProperty
	private String description = "A Nostr Relay written in Java";
	@JsonProperty
	private String pubkey = "";
	@JsonProperty
	private String contact = "lzq715@gmail.com";
	@JsonProperty("supported_nips")
	private final List<Integer> supportedNips = Arrays.asList(1, 2, 4, 9, 11, 15, 16, 20, 25, 26, 28, 33, 40);
	@JsonProperty
	private final String software = "https://github.com/jond715/bodhi-relay";
	@JsonProperty
	private final String version = "0.8.1";
	

	@Override
	public String toString() {
		return "Server{" + "name='" + name + '\'' + ", description='" + description + '\'' + ", pubkey='" + pubkey
				+ '\'' + ", contact='" + contact + '\'' + '}';
	}

}
