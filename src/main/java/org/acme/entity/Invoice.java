package org.acme.entity;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Invoice extends PanacheEntity
{
	@NotNull
	@Pattern(regexp = "^#")
	private String invoiceNo;
	
	//	HBM
	Invoice(){}
	public Invoice(String invoiceNo)
	{
		this.invoiceNo = invoiceNo;
	}
	
	public String getInvoiceNo()
	{
		return invoiceNo;
	}
}
