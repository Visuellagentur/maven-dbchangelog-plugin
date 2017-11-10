package de.va.maven.plugins.dbchangelog;

public enum ActionType {

    INSERT,
    DELETE,
    UPDATE;

    public static ActionType fromName(final String name) {
        return ActionType.valueOf(name.toUpperCase());
    }
}
