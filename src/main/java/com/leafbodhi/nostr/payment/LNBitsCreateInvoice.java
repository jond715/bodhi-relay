package com.leafbodhi.nostr.payment;

import lombok.Data;

/**
 * @author Jond
 */
@Data
public class LNBitsCreateInvoice {
    private Boolean out;
    private Long amount;
    private String memo;
    private String webhook;
    private String unit;
    private Boolean internal;
    private Long expiry;

    public LNBitsCreateInvoice() {
    }

    public LNBitsCreateInvoice(Boolean out, Long amount, String memo, String webhook, String unit, Boolean internal, Long expiry) {
        this.out = out;
        this.amount = amount;
        this.memo = memo;
        this.webhook = webhook;
        this.unit = unit;
        this.internal = internal;
        this.expiry = expiry;
    }
    @Override
    public String toString() {
        return "LNBitsCreateInvoice{" +
                "out=" + out +
                ", amount=" + amount +
                ", memo='" + memo + '\'' +
                ", webhook='" + webhook + '\'' +
                ", unit='" + unit + '\'' +
                ", internal=" + internal +
                ", expiry=" + expiry +
                '}';
    }
}
