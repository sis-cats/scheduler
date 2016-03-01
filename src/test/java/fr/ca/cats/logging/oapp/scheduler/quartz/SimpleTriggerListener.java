package fr.ca.cats.logging.oapp.scheduler.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.listeners.TriggerListenerSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleTriggerListener extends TriggerListenerSupport {
	
	private static Logger log = LoggerFactory.getLogger(SimpleTriggerListener.class.getName());

	private int cpt = 0;

	@Override
	public String getName() {
		return getClass().getName();
	}

	@Override
	public void triggerFired(Trigger trigger, JobExecutionContext context) {
		log.info(String.format("Fire a trigger [%s] for job [%s] jobDataMap [%s] with context [%s]",
				trigger.getKey().getName(), trigger.getJobKey().getName(), context.getMergedJobDataMap(),
				context.getFireInstanceId()));
	}

	@Override
	public void triggerComplete(Trigger trigger, JobExecutionContext context,
			Trigger.CompletedExecutionInstruction triggerInstructionCode) {
		log.info(String.format("Complete a trigger [%s] for job [%s] trigger mode [%s] with context [%s]",
				trigger.getKey().getName(), trigger.getJobKey().getName(), triggerInstructionCode.name(),
				context.getFireInstanceId()));
	}

	@Override
	public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
		log.info("Vetoed an execution");
		int i = (++cpt) % 2;
		log.debug(String.format("Vetoed : %d", i));
		return i == 0;
	}

	@Override
	public void triggerMisfired(Trigger trigger) {
		log.info("Misfire a trigger");
	}

}
