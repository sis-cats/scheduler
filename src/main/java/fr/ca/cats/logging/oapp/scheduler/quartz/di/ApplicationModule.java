package fr.ca.cats.logging.oapp.scheduler.quartz.di;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.google.inject.Binder;
import com.google.inject.Module;

import fr.ca.cats.logging.oapp.scheduler.quartz.context.DefaultSchedulabApplicationContext;
import fr.ca.cats.logging.oapp.scheduler.quartz.context.SchedulabApplicationContext;

/**
 * THis is a base DI used by Guice framework. Only used internally for unit testing
 * 
 * @author etp3468
 * @version 0.01
 */
public class ApplicationModule implements Module {
	
	private static Logger log = LogManager.getLogger(ApplicationModule.class);
	
	private static SchedulabApplicationContext schedulabApplicationContext;
	private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();
	private static Scheduler scheduler;
	
	static {
		try {
			scheduler = schedulerFactory.getScheduler();
			schedulabApplicationContext = new DefaultSchedulabApplicationContext(scheduler);
		} catch (SchedulerException e) {
			log.error(String.format("Unable to create the scheduler due to error [%s]", e.getMessage()), e);
			scheduler = null;
		}
	}

	@Override
	public void configure(Binder binder) {
		binder.bind(SchedulerFactory.class).toInstance(schedulerFactory);
		binder.bind(Scheduler.class).toInstance(scheduler);
		binder.bind(SchedulabApplicationContext.class).toInstance(schedulabApplicationContext);
	}

}
