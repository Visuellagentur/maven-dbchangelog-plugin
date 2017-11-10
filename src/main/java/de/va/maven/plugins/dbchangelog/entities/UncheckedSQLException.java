package de.va.maven.plugins.dbchangelog.entities;

import java.sql.SQLException;

/**
 * The class UncheckedSQLException models an unchecked exception that is a holder for an SQLException that might
 * be raised during for example the streaming of change entities from the database.
 */
public class UncheckedSQLException extends RuntimeException {

    public UncheckedSQLException(final SQLException cause) {
        super(cause);
    }
}
