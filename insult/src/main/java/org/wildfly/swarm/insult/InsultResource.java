package org.wildfly.swarm.insult;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.wildfly.swarm.insult.adjective.AdjectiveService;
import org.wildfly.swarm.insult.model.Insult;
import org.wildfly.swarm.insult.model.Name;
import org.wildfly.swarm.insult.noun.NounService;
import org.wildfly.swarm.topology.Topology;

/**
 * @author Ken Finnigan
 */
@Path("/")
public class InsultResource {

    private Topology topology;

    public InsultResource() {
        try {
            topology = Topology.lookup();
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Insult getInsult() throws Exception {
        return insultByName(null);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Insult insultByName(Name name) throws Exception {
        URI nounServiceUrl = getService("noun");
        URI adjServiceUrl = getService("adjective");

        ResteasyClient nounClient = new ResteasyClientBuilder().build();
        ResteasyWebTarget nounTarget = nounClient.target(nounServiceUrl);
        NounService nounService = nounTarget.proxy(NounService.class);

        ResteasyClient adjClient = new ResteasyClientBuilder().build();
        ResteasyWebTarget adjTarget = adjClient.target(adjServiceUrl);
        AdjectiveService adjService = adjTarget.proxy(AdjectiveService.class);

        return new Insult()
                .noun(nounService.getNoun().getNoun())
                .adj1(adjService.getAdjective().getAdjective())
                .adj2(adjService.getAdjective().getAdjective())
                .name(name != null ? name.getName() : null);
    }

    private URI getService(String name) throws Exception {
        Map<String, List<Topology.Entry>> map = this.topology.asMap();

        if (map.isEmpty()) {
            throw new Exception("Service not found for '" + name + "'");
        }

        Optional<Topology.Entry> seOptional = map
                .get(name)
                .stream()
                .findFirst();

        Topology.Entry serviceEntry = seOptional.orElseThrow(() -> new Exception("Service not found for '" + name + "'"));

        return new URI("http", null, serviceEntry.getAddress(), serviceEntry.getPort(), null, null, null);
    }
}
