package com.leafbodhi.nostr.payment;

import com.leafbodhi.nostr.db.model.InvoiceModel;

import java.time.Instant;

/**
 * @author Jond
 */
public class InvoiceWrapper {

    public static InvoiceModel objToModel(InvoiceInfo info){
        InvoiceModel model = new InvoiceModel();
        model.setPubkey(info.getPubkey());
        model.setAmount(info.getAmount());
        model.setMemo(info.getMemo());
        model.setBolt11(info.getBolt11());
        model.setPaymentHash(info.getPaymentHash());
        model.setStatus(info.getStatus().getValue());
        model.setCreatedAt(info.getCreatedAt());
        model.setExpiredAt(info.getExpiredAt());
        model.setCreatedTime(Instant.now().getEpochSecond());
        return model;
    }
}
