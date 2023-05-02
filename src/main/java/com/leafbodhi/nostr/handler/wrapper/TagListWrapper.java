package com.leafbodhi.nostr.handler.wrapper;

import java.util.List;
import java.util.stream.Collectors;

import com.leafbodhi.nostr.entity.Tag;

public class TagListWrapper {

	private List<Tag> tagList;

	public TagListWrapper(List<Tag> tagList) {
		this.tagList = tagList;
	}

	public String marshall() {

		StringBuilder result = new StringBuilder();

		result.append("[");
		if (!tagList.isEmpty()) {
			result.append(tagList.stream().filter(t -> t != null).map(t -> {
				return t.toJsonArrayString();
			}).collect(Collectors.joining(",")));
		}
		result.append("]");

		return result.toString();
	}
}
