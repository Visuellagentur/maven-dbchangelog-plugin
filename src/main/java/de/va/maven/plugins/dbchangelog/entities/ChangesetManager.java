package de.va.maven.plugins.dbchangelog.entities;

import org.apache.maven.plugin.logging.Log;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

/**
 * The final class ChangesetManager models a manager for currently unprocessed changesets.
 *
 * It provides the client with means to retrieve existing unprocessed changesets and, when done, to finalize these
 * changesets. Upon finalization, the changesets will basically just be marked for being processed.
 */
public final class ChangesetManager {

    private Connection connection;
    private Log log;
    private ChangesetEntityDao changesetEntityDao;
    private ChangeEntityDao changeEntityDao;
    private SortedSet<ChangesetEntity> changesets;
    private String url;
    private String driver;
    private String username;
    private String password;

    public SortedSet<ChangesetEntity> retrieveChangesets() throws SQLException {
        try {
            this.getLog().info("retrieving unprocessed changesets from database");
            this.changesets = this.changesetEntityDao.findUnprocessed();
            for (final ChangesetEntity changesetEntity : this.changesets) {
                this.getLog().info("retrieving changes for changeset " +  changesetEntity.getId());
                Map<String, Set<String>> databaseTableMap = new HashMap<>();
                changesetEntity.setChanges(this.changeEntityDao.findChanges(changesetEntity, databaseTableMap));
                changesetEntity.setDatabaseTableMap(databaseTableMap);
            }
        }
        catch (final SQLException ex) {
            this.getLog().error("there was an error processing the changesets from the database");
            this.closeConnection();
            throw ex;
        }

        return Collections.unmodifiableSortedSet(this.changesets);
    }

    public void finalizeChangesets() throws SQLException {
        if (this.changesets == null) {
            throw new IllegalStateException("changesets have not been retrieved yet");
        }
        try {
            this.changesetEntityDao.markAsProcessed(this.changesets);
        }
        catch (final SQLException ex) {
            this.getLog().error("there was an error finalizing the changesets in the database");
            this.closeConnection();
            throw ex;
        }
    }

    public void setConnectionParameters(final String url, final String driver,
                                        final String username, final String password) {
        this.url = url;
        this.driver = driver;
        this.username = username;
        this.password = password;
    }

    public void setLog(final Log log) {
        this.log = log;
    }

    public Log getLog() {
        return this.log;
    }

    public void init() throws SQLException {
        this.connection = new ConnectionFactory().createNewConnection(
                this.url, this.driver, this.username, this.password);
        this.changesetEntityDao = new ChangesetEntityDao();
        this.changesetEntityDao.setConnection(this.connection);
        this.changesetEntityDao.setLog(this.getLog());
        this.changeEntityDao = new ChangeEntityDao();
        this.changeEntityDao.setConnection(this.connection);
        this.changeEntityDao.setLog(this.getLog());
    }

    public void destroy() {
        this.changesets = null;
        this.changeEntityDao.destroy();
        this.changeEntityDao = null;
        this.changesetEntityDao.destroy();
        this.changesetEntityDao = null;
        this.url = null;
        this.driver = null;
        this.log = null;
        this.username = null;
        this.password = null;
        this.closeConnection();
    }

    private void closeConnection() {
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (final SQLException ex1) {
                this.getLog().warn("there was an error when closing the connection", ex1);
            } finally {
                this.connection = null;
            }
        }
    }
}
