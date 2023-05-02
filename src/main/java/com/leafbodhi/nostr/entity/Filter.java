package com.leafbodhi.nostr.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;

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
     * a list of event ids that are referenced in an "e" tag
     */
    @JsonProperty("#e")
    private List<String> eTags;

    /**
     * a list of pubkeys that are referenced in a "p" tag
     */
    @JsonProperty("#p")
    private List<String> pTags;

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

    public List<String> getAllTagsWithKey() {
        var e = eTags.stream().map(t -> "e#" + t).collect(Collectors.toList());
        var p = pTags.stream().map(t -> "p#" + t).collect(Collectors.toList());
        var allTags = new ArrayList<String>();
        allTags.addAll(e);
        allTags.addAll(p);
        return allTags;
    }

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
        if (this.eTags != null) {
            copy.eTags = List.copyOf(this.eTags);
        }
        if (this.pTags != null) {
            copy.pTags = List.copyOf(this.pTags);
        }
        copy.since = this.since;
        copy.until = this.until;
        copy.limit = this.limit;

        return copy;
    }

    @Override
    public String toString() {
        return "Filter [ids=" + ids + ", authors=" + authors + ", kinds=" + kinds + ", eTags=" + eTags + ", pTags="
                + pTags + ", since=" + since + ", until=" + until + ", limit=" + limit + "]";
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

        ids:
        if (ids != null && !ids.isEmpty()) {
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

        authors:
        if (authors != null && !authors.isEmpty()) {
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

        kinds:
        if (kinds != null && !kinds.isEmpty()) {
            var eventKind = event.getKind();
            for (var kind : kinds) {
                if (eventKind == kind) {
                    hasMatch = true;
                    break kinds;
                }
            }
            return false;
        }
        
        //TODO etags check

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
    	//TODO
    	return "[]";
    }
    
}
