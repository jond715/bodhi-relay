package com.leafbodhi.nostr.config;

import java.util.List;

import com.leafbodhi.nostr.entity.MessageType;

/**
 * TODO setting info
 * @author Jond
 *
 */
public class NostrConfig {
	
	Network network;
	Worker workers;
	Limits limits;
	
	private NostrConfig() {
		//TODO 
	}
	
	public NostrConfig createConfig() {
		//TODO initialize
		return new NostrConfig();
	}
	
	class Network {
		int maxPayloadSize;
		String remoteIpHeader;
	}
	class Worker{
		int count;
	}
	class Limits{
		ConnectionLimits connectionLimits;
		ClientLimits clientLimits;
		EventLimits eventLimits;
		MessageLimits messageLimits;
	}
	
	class ConnectionLimits{
		List<RateLimit> rateLimits;
		List<String> ipWhitelist;
		List<String> ipBlacklist;
	}
	
	class ClientLimits{
		ClientSubscriptionLimits subscription;
	}
	class EventLimits{
		
	}
	class MessageLimits{
		List<MessageRateLimit> rateLimits;
		List<String> ipWhitelist;
	}
	class RateLimit{
		String description;
		int period;
		int rate;
	}
	class ClientSubscriptionLimits{
		int maxSubscriptions;
		int maxFilters;
	}
	class MessageRateLimit extends RateLimit{
		MessageType type;
	}
	
}
