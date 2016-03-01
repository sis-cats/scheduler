package fr.ca.cats.logging.oapp.scheduler.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RepeatingJob implements Job {

	private Logger log = LoggerFactory.getLogger(SimpleJob.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.info("Executing repeating job");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// nothing to do
		}
		log.info("Repeating job execution complete");
	}

}
