package de.va.maven.plugins.dbchangelog;

public enum ChangeType {

    //SCHEMA,
    CONTENT;

    public static ChangeType fromName(final String name) {
        return ChangeType.valueOf(name.toUpperCase());
    }
}
