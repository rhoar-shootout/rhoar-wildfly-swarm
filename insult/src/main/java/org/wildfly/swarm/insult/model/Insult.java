package org.wildfly.swarm.insult.model;

/**
 * @author Ken Finnigan
 */
public class Insult {
    private String noun;

    private String adj1;

    private String adj2;

    private String name;

    public String getNoun() {
        return noun;
    }

    public Insult noun(String noun) {
        this.noun = noun;
        return this;
    }

    public String getAdj1() {
        return adj1;
    }

    public Insult adj1(String adj1) {
        this.adj1 = adj1;
        return this;
    }

    public String getAdj2() {
        return adj2;
    }

    public Insult adj2(String adj2) {
        this.adj2 = adj2;
        return this;
    }

    public String getName() {
        return name;
    }

    public Insult name(String name) {
        this.name = name;
        return this;
    }
}
