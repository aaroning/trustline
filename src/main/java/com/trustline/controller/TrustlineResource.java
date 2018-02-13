package com.trustline.controller;

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

import com.trustline.domain.Payment;
import com.trustline.service.AccountService;

@Path("/trustline")
public class TrustlineResource {
	
	private final AccountService accountService;

	@Inject
	public TrustlineResource(AccountService accountService) {
		this.accountService = accountService;
	}
	
	@POST
	@Path("/account")
    @Consumes(MediaType.APPLICATION_XML)
	public Response receiveFunds(Payment payment) {
		accountService.receivePayment(payment.getAmount());
		return Response.status(200).build();
	}
	
	@GET
	@Path("/sendFunds")
	public Response sendFunds(@QueryParam("recipient") String recipient, @QueryParam("amount") String amount) {
		accountService.sendPayment(recipient, Integer.parseInt(amount));
		return Response.status(200).build();
	}

}
