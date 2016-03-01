package fr.ca.cats.logging.oapp.scheduler.quartz;

import static org.quartz.DateBuilder.evenMinuteDateAfterNow;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;

import org.junit.Test;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultQuartzSchedulerTest {

	private static Logger log = LoggerFactory.getLogger(DefaultQuartzSchedulerTest.class);

	@Test
	public void simpleSchedule() throws SchedulerException, InterruptedException {
		log.info("Creating a new scheduler");
		log.info("Creating scheduler factory");
		final SchedulerFactory factory = new StdSchedulerFactory();
		final SimpleTriggerListener triggerListener = new SimpleTriggerListener();
		final SimpleJobListener jobListener = new SimpleJobListener();
		final SimpleSchedulerListener schedulerListener = new SimpleSchedulerListener();
		log.info("Creating a scheduler");
		Scheduler scheduler = factory.getScheduler();
		log.info("Adding listeners");
		scheduler.getListenerManager().addTriggerListener(triggerListener);
		scheduler.getListenerManager().addJobListener(jobListener);
		scheduler.getListenerManager().addSchedulerListener(schedulerListener);
		log.trace("Creating a new job");
		JobDetail job = newJob(SimpleJob.class).withIdentity("job1", "group1").build();
		log.info("Creating a new trigger");
		Date runTime = evenMinuteDateAfterNow();
		Trigger trigger = newTrigger().withIdentity("trigger1", "group1").startAt(runTime).build();
		log.info("Scheduling the job");
		scheduler.scheduleJob(job, trigger);
		log.info("Starting the scheduler");
		scheduler.start();
		int round = 50;
		while (--round > 0) {
			JobDetail aJob = newJob(SimpleJob.class).withIdentity("job" + round, "group1").build();
			Trigger aTrigger = newTrigger().withIdentity("trigger" + round, "group1").startAt(runTime).build();
			scheduler.scheduleJob(aJob, aTrigger);
			Thread.sleep(1500);
		}
		log.info("All job done");
		scheduler.shutdown(true);
	}
}
