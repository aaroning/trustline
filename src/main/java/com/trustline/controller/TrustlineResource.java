package com.trustline.controller;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import com.trustline.domain.Payment;
import com.trustline.service.AccountService;

@Path("/trustline")
public class TrustlineResource {
	
	private final AccountService accountService;
	
	private static final Logger LOG = LogManager.getLogger();

	@Inject
	public TrustlineResource(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@POST
	@Path("/account")
    @Consumes(MediaType.APPLICATION_JSON)
	public Response receiveFunds(String request) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode payment = mapper.readTree(request);
			accountService.receivePayment(payment.get("amount").asInt());
		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		
		return Response.status(Response.Status.OK).build();
	}
	
	@GET
	@Path("/sendFunds")
	public Response sendFunds(@QueryParam("recipient") String recipient, @QueryParam("amount") String amount) {
		try {
			accountService.sendPayment(recipient, Integer.parseInt(amount));
		} catch (Exception e) {
			LOG.error("server error sending funds", e);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.status(Response.Status.OK).build();
	}

}
