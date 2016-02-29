package fr.ca.cats.logging.oapp.scheduler.quartz.context;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * This is the entry point of the job scheduler. Since we have to start / stop / clean / restart / ...
 * of the scheduler processing we want to ensure his working's consistency.
 * 
 * To do that, we are working on two layer provided by the container.
 * <ul>
 * 	<ol>
 * 		The first one is the {@code Servlet} container layer (meanings on the server starting / stopping).
 * 		In this case, we have to check the state of the scheduler. It has to be started and the job stack has to 
 * 		be clean.
 * 		What does "clean" means ?<br>
 * 		Clean is equivalent that no job should be marked has "pending" or on "error".
 * 		<ul>
 * 			<li>If one or more are in this 
 * 				state, the scheduling process implementation will try to repair the fault. See {@see RepairingProcessor}
 * 				for this case.</li>
 * 			<li>If some slots has been forget and if they are still "processable", the worker will try to process these.
 * 			({@see ForgettedProcessor}).</li>
 * 			<li>If some jobs aborted. they can be rescheduled according to our retrying policy ({@see RetryPolicy}). Jobs
 * 			that exceed the number of processing attempts will be forget.Database will reflect the state of the job and
 * 			some reporting can be made easily.</li>
 * 		</ul>
 * 	</ol>
 * 	<ol>
 * 		The second one is the {@code Servlet} lifecycle (meanings web request layer). This layer provide a way to do some action
 * 		at each request. For now, we'll doing some little check but since we are working with the scheduler itself, it's
 * 		not always relevant.
 * 	</ol>
 * </ul>
 * </p>
 * 
 * @author etp3468
 * @version 0.0.1
 */
@SuppressWarnings("unused")
public class SchedulerServletContextListener implements ServletContextListener {

	@Autowired
	private SchedulabApplicationContext schedulabApplicationContext;
	
	private ServletContext context;
	
	public SchedulerServletContextListener() {
		log("Starting scheduler context listener");
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		context = sce.getServletContext();
		schedulabApplicationContext.setServletContext(context);
		
		/*try {
		} catch (SchedulerException e) {
			log(String.format("Unable to manage scheduling due to error [%s]", e.getMessage()), e);
		}*/
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		context = sce.getServletContext();
	}

	private void log(String message, Object ...params) {
		final String msg = String.format(message, params);
		if(null != context) {
			context.log(msg);
		} else {
			System.out.println(msg);
		}
	}
	
	private void error(String message, Throwable th) {
		if(null != context) {
			context.log(message);
		} else {
			System.out.println(message);
		}
		th.printStackTrace(System.err);
	}
}
