package fr.ca.cats.logging.oapp.scheduler.quartz;

import java.util.EventListener;

import org.quartz.JobExecutionContext;

public interface ProcessingListener extends EventListener {

	void beforeJobProcessing(JobExecutionContext context);
	
	void afterJobProcessing(JobExecutionContext context);
}
