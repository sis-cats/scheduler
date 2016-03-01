package fr.ca.cats.logging.oapp.scheduler.quartz;

import java.net.InetAddress;
import java.util.Random;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleJobListener implements JobListener {
	
	private static Logger log = LoggerFactory.getLogger(SimpleJobListener.class);
	
	private Random random = new Random();
	
	private SimpleSchedulerListener schedulerListener;

	public SimpleJobListener(SimpleSchedulerListener schedulerListener) {
		this.schedulerListener = schedulerListener;
	}

	@Override
	public String getName() {
		return getClass().getSimpleName();
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		context.put("slotId", random.nextInt());
		context.put("jobId", random.nextInt());
		context.put("tsStart", random.nextLong());
		context.put("tsEnd", random.nextLong());
		try {
			InetAddress addr = InetAddress.getLocalHost();
			context.put("serverName", addr.getHostName());
			context.put("serverIp", addr.getHostAddress());
		} catch(Exception e) {
			context.put("serverName", "localhost");
			context.put("serverIp", "127.0.0.1");
		}
		log.info("Job will be executed");
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		log.info("Job was vetoed");
	}

	@Override
	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
		log.info("Job was executed");
		schedulerListener.notifyDone();
	}

}
