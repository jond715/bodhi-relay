package com.leafbodhi.nostr.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author Jond
 */
@Data
public class LNBitsCreateInvoiceResponse {
    @JsonProperty("payment_hash")
    private String paymentHash;
    @JsonProperty("payment_request")
    private String paymentRequest;
    @JsonProperty("checking_id")
    private String checkingId;
    @JsonProperty("lnurl_response")
    private String lnurlResponse;

    public LNBitsCreateInvoiceResponse() {
    }

    public LNBitsCreateInvoiceResponse(String paymentHash, String paymentRequest) {
        this.paymentHash = paymentHash;
        this.paymentRequest = paymentRequest;
    }

    @Override
    public String toString() {
        return "LNBitsCreateInvoiceResponse{" +
                "paymentHash='" + paymentHash + '\'' +
                ", paymentRequest='" + paymentRequest + '\'' +
                '}';
    }
}
