package org.wildfly.swarm.insult.adjective;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author Ken Finnigan
 */
@Path("/adjective")
public interface AdjectiveService {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Adjective getAdjective();
}
