package com.leafbodhi.nostr.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author Jond
 */
@Data
public class InvoiceStatusResponse {

    @JsonProperty("id")
    private String reference;
    @JsonProperty("status")
    private String status;

}
