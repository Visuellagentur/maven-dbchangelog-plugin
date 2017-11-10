package de.va.maven.plugins.dbchangelog;

public enum ReleaseType {

    RELEASE,
    PATCH,
    HOTFIX;

    public static ReleaseType fromName(final String name) {
        return ReleaseType.valueOf(name.toUpperCase());
    }
}
