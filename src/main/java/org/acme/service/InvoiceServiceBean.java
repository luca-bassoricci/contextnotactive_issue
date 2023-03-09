package org.acme.service;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import org.acme.entity.Invoice;

@ApplicationScoped
public class InvoiceServiceBean
{
	@Transactional
	public @NotNull Invoice saveInvoice(@NotNull Invoice invoice)
	{
		invoice.persist();
		return invoice;
	}
}
