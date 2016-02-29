package fr.ca.cats.logging.oapp.scheduler.quartz.di;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import fr.ca.cats.logging.oapp.scheduler.quartz.context.DefaultSchedulabApplicationContext;
import fr.ca.cats.logging.oapp.scheduler.quartz.context.SchedulabApplicationContext;

@Configuration
@PropertySource("classpath:/scheduler.properties")
public class SchedulerConfiguration {

	///////////////////////////////////////////////////////////////////////////
	///
	/// Quartz scheduler related beans
	///
	///////////////////////////////////////////////////////////////////////////
	
	@Bean(name = "StdSchedulerFactory")
	public SchedulerFactory factory() {
		return new StdSchedulerFactory();
	}
	
	@Bean(name = "defaultScheduler")
	public Scheduler scheduler() throws SchedulerException {
		return factory().getScheduler();
	}
	
	///////////////////////////////////////////////////////////////////////////
	///
	/// Application processing related beans
	///
	///////////////////////////////////////////////////////////////////////////
	
	@Bean(name = "defaultSchedulableContext")
	public SchedulabApplicationContext context() {
		return new DefaultSchedulabApplicationContext();
	}
}
