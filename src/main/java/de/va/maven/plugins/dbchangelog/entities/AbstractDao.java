package de.va.maven.plugins.dbchangelog.entities;

import org.apache.maven.plugin.logging.Log;

import java.sql.*;

/**
 * The abstract class AbstractDao models the root of a hierarchy of derived classes that realize the DAO pattern.
 */
public abstract class AbstractDao {

    private Connection connection;
    private Log log;
    private boolean commitInProgress;

    protected AbstractDao() {
        this.commitInProgress = false;
    }

    public void setConnection(final Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void setLog(final Log log) {
        this.log = log;
    }

    public Log getLog() {
        return this.log;
    }

    public abstract void init() throws SQLException;

    public void destroy() {
        this.connection = null;
        this.log = null;
    }

    /**
     * Closes the specified result set.
     *
     * Any SQLExceptionS raised will be logged as a warning.
     *
     * @param resultSet
     */
    protected void close(final ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            }
            catch (SQLException ex) {
                this.getLog().warn("closing result set failed", ex);
            }
        }
    }

    /**
     * Closes the specified statement.
     *
     * Any SQLExceptionS raised will be logged as a warning.
     *
     * @param statement
     */
    protected void close(final Statement statement) {
        try {
            statement.close();
        }
        catch (SQLException ex) {
            this.getLog().warn("closing statement failed", ex);
        }
    }

    /**
     * Commits the current transaction.
     *
     * Any SQLExceptionS raised will be logged as an error and the exception will be rethrown.
     */
    protected void commit() throws SQLException {
        try {
            this.commitInProgress = true;
            this.connection.commit();
            this.commitInProgress = false;
        }
        catch (SQLException ex) {
            this.getLog().error("commit failed", ex);
            throw ex;
        }
    }

    /**
     * Rolls back a previously failed commit.
     *
     * Any SQLExceptionS raised will be logged as a warning.
     */
    protected void rollback() {
        if (this.commitInProgress) {
            try {
                this.commitInProgress = false;
                this.connection.rollback();
            } catch (SQLException ex) {
                this.getLog().warn("rollback failed", ex);
            }
        }
    }

    /**
     * Creates a new prepared statement from the specified query.
     *
     * @param query
     * @return the prepared statement
     * @throws SQLException
     */
    protected PreparedStatement prepareStatement(final String query) throws SQLException {
        return this.getConnection().prepareStatement(query);
    }
}
