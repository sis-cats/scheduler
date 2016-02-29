package fr.ca.cats.logging.oapp.scheduler.quartz;

import static org.quartz.DateBuilder.evenMinuteDateAfterNow;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;
import java.util.UUID;

import org.junit.Test;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultQuartzSchedulerTest {

	private static Logger log = LoggerFactory.getLogger(DefaultQuartzSchedulerTest.class);

	public static class SimpleJob implements Job {

		private static final String COUNT = "count";

		private Logger log = LoggerFactory.getLogger(SimpleJob.class);

		private UUID uuid;

		public SimpleJob() {
			log.info("Creating a new job");
			uuid = UUID.randomUUID();
		}

		@Override
		public void execute(JobExecutionContext context) throws JobExecutionException {
			log.info("Starting job execution [{}]", uuid.toString());
			JobKey jobKey = context.getJobDetail().getKey();

			if (context.isRecovering()) {
				log.info("SimpleRecoveryJob: " + jobKey + " RECOVERING at " + new Date());
			} else {
				log.info("SimpleRecoveryJob: " + jobKey + " starting at " + new Date());
			}

			try {
				log.trace("Job [{}] is now sleeping in thread [{}]", uuid.toString(), Thread.currentThread().getName());
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				log.error("Thread sleeping in erro with message [{}]", e.getMessage());
			}
			log.info("Job execution complete [{}]", uuid.toString());

			JobDataMap data = context.getJobDetail().getJobDataMap();
			int count;
			if (data.containsKey(COUNT)) {
				count = data.getInt(COUNT);
			} else {
				count = 0;
			}
		    ++count;
		    data.put(COUNT, count);
			log.info("Job {} has been execyted {} time(s) in order to be successfully completed", uuid.toString(), count);
		}
	}

	@Test
	public void simpleSchedule() throws SchedulerException, InterruptedException {
		log.info("Creating a new scheduler");
		log.info("Creating scheduler factory");
		SchedulerFactory factory = new StdSchedulerFactory();
		log.info("Creating a scheduler");
		Scheduler scheduler = factory.getScheduler();
		scheduler.clear();
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
			Thread.sleep(3000);
		}
		log.info("All job done");
		scheduler.shutdown(true);
	}
}
