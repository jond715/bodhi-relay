package com.leafbodhi.nostr.payment;

import lombok.Data;

/**
 * @author Jond
 */
@Data
public class LNBitsCallback {
    private String checkingId;
    private Boolean pending;
    private Long amount;
    private String memo;
    private Long time;
    private String bolt11;
    private String preimage;
    private String paymentHash;
    private String walletId;
    private String webhook;
    private String webhookStatus;

    public LNBitsCallback() {
    }

    public LNBitsCallback(String checkingId, Boolean pending, Long amount, String memo, Long time, String bolt11, String preimage, String paymentHash, String walletId, String webhook, String webhookStatus) {
        this.checkingId = checkingId;
        this.pending = pending;
        this.amount = amount;
        this.memo = memo;
        this.time = time;
        this.bolt11 = bolt11;
        this.preimage = preimage;
        this.paymentHash = paymentHash;
        this.walletId = walletId;
        this.webhook = webhook;
        this.webhookStatus = webhookStatus;
    }

    @Override
    public String toString() {
        return "LNBitsCallback{" +
                "checkingId='" + checkingId + '\'' +
                ", pending=" + pending +
                ", amount=" + amount +
                ", memo='" + memo + '\'' +
                ", time=" + time +
                ", bolt11='" + bolt11 + '\'' +
                ", preimage='" + preimage + '\'' +
                ", paymentHash='" + paymentHash + '\'' +
                ", walletId='" + walletId + '\'' +
                ", webhook='" + webhook + '\'' +
                ", webhookStatus=" + webhookStatus +
                '}';
    }
}
