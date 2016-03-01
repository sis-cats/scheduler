package fr.ca.cats.logging.oapp.scheduler.quartz;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.listeners.SchedulerListenerSupport;

public class SimpleSchedulerListener extends SchedulerListenerSupport {

	@Override
	public void jobAdded(JobDetail jobDetail) {
		// TODO Auto-generated method stub
		super.jobAdded(jobDetail);
	}

	@Override
	public void jobDeleted(JobKey jobKey) {
		// TODO Auto-generated method stub
		super.jobDeleted(jobKey);
	}

	@Override
	public void jobPaused(JobKey jobKey) {
		// TODO Auto-generated method stub
		super.jobPaused(jobKey);
	}

	@Override
	public void jobResumed(JobKey jobKey) {
		// TODO Auto-generated method stub
		super.jobResumed(jobKey);
	}

	@Override
	public void jobScheduled(Trigger trigger) {
		// TODO Auto-generated method stub
		super.jobScheduled(trigger);
	}

	@Override
	public void jobsPaused(String jobGroup) {
		// TODO Auto-generated method stub
		super.jobsPaused(jobGroup);
	}

	@Override
	public void jobsResumed(String jobGroup) {
		// TODO Auto-generated method stub
		super.jobsResumed(jobGroup);
	}

	@Override
	public void jobUnscheduled(TriggerKey triggerKey) {
		// TODO Auto-generated method stub
		super.jobUnscheduled(triggerKey);
	}

	@Override
	public void schedulerError(String msg, SchedulerException cause) {
		// TODO Auto-generated method stub
		super.schedulerError(msg, cause);
	}

	@Override
	public void schedulerInStandbyMode() {
		// TODO Auto-generated method stub
		super.schedulerInStandbyMode();
	}

	@Override
	public void schedulerShutdown() {
		// TODO Auto-generated method stub
		super.schedulerShutdown();
	}

	@Override
	public void schedulerShuttingdown() {
		// TODO Auto-generated method stub
		super.schedulerShuttingdown();
	}

	@Override
	public void schedulerStarted() {
		// TODO Auto-generated method stub
		super.schedulerStarted();
	}

	@Override
	public void schedulerStarting() {
		// TODO Auto-generated method stub
		super.schedulerStarting();
	}

	@Override
	public void triggerFinalized(Trigger trigger) {
		// TODO Auto-generated method stub
		super.triggerFinalized(trigger);
	}

	@Override
	public void triggerPaused(TriggerKey triggerKey) {
		// TODO Auto-generated method stub
		super.triggerPaused(triggerKey);
	}

	@Override
	public void triggerResumed(TriggerKey triggerKey) {
		// TODO Auto-generated method stub
		super.triggerResumed(triggerKey);
	}

	@Override
	public void triggersPaused(String triggerGroup) {
		// TODO Auto-generated method stub
		super.triggersPaused(triggerGroup);
	}

	@Override
	public void triggersResumed(String triggerGroup) {
		// TODO Auto-generated method stub
		super.triggersResumed(triggerGroup);
	}

	@Override
	public void schedulingDataCleared() {
		// TODO Auto-generated method stub
		super.schedulingDataCleared();
	}
}
