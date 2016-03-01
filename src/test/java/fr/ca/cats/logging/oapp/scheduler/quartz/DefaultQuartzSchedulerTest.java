package fr.ca.cats.logging.oapp.scheduler.quartz;

import static org.quartz.DateBuilder.evenMinuteDateAfterNow;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.JobKey.jobKey;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.locks.Lock;

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
	public void aSimpleSchedule() throws SchedulerException, InterruptedException {
		
		Random rand = new Random();
		
		log.info("Creating a new scheduler");
		log.info("Creating scheduler factory");
		final SchedulerFactory factory = new StdSchedulerFactory();
		log.info("Creating a scheduler");
		Scheduler scheduler = factory.getScheduler();
		
		final SimpleTriggerListener triggerListener = new SimpleTriggerListener(scheduler);
		final SimpleSchedulerListener schedulerListener = new SimpleSchedulerListener(triggerListener);
		final SimpleJobListener jobListener = new SimpleJobListener(schedulerListener);
		log.info("Adding listeners");
		scheduler.getListenerManager().addTriggerListener(triggerListener);
		scheduler.getListenerManager().addJobListener(jobListener);
		scheduler.getListenerManager().addSchedulerListener(schedulerListener);
		log.trace("Creating a new job");
		JobDetail job = newJob(SimpleJob.class).withIdentity("job1" + Integer.toString(rand.nextInt()), "group1").build();
		log.info("Creating a new trigger");
		Date runTime = evenMinuteDateAfterNow();
		Trigger trigger = newTrigger().withIdentity("trigger1" + Integer.toString(rand.nextInt()), "group1").startAt(runTime).build();
		
		final String rjName = "repeatingJob" + Integer.toString(rand.nextInt());
		final String rjGroupName = "repeatingGroup";
		JobDetail repeatingJob = newJob(RepeatingJob.class)
				.withIdentity(rjName, rjGroupName)
				.build();
		Trigger rt = newTrigger().withIdentity("repeatingTrigger" + Integer.toString(rand.nextInt()), "repeatingTriggerGroup")
				.startNow()
				.withSchedule(simpleSchedule() 
			            .withIntervalInSeconds(2) 
			            .repeatForever())
				.build();
		log.info("Scheduling the job");
		scheduler.scheduleJob(job, trigger);
		scheduler.scheduleJob(repeatingJob, rt);
		log.info("Starting the scheduler");
		scheduler.start();
		int round = 10;
		while (--round > 0) {
			final String str = Integer.toString(rand.nextInt());
			JobDetail aJob = newJob(SimpleJob.class).withIdentity("job" + round, "group1" + str).build();
			Trigger aTrigger = newTrigger().withIdentity("trigger" + round, "group1" + str).startNow().build(); //(runTime).build();
			scheduler.scheduleJob(aJob, aTrigger);
			//Thread.sleep(300);
		}
		log.info("All job done");
		
		Lock lock = schedulerListener.getExitLock();
		synchronized(lock) {
			// Add a timeout
			// have to shutdown the repeating job
			lock.wait(10000);
		}
		scheduler.deleteJob(jobKey(rjName, rjGroupName));
		log.info("Quitting");
	}
}
