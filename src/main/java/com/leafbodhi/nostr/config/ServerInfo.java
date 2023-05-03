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
	private String pubkey = "45bd25c8648da487c573144f481db102ca23fd502e0b503ec90eb7ba451e327b";
	@JsonProperty
	private String contact = "lzq715@gmail.com";
	@JsonProperty("supported_nips")
	private final List<Integer> supportedNips = Arrays.asList(1, 2, 4, 9, 11, 12, 15, 16, 20, 22, 25, 26, 28, 33, 40);
	// TODO nips 42,111 (other:3,5,13,14,19,38,45,5,65)

	@JsonProperty
	private final String software = "https://github.com/jond715/bodhi-relay";
	@JsonProperty
	private final String version = "0.8.3";

	private static ServerInfo SERVER_INFO = new ServerInfo();

	public static ServerInfo getIntance() {
		return SERVER_INFO;
	}

	private ServerInfo() {
	}

	@Override
	public String toString() {
		return "Server{" + "name='" + name + '\'' + ", description='" + description + '\'' + ", pubkey='" + pubkey
				+ '\'' + ", contact='" + contact + '\'' + '}';
	}

}
