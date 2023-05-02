package com.leafbodhi.nostr.entity;

import java.util.ArrayList;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
public class Tag extends ArrayList<String> {

	private static final long serialVersionUID = -4099140308218532142L;

	public String toJsonArrayString() {

		StringBuilder result = new StringBuilder();
		result.append("[");
		result.append("\"" + this.get(0) + "\"");
		result.append(",");
		result.append("\"" + this.get(1) + "\"");
		if (this.size() >= 3) {
			if (StringUtils.hasLength(this.get(2)) || "".equals(this.get(2))) {
				result.append(",");
				result.append("\"" + this.get(2) + "\"");
			}
		}
		if (this.size() >= 4) {
			if (StringUtils.hasLength(this.get(3)) || "".equals(this.get(3))) {
				result.append(",");
				result.append("\"" + this.get(3) + "\"");
			}
		}

		result.append("]");
		return result.toString();
	}
	public String getTagKey() {
		return this.get(0);
	}

	public String getTagValue() {
		return this.get(1);
	}

}
