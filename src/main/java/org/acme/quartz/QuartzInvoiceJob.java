package org.acme.quartz;

import javax.inject.Inject;

import org.acme.entity.Invoice;
import org.acme.service.InvoiceServiceBean;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@DisallowConcurrentExecution
public class QuartzInvoiceJob implements Job
{	
	@Inject
	InvoiceServiceBean invoiceService;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException
	{
		final Invoice bean = new Invoice("invalid-invoiceNo");
		invoiceService.saveInvoice(bean);
	}
}
