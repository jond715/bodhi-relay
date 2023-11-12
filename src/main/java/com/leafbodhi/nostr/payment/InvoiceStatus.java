package com.leafbodhi.nostr.payment;

/**
 * @author Jond
 */
public enum InvoiceStatus {
    PENDING("pending"),
    PAID("paid"),
    EXPIRE("expired");

    private final String value;

    private InvoiceStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

//    public InvoiceStatus valueOffff(String v){
//        if(InvoiceStatus.EXPIRE.value.equals(v)){
//            return InvoiceStatus.EXPIRE;
//        }else if(InvoiceStatus.PAID.value.equals(v)){
//            return InvoiceStatus.PAID;
//        }else{
//            return InvoiceStatus.PENDING;
//        }
//    }
}
