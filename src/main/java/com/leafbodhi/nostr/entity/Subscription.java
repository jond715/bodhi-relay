package com.leafbodhi.nostr.entity;

import lombok.Data;

@Data
public class Subscription {

	private String subscriptionId;
	private Filter filter;

	public Subscription(String subscriptionId, Filter filter) {
		this.subscriptionId = subscriptionId;
		this.filter = filter;
	}
}
