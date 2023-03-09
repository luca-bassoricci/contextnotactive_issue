package org.acme.quartz;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class QuartzSetupBean
{
	@Inject
	org.quartz.Scheduler quartz;

	void postConstruct(@Observes StartupEvent evt) throws SchedulerException 
	{
		JobDetail job = JobBuilder.newJob(QuartzInvoiceJob.class)
			.withIdentity("QuartzInvoiceJob", "myGroup")
			.build();
		Trigger trigger = TriggerBuilder.newTrigger()
			.withIdentity("myTrigger", "myGroup").startNow()
			.withSchedule(SimpleScheduleBuilder.simpleSchedule()
					.withIntervalInSeconds(10).repeatForever())
			.build();
		quartz.scheduleJob(job, trigger);
	}
}
