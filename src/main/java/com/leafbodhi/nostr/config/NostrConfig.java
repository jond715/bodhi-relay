package com.leafbodhi.nostr.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.leafbodhi.nostr.entity.MessageType;

import lombok.Data;

/**
 * setting info
 * 
 * @author Jond
 *
 */
@Data
@Component
@ConfigurationProperties(prefix = "nostr.config")
public class NostrConfig {

	public Network network;
	public Worker workers;
	public Limits limits;

	public NostrConfig() {
		// TODO load config file
		limits = new Limits();
	}

	public class Network {
		public int maxPayloadSize;
		public String remoteIpHeader;
	}

	public class Worker {
		public int count;
	}
	@Data
	public class Limits {
		public ConnectionLimits connectionLimits;
		public ClientLimits clientLimits;
		public EventLimits eventLimits = new EventLimits();
		public MessageLimits messageLimits;
	}

	public class ConnectionLimits {
		public List<RateLimit> rateLimits;
		public List<String> ipWhitelist;
		public List<String> ipBlacklist;
	}

	public class ClientLimits {
		public ClientSubscriptionLimits subscription;
	}
	
	@Data
	public class EventLimits {
		public CreatedAtLimits createdAtLimits = new CreatedAtLimits();
	}
	
	@Data
	public class CreatedAtLimits {
		/**
		 * Maximum number of seconds allowed before the current unix timestamp
		 * default lower limit as 1 day into the past
		 */
		long maxNegativeDelta = 60 * 60 *24;
		/**
		 * Maximum number of seconds allowed after the current unix timestamp
		 * default upper limit as 15 minutes into the future
		 */
		long maxPositiveDelta = 60 * 15;
	}

	public class MessageLimits {
		List<MessageRateLimit> rateLimits;
		List<String> ipWhitelist;
	}

	public class RateLimit {
		String description;
		int period;
		int rate;
	}

	public class ClientSubscriptionLimits {
		int maxSubscriptions;
		int maxFilters;
	}

	public class MessageRateLimit extends RateLimit {
		MessageType type;
	}

}
