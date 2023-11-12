package com.leafbodhi.nostr;

import com.leafbodhi.nostr.payment.LNBitsPaymentHandler;
import org.junit.jupiter.api.Test;

/**
 * @author Jond
 */
public class TestLnBitsPaymentHandler {

    @Test
    public void testLnBits() throws Exception {
        LNBitsPaymentHandler handler = new LNBitsPaymentHandler();
        try {
            System.out.println(handler.checkInvoice("e8e2cd9e746ec45b551fa23977be4bfa499182ca6176cd6281c40e9754444832"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
