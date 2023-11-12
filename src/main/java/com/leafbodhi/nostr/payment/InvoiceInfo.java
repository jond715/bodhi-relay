package com.leafbodhi.nostr.payment;

import lombok.Data;

/**
 * @author Jond
 */
@Data
public class InvoiceInfo {

    private String pubkey;
    private String paymentHash;
    private String bolt11;
    private long amount;
    private InvoiceStatus status;
    private String memo;
    private Long confirmedAt;
    private Long createdAt;
    private Long expiredAt;


    public InvoiceInfo() {
    }

    public InvoiceInfo(String pubkey, String paymentHash, String bolt11, long amount, InvoiceStatus status, String memo, Long confirmedAt) {
        this.pubkey = pubkey;
        this.paymentHash = paymentHash;
        this.bolt11 = bolt11;
        this.amount = amount;
        this.status = status;
        this.memo = memo;
        this.confirmedAt = confirmedAt;
    }

    @Override
    public String toString() {
        return "InvoiceInfo{" +
                "pubkey='" + pubkey + '\'' +
                ", paymentHash='" + paymentHash + '\'' +
                ", bolt11='" + bolt11 + '\'' +
                ", amount=" + amount +
                ", status=" + status +
                ", memo='" + memo + '\'' +
                ", confirmedAt=" + confirmedAt +
                '}';
    }
}
