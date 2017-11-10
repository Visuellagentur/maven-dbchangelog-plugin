package de.va.maven.plugins.dbchangelog.entities;

import de.va.maven.plugins.dbchangelog.ChangeType;
import de.va.maven.plugins.dbchangelog.ReleaseType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * The final class ChangesetEntityDao models a concrete DAO for entities of type ChangesetEntity.
 */
public class ChangesetEntityDao extends AbstractDao {

    private static final String QUERY_UNPROCESSED = "SELECT * FROM dbchangelog_changeset WHERE processed = 0";
    private static final String UPDATE_CHANGESET = "UPDATE dbchangelog_changeset SET (processed = 1) WHERE id = ?";

    private PreparedStatement statement;
    private PreparedStatement updateStatement;

    public SortedSet<ChangesetEntity> findUnprocessed() throws SQLException {
        final SortedSet<ChangesetEntity> result = new TreeSet<>();

        ResultSet resultSet = null;
        try {
            if (this.statement.execute()) {
                resultSet = this.statement.getResultSet();
                while (resultSet.next()) {
                    final ChangesetEntity changesetEntity = new ChangesetEntity();
                    // TODO:schema defines this as bigint
                    changesetEntity.setId(resultSet.getInt(1));
                    changesetEntity.setTimestamp(resultSet.getDate(2));
                    changesetEntity.setProvider(resultSet.getString(3));
                    changesetEntity.setReleaseType(ReleaseType.fromName(resultSet.getString(4)));
                    changesetEntity.setChangeType(ChangeType.fromName(resultSet.getString(5)));
                    changesetEntity.setUsername(resultSet.getString(6));
                    changesetEntity.setDescription(resultSet.getString(7));
                    result.add(changesetEntity);
                }
            }
        }
        finally {
            this.close(resultSet);
        }

        return result;
    }

    public void markAsProcessed(final SortedSet<ChangesetEntity> changesetEntities) throws SQLException {
        // FIXME:use prepared statement instead
        try {
            for (final ChangesetEntity changesetEntity : changesetEntities) {
                final int id = changesetEntity.getId();
                this.updateStatement.setInt(1, changesetEntity.getId());
                if (!this.updateStatement.execute()) {
                    if (this.updateStatement.getUpdateCount() != 1) {
                        throw new SQLException("update of changesetEntity " + id + " failed");
                    }
                }
                this.commit();
            }
        }
        catch (final SQLException ex) {
            this.rollback();
            throw ex;
        }
    }

    public void init() throws SQLException {
        this.statement = this.prepareStatement(ChangesetEntityDao.QUERY_UNPROCESSED);
        this.updateStatement = this.prepareStatement(ChangesetEntityDao.UPDATE_CHANGESET);
    }

    public void destroy() {
        this.close(this.statement);
        this.statement = null;
        this.close(this.updateStatement);
        this.updateStatement = null;
        super.destroy();
    }
}
