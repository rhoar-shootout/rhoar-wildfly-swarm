package org.wildfly.swarm.adjective;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
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

import org.wildfly.swarm.adjective.model.Adjective;

/**
 * @author Ken Finnigan
 */
@Path("/")
@ApplicationScoped
public class AdjectiveResource {

    private List<Adjective> adjectives = new ArrayList<>();

    @PostConstruct
    public void loadData() {
        try {
            URL adjectivesUrl = this.getClass().getClassLoader().getResource("adjectives.txt");
            if (adjectivesUrl != null) {
                Files.lines(Paths.get(adjectivesUrl.toURI()))
                        .forEach(adj -> adjectives.add(new Adjective(adj)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Adjective getAdjective() {
        return adjectives.get(new Random().nextInt(adjectives.size()));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addAdjective(Adjective adjective) {
        if (adjectives.contains(adjective)) {
            return Response
                    .status(Response.Status.CONFLICT)
                    .build();
        }

        adjectives.add(adjective);

        return Response
                .status(Response.Status.CREATED)
                .entity(adjective)
                .build();
    }

    @DELETE
    @Path("/{adjective}")
    public Response deleteAdjective(@PathParam("adjective") String adjectiveName) {
        Adjective deletingAdjective = new Adjective(adjectiveName);

        if (!adjectives.contains(deletingAdjective)) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        adjectives.remove(deletingAdjective);

        return Response
                .noContent()
                .build();
    }
}
