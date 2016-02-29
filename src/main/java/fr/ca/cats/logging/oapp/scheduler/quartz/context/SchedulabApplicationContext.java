package fr.ca.cats.logging.oapp.scheduler.quartz.context;

import javax.servlet.ServletContext;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;

public interface SchedulabApplicationContext {

	/**
	 * Fetch the application scheduler.
	 * 
	 * @return the application global scheduler.
	 */
	Scheduler getScheduler();
	
	/**
	 * Boot the application context
	 * 
	 * @return
	 */
	int boot();
	
	/**
	 * Shutdown the application context
	 * 
	 * @return
	 */
	int shutdown();
	
	/**
	 * Check if the application context is started.
	 * 
	 * @return	true if the application context is started
	 * 			false if the application context isn't started
	 */
	boolean isStarted();
	
	/**
	 * Check if the context is shutdown.
	 * 
	 * @return
	 */
	boolean isShutdown();
	
	/**
	 * Check if the context is in standby mode
	 * 
	 * @return
	 */
	boolean isInStandbyMode();

	/**
	 * Define the servlet context of the application.
	 * 
	 * @param sc
	 */
	void setServletContext(ServletContext sc);
	
	/**
	 * Fetch the servlet context actually define as current context in the application.
	 * 
	 * @return
	 */
	ServletContext getServletContext();
	
	/**
	 * Place the scheduler in standby mode
	 * 
	 * @param inStandBy		indicates the mode
	 * 						true indicates that the standby mode will be activated
	 * 						false indicates that the standby mode will be deactivated
	 * @return	the instance itself
	 * @throws 	SchedulerException	throw an exception if something goes wrong on the scheduler layer 
	 */
	SchedulabApplicationContext setStandByMode(boolean inStandBy) throws SchedulerException;
}
