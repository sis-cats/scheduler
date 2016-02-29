package fr.ca.cats.logging.oapp.scheduler.quartz.di;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({DatabaseConfiguration.class, SchedulerConfiguration.class })
public class SpringConfiguration {

	
}
