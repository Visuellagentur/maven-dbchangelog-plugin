package de.va.maven.plugins.dbchangelog.entities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The final class ConnectionFactory models a factory for JDBC connections.
 */
public final class ConnectionFactory {

    public Connection createNewConnection(final String url, final String driver,
                                          final String username, final String password) throws SQLException {
        Connection result;

        try {
            Class.forName(driver);
            result = DriverManager.getConnection(url, username, password);
            result.setAutoCommit(false);
        } catch (final ClassNotFoundException ex) {
            throw new SQLException("jdbc driver '" + driver + "' is not available", ex);
        } catch (final NullPointerException | SQLException ex) {
            throw new SQLException("invalid db connection properties or unable to connect to dbms", ex);
        }

        return result;
    }
}
