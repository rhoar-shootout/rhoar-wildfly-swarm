package org.wildfly.swarm.insult.model;

/**
 * @author Ken Finnigan
 */
public class Name {
    private String name;

    public String getName() {
        return name;
    }

    public Name name(String name) {
        this.name = name;
        return this;
    }
}
