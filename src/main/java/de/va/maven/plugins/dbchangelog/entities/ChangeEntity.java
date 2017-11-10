package de.va.maven.plugins.dbchangelog.entities;

import de.va.maven.plugins.dbchangelog.ActionType;

import java.util.Map;

/**
 * The final class ChangeEntity models an entity that holds information on individual changes made on the data or schema
 * of a given table.
 */
public final class ChangeEntity implements Comparable<ChangeEntity> {
    // TODO:schema defines this as bigint
    private int changesetId;
    // TODO:schema defines this as bigint
    private int seqNum;
    private String databaseName;
    private String tableName;
    private ActionType actionType;
    private Map<String, String> oldSelector;
    private Map<String, String> oldValue;
    private Map<String, String> newSelector;
    private Map<String, String> newValue;
    private ChangesetEntity changeset;
    private Map<String, String> selector;

    public int getChangesetId() {
        return this.changesetId;
    }

    public void setChangeset(final ChangesetEntity changeset) {
        this.changeset = changeset;
    }

    public ChangesetEntity getChangeset() {
        return this.changeset;
    }

    public void setChangesetId(int changesetId) {
        this.changesetId = changesetId;
    }

    public int getSeqNum() {
        return this.seqNum;
    }

    public void setSeqNum(int seqNum) {
        this.seqNum = seqNum;
    }

    public String getDatabaseName() {
        return this.databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public ActionType getActionType() {
        return this.actionType;
    }

    public void setActionType(final ActionType actionType) {
        this.actionType = actionType;
    }

    public Map<String, String> getOldSelector() {
        return this.oldSelector;
    }

    public void setOldSelector(Map<String, String> oldSelector) {
        this.oldSelector = oldSelector;
    }

    public Map<String, String> getNewSelector() {
        return this.newSelector;
    }

    public void setNewSelector(Map<String, String> newSelector) {
        this.newSelector = newSelector;
    }

    public Map<String, String> getOldValue() {
        return this.oldValue;
    }

    public void setOldValue(Map<String, String> oldValue) {
        this.oldValue = oldValue;
    }

    public Map<String, String> getNewValue() {
        return this.newValue;
    }

    public void setNewValue(Map<String, String> newValue) {
        this.newValue = newValue;
    }

    @Override
    public int compareTo(final ChangeEntity other) {
        int result = this.getChangesetId() - other.getChangesetId();

        if (result == 0) {
            result = this.getSeqNum() - other.getSeqNum();
        }

        return result;
    }

    @Override
    public int hashCode() {
        return (String.format("%010d", this.getChangesetId()) + String.format("%010d", this.getSeqNum())).hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        boolean result = false;

        if (other instanceof ChangeEntity) {
            return this.hashCode() == other.hashCode();
        }

        return result;
    }

    public void setSelector(Map<String, String> selector) {
        this.selector = selector;
    }

    public Map<String, String> getSelector() {
        return selector;
    }
}
