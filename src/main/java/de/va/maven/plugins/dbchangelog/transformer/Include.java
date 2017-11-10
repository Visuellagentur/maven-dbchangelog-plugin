package de.va.maven.plugins.dbchangelog.transformer;

public class Include implements Comparable<Include> {

    private final Resource resource;

    public Include(final Resource resource) {
        this.resource = resource;
    }

    public Resource getResource() {
        return this.resource;
    }

    public int compareTo(final Include other) {
        throw new RuntimeException("not implemented yet");
    }
}
