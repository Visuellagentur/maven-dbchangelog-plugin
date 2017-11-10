package de.va.maven.plugins.dbchangelog.entities;

import de.va.maven.plugins.dbchangelog.ChangeType;
import de.va.maven.plugins.dbchangelog.ReleaseType;

import java.sql.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * The final class ChangesetEntity models an entity that describes a changeset in the database changelog.
 */
public final class ChangesetEntity implements Comparable<ChangesetEntity> {
    // TODO:schema defines this as bigint
    private int id;
    private Date timestamp;
    private String provider;
    private ReleaseType releaseType;
    private ChangeType changeType;
    private String release;
    private String username;
    private String description;
    private Map<String, Set<String>> databaseTableMap;
    private Map<String, Iterator<ChangeEntity>> changes;

    public void setDatabaseTableMap(final Map<String, Set<String>> databaseTableMap) {
        this.databaseTableMap = databaseTableMap;
    }

    public Map<String, Set<String>> getDatabaseTableMap() {
        return this.databaseTableMap;
    }

    // key: <databaseName>#<tableName>
    public Iterator<ChangeEntity> getChanges(final String databaseName, final String tableName) {
        return this.changes.get(databaseName + "#" + tableName);
    }

    public void setChanges(final Map<String, Iterator<ChangeEntity>> changes) {
        this.changes = changes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRelease() {
        return this.release;
    }

    public void setRelease(final String release) {
        this.release = release;
    }

    public ReleaseType getReleaseType() {
        return this.releaseType;
    }

    public void setReleaseType(final ReleaseType releaseType) {
        this.releaseType = releaseType;
    }

    public ChangeType getChangeType() {
        return this.changeType;
    }

    public void setChangeType(final ChangeType changeType) {
        this.changeType = changeType;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(final Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * TODO:refactor to transformer
     *
     * Returns a normalized version of the description where all opening and closing XML comments have been escaped so
     * that it can be included safely in for example Liquibase XML changelogs.
     *
     * @return the normalized description
     */
    public String getNormalizedDescription() {
        return this.getDescription().
                replaceAll("[-]+>", "->").
                replaceAll("<![-]+", "<-");
    }

    public String getProvider() {
        return this.provider;
    }

    public void setProvider(final String provider) {
        this.provider = provider;
    }

    @Override
    public int compareTo(final ChangesetEntity other) {
        return this.getId() - other.getId();
    }

    @Override
    public int hashCode() {
        return this.getId();
    }

    @Override
    public boolean equals(final Object other) {
        boolean result = false;

        if (other instanceof ChangesetEntity){
            result = this.hashCode() == other.hashCode();
        }

        return result;
    }
}
