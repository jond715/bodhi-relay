package com.leafbodhi.nostr.entity;

import java.util.List;

import lombok.Data;

@Data
public class Subscription {

	private String subscriptionId;
	private List<Filter> filters;

	public Subscription(String subscriptionId, List<Filter> filters) {
		this.subscriptionId = subscriptionId;
		this.filters = filters;
	}
}
