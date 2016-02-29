package fr.ca.cats.logging.oapp.scheduler.quartz.dao;

public interface JobDao {

	/**
	 * Execute a standard CALL on the stored procedure
	 * 
	 * @param joblet	the job to pass to the stored procedure
	 * @return			the result passed back by the procedure
	 */
	int callProcedure(Object joblet);
}
