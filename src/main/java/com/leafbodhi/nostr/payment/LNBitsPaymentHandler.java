package com.leafbodhi.nostr.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leafbodhi.nostr.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.util.Random;

/**
 * @author Jond
 */
@Slf4j
public class LNBitsPaymentHandler {

    private String API_SERVER = "https://legend.lnbits.com";
    private String API_KEY = "d887fa4299434db591e5e1df2765c60b";

    public LNBitsPaymentHandler() {
    }

    public String getWalletInfo() {
        String url = API_SERVER + "/api/v1/wallet";
        String result = "";
        try {
            result = HttpUtil.getForLNbits(url, API_KEY, "");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public InvoiceInfo getInvoice(String publicKey, Long amount) {
        try {
            // Generate a random number for the memo
            int randomNumber = new Random().nextInt(Integer.MAX_VALUE);
            String memo = String.format("%d: %s", randomNumber, publicKey);

            // Create the callback URL
            String callbackUrl = "http://127.0.0.1:8080/lnbits";

            Long createAt = Instant.now().getEpochSecond();
            Long expireInterval = 600L;
            Long expiredAt = createAt + expireInterval;

            // Create the invoice request
            LNBitsCreateInvoice invoiceRequest = new LNBitsCreateInvoice();
            invoiceRequest.setOut(false);
            invoiceRequest.setAmount(amount);
            invoiceRequest.setMemo(memo);
            invoiceRequest.setWebhook(callbackUrl);
            invoiceRequest.setUnit("sat");
            invoiceRequest.setInternal(false);
            invoiceRequest.setExpiry(expireInterval);

            // Create the request URL
            String url = API_SERVER + "/api/v1/payments";

            String result = "";
            ObjectMapper mapper = new ObjectMapper();
            result = HttpUtil.getForLNbits(url, API_KEY, mapper.writeValueAsString(invoiceRequest));

            log.info("create invoice:" + result);
            // Get the invoice response
            LNBitsCreateInvoiceResponse invoiceResponse = mapper.readValue(result, LNBitsCreateInvoiceResponse.class);
            // Return the invoice info
            InvoiceInfo info = new InvoiceInfo();
            info.setPubkey(publicKey);
            info.setPaymentHash(invoiceResponse.getPaymentHash());
            info.setBolt11(invoiceResponse.getPaymentRequest());
            info.setAmount(amount);
            info.setMemo(memo);
            info.setStatus(InvoiceStatus.PENDING);
            info.setCreatedAt(createAt);
            info.setExpiredAt(expiredAt);

            return info;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public InvoiceStatus checkInvoice(String paymentHash) throws Error, MalformedURLException {
        try {
            // Create the request URL
            String url = API_SERVER + "/api/v1/payments/" + paymentHash;
            URL requestUrl = new URL(url);
            String result = "";
            result = HttpUtil.getForLNbits(url, API_KEY, "");
            log.info("check invoice:" + result);

            // Get the invoice response
            LNBitsCheckInvoiceResponse invoiceResponse = new LNBitsCheckInvoiceResponse();
            // Return the invoice status
            return invoiceResponse.getPaid() ? InvoiceStatus.PAID : InvoiceStatus.PENDING;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return InvoiceStatus.PENDING;
    }
}
