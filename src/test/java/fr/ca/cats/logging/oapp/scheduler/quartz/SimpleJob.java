package fr.ca.cats.logging.oapp.scheduler.quartz;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SimpleJob implements Job {
	
	private static final String COUNT = "count";

	private Logger log = LoggerFactory.getLogger(SimpleJob.class);

	private UUID uuid;
	private String serverName;
	private String serverIp;
	private int slotId;
	private int jobId;
	private long tsStart;
	private long tsEnd;

	public SimpleJob() {
		log.info("Creating a new job");
		uuid = UUID.randomUUID();
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public int getSlotId() {
		return slotId;
	}

	public void setSlotId(int slotId) {
		this.slotId = slotId;
	}

	public int getJobId() {
		return jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public long getTsStart() {
		return tsStart;
	}

	public void setTsStart(long tsStart) {
		this.tsStart = tsStart;
	}

	public long getTsEnd() {
		return tsEnd;
	}

	public void setTsEnd(long tsEnd) {
		this.tsEnd = tsEnd;
	}
	
	public String describe() {
		StringBuilder sb = new StringBuilder();
		Field[] fields = getClass().getDeclaredFields();
		for(final Field f : fields) {
			if(sb.length() > 0) {
				sb.append(',');
			}
			try {
				sb.append(f.getName())
				.append('-')
				.append(f.get(this));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// nothing to do ...
			}
		}
		return sb.toString();
	}
	
	@PostConstruct
	public void postConstruct() {
		log.info("Job creation completed");
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.info("Starting job execution [{}]", uuid.toString());
		log.info(String.format("Describing the job: [%s]", describe()));
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
		if(data.containsKey("slotId")) {
			log.info(String.format("slotId: [%d]", data.get("slotId")));
		} else {
			log.error("SlotId not found");
		}
		if(data.containsKey("serverName")) {
			log.info(String.format("serverName: [%s]", data.getString("serverName")));
		} else {
			log.error("ServerName not found");
		}
		int count;
		if (data.containsKey(COUNT)) {
			count = data.getInt(COUNT);
		} else {
			count = 0;
		}
		++count;
		data.put(COUNT, count);
		log.info("Job {} has been execyted {} time(s) in order to be successfully completed", uuid.toString(),
				count);
	}

}
