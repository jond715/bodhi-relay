package com.leafbodhi.nostr.payment;

import lombok.Data;

/**
 * @author Jond
 */
@Data
public class LNBitsCheckInvoiceResponse {
    private Boolean paid;

    public LNBitsCheckInvoiceResponse() {
    }

    public LNBitsCheckInvoiceResponse(Boolean paid) {
        this.paid = paid;
    }

    @Override
    public String toString() {
        return "LNBitsCheckInvoiceResponse{" +
                "paid=" + paid +
                '}';
    }
}
