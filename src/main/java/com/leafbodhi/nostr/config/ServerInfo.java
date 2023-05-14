package com.leafbodhi.nostr.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "nostr.info")
public class ServerInfo {

	@JsonProperty
	private String name = "bodhi-relay";
	@JsonProperty
	private String description = "A Nostr Relay written in Java";
	@JsonProperty("supported_nips")
	private List<Integer> supportedNips = Arrays.asList(1, 2, 4, 9, 11, 12, 15, 16, 20, 22, 25, 26, 28, 33, 40);
	// TODO nips 42,111 (other:3,5,13,14,19,38,45,5,65)
	
	@JsonProperty
	private String software = "https://github.com/jond715/bodhi-relay";
	@JsonProperty
	private String version = "0.8.4";

	@JsonProperty
	private String pubkey;
	@JsonProperty
	private String contact;
	
	@Override
	public String toString() {
		return "Server{" + "name='" + name + '\'' + ", description='" + description + '\'' + ", pubkey='" + pubkey
				+ '\'' + ", contact='" + contact + '\'' + '}';
	}

}
