package com.leafbodhi.nostr.payment;

/**
 * @author Jond
 */
public enum InvoiceUnit {
    MSATS("msats"),
    SATS("sats"),
    BTC("btc");

    private final String value;

    private InvoiceUnit(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
