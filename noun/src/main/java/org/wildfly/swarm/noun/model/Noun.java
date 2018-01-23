package org.wildfly.swarm.noun.model;

import java.util.Objects;

/**
 * @author Ken Finnigan
 */
public class Noun {

    private String noun;

    public Noun() {
    }

    public Noun(String noun) {
        this.noun = noun;
    }

    public String getNoun() {
        return noun;
    }

    public Noun noun(String noun) {
        this.noun = noun;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Noun noun1 = (Noun) o;
        return Objects.equals(noun, noun1.noun);
    }

    @Override
    public int hashCode() {
        return Objects.hash(noun);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Noun{");
        sb.append("noun='").append(noun).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
