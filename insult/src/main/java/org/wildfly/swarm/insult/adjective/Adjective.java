package org.wildfly.swarm.insult.adjective;

/**
 * @author Ken Finnigan
 */
public class Adjective {

    private String adjective;

    public Adjective() {
    }

    public Adjective(String adjective) {
        this.adjective = adjective;
    }

    public String getAdjective() {
        return adjective;
    }

    public Adjective adjective(String adjective) {
        this.adjective = adjective;
        return this;
    }
}
