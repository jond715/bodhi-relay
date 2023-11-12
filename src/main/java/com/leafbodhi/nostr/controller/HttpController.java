package com.leafbodhi.nostr.controller;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.leafbodhi.nostr.db.model.InvoiceModel;
import com.leafbodhi.nostr.db.service.IInvoiceService;
import com.leafbodhi.nostr.payment.*;
import com.leafbodhi.nostr.utils.NostrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.leafbodhi.nostr.config.ServerInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;

import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 * @author Jond
 */
@Slf4j
@Controller
public class HttpController {
	@Autowired
	private HttpServletRequest req;
	@Autowired
	private HttpServletResponse resp;
	@Autowired
	private ServerInfo serverInfo;

	@Autowired
	private IInvoiceService invoiceService;
	
	private static ObjectMapper OBJECTMAPPER = new ObjectMapper();

	private final String DEFAULT_ACCEPT = "application/nostr+json";

	private final String ACCEPTED_YES = "yes";

	@GetMapping("/")
	public String index() throws IOException {
		String reqAccept = req.getHeader("Accept");
		System.out.println("Accept:" + reqAccept);

		if (DEFAULT_ACCEPT.equals(reqAccept)) {
			resp.setHeader("Access-Control-Allow-Origin", "*");
			resp.setHeader("Access-Control-Allow-Headers", "*");
			resp.setHeader("Access-Control-Allow-Methods", "GET, POST");

			String response = "";

			try {
				response = OBJECTMAPPER.writeValueAsString(serverInfo);
			} catch (JsonProcessingException e) {
				log.warn("Error during response serialization.", e);
				response = "Nostr reley error.";
			}

			return response;
		} else {
			return "Please use a Nostr client to connect.";
		}
	}
	@RequestMapping("/invoices")
	public String invoices(Model model) {
		//TODO payment status
		model.addAttribute("name","bodhi-relay");
		model.addAttribute("amount","5000");
		return "index.html";
	}

	@RequestMapping("/invoices_process")
	public String invoicesProcess(String pubkey,String tosAccepted,String feeSchedule,Model model) {
		//[pubkey=npub1gk7jtjry3kjg03tnz385s8d3qt9z8l2s9c94q0kfp6mm53g7xfasgux4q2&tosAccepted=yes&feeSchedule=admission]
		log.info("pubkey:" + pubkey + ", tosAccepted:" + tosAccepted);
		if(!ACCEPTED_YES.equals(tosAccepted)){
			return "redirect:/invoices";
		}
		Long amount = 5000L;
		//TODO data persistence
		LNBitsPaymentHandler paymentHandler = new LNBitsPaymentHandler();
		InvoiceInfo invoiceInfo = paymentHandler.getInvoice(pubkey, amount);
		log.info(invoiceInfo.toString());

		InvoiceModel invoiceModel = InvoiceWrapper.objToModel(invoiceInfo);

		invoiceService.save(invoiceModel);


		model.addAttribute("invoiceInfo",invoiceInfo);
		model.addAttribute("relayUrl","wss://relay.leafbodhi.com");
		model.addAttribute("relayPubkey",serverInfo.getPubkey());
		model.addAttribute("pubkey",pubkey);
		model.addAttribute("processor","lnbits");
		model.addAttribute("name",serverInfo.getName());


		return "invoices.html";
	}
	@RequestMapping("/payment")
	public String payment(Model map) {
		map.addAttribute("name","bodhi-relay");
		map.addAttribute("relayUrl","relay.leafbodhi.com");
		return "invoices.html";
	}

	@GetMapping("/invoices/{pubkey}/status")
	@ResponseBody
	public String invoicesStatus(@PathVariable(value = "pubkey")String pubkey) {
		InvoiceStatusResponse resp = new InvoiceStatusResponse();
		resp.setReference(pubkey);
		//TODO
		InvoiceModel model = invoiceService.getByPubkey(pubkey);
		if(model == null) {
			resp.setStatus(InvoiceStatus.EXPIRE.getValue());
		}else{
			resp.setStatus(model.getStatus());
		}
		ObjectMapper mapper = new ObjectMapper();

		String jsonStr = null;
		try {
			jsonStr = mapper.writeValueAsString(resp);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return jsonStr;
	}


}
