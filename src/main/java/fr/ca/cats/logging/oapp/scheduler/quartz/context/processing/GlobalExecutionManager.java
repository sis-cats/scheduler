package fr.ca.cats.logging.oapp.scheduler.quartz.context.processing;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.quartz.listeners.SchedulerListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import fr.ca.cats.logging.oapp.scheduler.quartz.context.SchedulabApplicationContext;

/**
 * This is the main handler on scheduler.
 * This is responsible to realign scheduling process and scheduler.
 * 
 * It listen about all relevant scheduling events.
 * 
 * Typical needs is to check if the date of the day is still available. If the date isn't still valid,
 * we will indicates to the {@code schedulabApplicationContext} that it has to do something around the
 * context (even if we are no longer in the old way of working)
 * 
 * @author etp3468
 *
 */
@SuppressWarnings("unused")
@Component
public class GlobalExecutionManager extends SchedulerListenerSupport {
	
	private static Logger log = LogManager.getLogger(GlobalExecutionManager.class);

	private DateTime startTime;
	private DateTime endTime;
	
	@Autowired
	@Qualifier("defaultSchedulableContext")
	private SchedulabApplicationContext schedulabApplicationContext;

	public GlobalExecutionManager() {
		super();
		log.info("Starting global execution manager");
	}
	
	
	
}
