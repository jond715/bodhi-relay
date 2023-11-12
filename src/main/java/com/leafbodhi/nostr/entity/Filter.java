package com.leafbodhi.nostr.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@Data
public class Filter {
	/**
	 * a list of event ids or prefixes
	 */
	private List<String> ids;

	/**
	 * a list of pubkeys or prefixes, the pubkey of an event must be one of these
	 */
	private List<String> authors;

	/**
	 * a list of a kind numbers
	 */
	private List<Integer> kinds;

	/**
	 * a list of tags that are referenced in an "*" tag
	 */
	private Map<String, List<String>> tags;

	/**
	 * a timestamp, events must be newer than this to pass
	 */
	private Long since;

	/**
	 * a timestamp, events must be older than this to pass
	 */
	private Long until;

	/**
	 * maximum number of events to be returned in the initial query
	 */
	private Integer limit;

	public Filter copy() {
		var copy = new Filter();
		if (this.ids != null) {
			copy.ids = List.copyOf(this.ids);
		}
		if (this.authors != null) {
			copy.authors = List.copyOf(this.authors);
		}
		if (this.kinds != null) {
			copy.kinds = List.copyOf(this.kinds);
		}
		if (this.tags != null) {
			copy.tags = Map.copyOf(this.tags);
		}
		copy.since = this.since;
		copy.until = this.until;
		copy.limit = this.limit;

		return copy;
	}

	@Override
	public String toString() {
		return "Filter [ids=" + ids + ", authors=" + authors + ", kinds=" + kinds + ", tags=" + tags + ", since="
				+ since + ", until=" + until + ", limit=" + limit + "]";
	}

	@Override
	public boolean equals(Object obj) {
		return this.toString().equals(obj.toString());
	}

	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}

	public boolean isMatch(Event event) {
		boolean hasMatch = false;

		ids: if (ids != null && !ids.isEmpty()) {
			var eventId = event.getId();
			for (var id : ids) {
				if (id.isEmpty()) {
					continue;
				}
				if (eventId.startsWith(id)) {
					hasMatch = true;
					break ids;
				}
			}
			return false;
		}

		authors: if (authors != null && !authors.isEmpty()) {
			var eventAuthor = event.getPubkey();
			for (var author : authors) {
				if (author.isEmpty()) {
					continue;
				}
				if (eventAuthor.startsWith(author)) {
					hasMatch = true;
					break authors;
				}
			}
			return false;
		}

		kinds: if (kinds != null && !kinds.isEmpty()) {
			var eventKind = event.getKind();
			for (var kind : kinds) {
				if ( eventKind.equals(kind) ) {
					hasMatch = true;
					break kinds;
				}
			}
			return false;
		}

		// TODO tags check

		if (since != null) {
			if (event.getCreatedAt() > since) {
				return false;
			}
			hasMatch = true;
		}

		if (until != null) {
			if (event.getCreatedAt() < until) {
				return false;
			}
			hasMatch = true;
		}

		return hasMatch;
	}

	public String toJsonString() {
		// TODO
		return "[]";
	}
	
	@SuppressWarnings("unchecked")
	public static Filter jsonToObj(JsonNode node) {
		ObjectMapper mapper = new ObjectMapper();
		Filter f = new Filter();
		Iterator<String> it = node.fieldNames();
		while(it.hasNext()) {
			String key = it.next();
			if("ids".equals(key)) {
				f.setIds(mapper.convertValue(node.get(key),List.class));
			}else if("kinds".equals(key)) {
				f.setKinds(mapper.convertValue(node.get(key),List.class));
			}else if("since".equals(key)) {
				f.setSince(node.get(key).asLong());
			}else if("until".equals(key)) {
				f.setUntil(node.get(key).asLong() );
			}else if("authors".equals(key)) {
				f.setAuthors(mapper.convertValue(node.get(key),List.class));
			}else if("limit".equals(key)) {
				f.setLimit(node.get(key).asInt());
			}else if(key.startsWith("#") && key.length()>1 ) {
				Map<String, List<String>> tags = new HashMap<>();
				String keyValue = key.substring(1);
				List<String> values = new ArrayList<>();
				values = mapper.convertValue(node.get(key), List.class);
				tags.put(keyValue, values);
				f.setTags(tags);
			}
		}
		return f ;
	}
	
	public static String objToJson(Filter filter) {
		//TODO
		return null;
	}

}
