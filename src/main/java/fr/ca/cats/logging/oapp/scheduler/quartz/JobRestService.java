package fr.ca.cats.logging.oapp.scheduler.quartz;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Scheduler reading rest mico service based on a pure jersey implementation.
 * 
 */
@Path("job")
public class JobRestService {

	/**
	 * Internal logger
	 */
	private static Logger log = LogManager.getLogger(JobRestService.class);
	
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Path("/{jobName}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJobByName(@PathParam("jobName") final String jobName) {
    	log.info(String.format("Fetching information about the state of job [{}]", jobName));
        return "Got it!";
    }
}
