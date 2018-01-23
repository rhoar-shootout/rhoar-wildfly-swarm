package org.wildfly.swarm.noun;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.wildfly.swarm.noun.model.Noun;
import org.wildfly.swarm.topology.Advertise;

/**
 * @author Ken Finnigan
 */
@Path("/")
@ApplicationScoped
@Advertise("noun")
public class NounResource {

    private List<Noun> nouns = new ArrayList<>();

    @PostConstruct
    public void loadData() {
        try {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("nouns.txt");
            if (is != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                reader.lines()
                        .forEach(noun -> nouns.add(new Noun(noun.trim())));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Noun getNoun() {
        return nouns.get(new Random().nextInt(nouns.size()));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addNoun(Noun noun) {
        if (nouns.contains(noun)) {
            return Response
                    .status(Response.Status.CONFLICT)
                    .build();
        }

        nouns.add(noun);

        return Response
                .status(Response.Status.CREATED)
                .entity(noun)
                .build();
    }

    @DELETE
    @Path("/{noun}")
    public Response deleteNoun(@PathParam("noun") String nounName) {
        Noun deletingNoun = new Noun(nounName);

        if (!nouns.contains(deletingNoun)) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        nouns.remove(deletingNoun);

        return Response
                .noContent()
                .build();
    }
}
