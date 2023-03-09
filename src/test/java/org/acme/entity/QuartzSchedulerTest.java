package org.acme.entity;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.acme.quartz.QuartzInvoiceJob;
import org.junit.jupiter.api.Test;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import io.quarkus.arc.Arc;
import io.quarkus.arc.Priority;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.QuarkusTestProfile;
import io.quarkus.test.junit.TestProfile;

@QuarkusTest
@TestProfile(QuartzSchedulerTest.Profile.class)
public class QuartzSchedulerTest
{
	public static class Profile implements QuarkusTestProfile
	{
		@Override
		public Map<String, String> getConfigOverrides()
		{
			return Map.of("quarkus.quartz.start-mode", "forced");
		}
	}

	@Test
	public void testSimpleScheduledJobs() throws InterruptedException
	{
		CountDownLatch latch = Arc.container().instance(CountDownLatch.class).get();
		assertTrue(latch.await(5, TimeUnit.SECONDS), "Latch count: " + latch.getCount());
	}

	@ApplicationScoped
	public static class CountDownLatchProducer
	{
		@Produces
		@Singleton
		CountDownLatch LATCH = new CountDownLatch(2);
	}

	@Decorator
	@Priority(1)
	@Alternative
	public static abstract class LatchedJob extends QuartzInvoiceJob
	{
		@Delegate
		@Inject
		QuartzInvoiceJob job;
		@Inject
		CountDownLatch latch;

		@Override
		public void execute(JobExecutionContext context) throws JobExecutionException
		{
			super.execute(context);
			latch.countDown();
		}
	}
}
