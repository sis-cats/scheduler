package fr.ca.cats.logging.oapp.scheduler.quartz.context;

import javax.servlet.ServletContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;

public class DefaultSchedulabApplicationContext implements SchedulabApplicationContext {
	
	private static Logger log = LogManager.getLogger(DefaultSchedulabApplicationContext.class);
	
	public static final int SUCCESS = 0;
	public static final int GENERIC_ERROR = 1;
	public static final int NOT_STARTED_ERROR = 2;
	public static final int ALREADY_STARTED_ERROR = 3;
	
	@Autowired
	private Scheduler scheduler;
	private ServletContext servletContext;
	
	public DefaultSchedulabApplicationContext() {
		log.info("Creating a new instance of the application context");
	}
	
	/**
	 * Instance of the application context
	 */
	public DefaultSchedulabApplicationContext(Scheduler scheduler) {
		this();
		this.scheduler = scheduler;
	}

	@Override
	public Scheduler getScheduler() {
		return scheduler;
	}
	
	public ServletContext getServletContext() {
		return servletContext;
	}
	
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@Override
	public int boot() {
		log.info("Booting the application context");
		int result = 0;
		if(isStarted()) {
			log.warn("The context is already up");
			result = ALREADY_STARTED_ERROR;
		} else {
			try {
				scheduler.start();
				log.info("A[plication context successfully started");
				result = SUCCESS;
			} catch(Exception e) {
				log.error(String.format("Unable to start the context due to error : [%s]", e.getMessage()), e);
				result = GENERIC_ERROR;
			}
		}
		return result;
	}

	@Override
	public int shutdown() {
		log.info("Shuting down the context");
		int result = 0;
		if(!isStarted()) {
			result = NOT_STARTED_ERROR;
		} else {
			try {
				scheduler.shutdown(true);
				log.info("The context has been successfully shutdown");
				result = SUCCESS;
			} catch(Exception e) {
				log.error(String.format("An error occurrre when the context trying to shutdown with message [%s]", e.getMessage()), e);
				result = GENERIC_ERROR;
			}
		}
		return result;
	}

	@Override
	public boolean isStarted() {
		log.info("Check if the context is started");
		try {
			return scheduler.isStarted();
		} catch (SchedulerException e) {
			log.error(String.format("Unable to stop the context due to the following error [{}]", e.getMessage()), e);
			return false;
		}
	}
	
	@Override
	public boolean isShutdown() {
		try {
			return scheduler.isShutdown();
		} catch (SchedulerException e) {
			log.error(String.format("Error occurred when checking about eh shutdown state of the application context with message [%s]", e.getMessage()), e);
			return false;
		}
	}

	@Override
	public boolean isInStandbyMode() {
		try {
			return scheduler.isInStandbyMode();
		} catch (SchedulerException e) {
			log.error(String.format("Error occurred when checking about eh standby state of the application context with message [%s]", e.getMessage()), e);
			return false;
		}
	}

	@Override
	public SchedulabApplicationContext setStandByMode(boolean inStandBy) throws SchedulerException {
		// TODO : should we check the state of the scheduler ?
		// TODO : should we reaaly leave an exception ?
		if(inStandBy) {
			log.info("Tryingo to put the scheduler in standby mode");
			scheduler.standby();
		} else {
			log.info("Tryingo to restart the scheduler");
			scheduler.start();
		}
		return this;
	}

}
