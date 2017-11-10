package de.va.maven.plugins.dbchangelog.entities;

import de.va.maven.plugins.dbchangelog.ActionType;

import java.io.Reader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * The final class ChangeEntityDao models a concrete DAO for entities of type ChangeEntity.
 */
public final class ChangeEntityDao extends AbstractDao {

    private static final String FIND_DATABASES_QUERY =
            "SELECT DISTINCT database_name FROM dbchangelog_change WHERE changeset_id = ? ORDER BY database_name ASC";
    private static final String FIND_TABLES_QUERY =
            "SELECT DISTINCT table_name FROM dbchangelog_change WHERE changeset_id = ? AND database_name = ? ORDER BY table_name ASC";
    private static final String QUERY =
            "SELECT * FROM dbchangelog_change WHERE changeset_id = ? AND database_name = ? and table_name = ? ORDER BY seq_num ASC";

    private PreparedStatement statement;
    private PreparedStatement findDatabasesStatement;
    private PreparedStatement findTablesStatement;

    /**
     * Returns a map that maps iterators over change entities to keys formed by concatenation of database names and
     * table names.
     *
     * The reason for this is to ensure that very large changesets will not prevent us from writing these to the file
     * system due to overall memory constraints.
     *
     * @param changesetEntity
     * @param databaseTableMap
     * @return
     * @throws SQLException
     */
    public Map<String, Iterator<ChangeEntity>> findChanges(
            final ChangesetEntity changesetEntity, /* inout */ final Map<String, Set<String>> databaseTableMap) throws SQLException {
        Map<String, Iterator<ChangeEntity>> result = new HashMap<>();

        for (final String databaseName : this.findDatabases(changesetEntity)) {
            if (!databaseTableMap.containsKey(databaseName)) {
                databaseTableMap.put(databaseName, new TreeSet<>());
            }
            Set<String> tables = databaseTableMap.get(databaseName);
            for (final String tableName : this.findTables(changesetEntity, databaseName)) {
                tables.add(tableName);
                this.statement.setInt(1, changesetEntity.getId());
                this.statement.setString(2, databaseName);
                this.statement.setString(3, tableName);
                if (this.statement.execute()) {
                    ResultSet resultSet = this.statement.getResultSet();
                    result.put(databaseName + "#" + tableName, new Iterator<ChangeEntity>() {

                        private boolean done = false;

                        @Override
                        public boolean hasNext() {
                            boolean result = false;

                            if (!this.done) {
                                try {
                                    result = resultSet.next();
                                    if (this.done) {
                                        resultSet.close();
                                        this.done = true;
                                    }
                                } catch (final SQLException ex) {
                                    close(resultSet);
                                    throw new UncheckedSQLException(ex);
                                }
                            }

                            return result;
                        }

                        @Override
                        public ChangeEntity next() {
                            ChangeEntity result;

                            if (this.done) {
                                throw new IllegalStateException();
                            }

                            try {
                                result = createNewChangeEntity(changesetEntity, resultSet);
                            } catch (final SQLException ex) {
                                close(resultSet);
                                throw new UncheckedSQLException(ex);
                            }

                            return result;
                        }
                    });
                }
            }
        }

        return result;
    }

    public void init() throws SQLException {
        this.statement = this.prepareStatement(ChangeEntityDao.QUERY);
        this.findDatabasesStatement = this.prepareStatement(ChangeEntityDao.FIND_DATABASES_QUERY);
        this.findTablesStatement = this.prepareStatement(ChangeEntityDao.FIND_TABLES_QUERY);
    }

    public void destroy() {
        this.close(this.statement);
        this.close(this.findDatabasesStatement);
        this.close(this.findTablesStatement);
        this.statement = null;
        this.findDatabasesStatement = null;
        this.findTablesStatement = null;
        super.destroy();
    }

    private List<String> findDatabases(final ChangesetEntity changesetEntity) throws SQLException {
        List<String> result = null;

        ResultSet resultSet = null;
        try {
            this.findDatabasesStatement.setInt(1, changesetEntity.getId());
            if (this.findDatabasesStatement.execute()) {
                resultSet = this.findDatabasesStatement.getResultSet();
                result = this.asList(resultSet);
            }
        }
        finally {
            this.close(resultSet);
        }

        return result;
    }

    private List<String> findTables(final ChangesetEntity changesetEntity, final String databaseName) throws SQLException {
        List<String> result = null;

        ResultSet resultSet = null;
        try {
            this.findTablesStatement.setInt(1, changesetEntity.getId());
            this.findTablesStatement.setString(2, databaseName);
            if (this.findTablesStatement.execute()) {
                resultSet = this.findTablesStatement.getResultSet();
                result = this.asList(resultSet);
            }
        }
        finally {
            this.close(resultSet);
        }

        return result;
    }

    private ChangeEntity createNewChangeEntity(final ChangesetEntity changesetEntity, final ResultSet resultSet) throws SQLException {
        final ChangeEntity result = new ChangeEntity();

        result.setChangeset(changesetEntity);
        // TODO:schema defines this as bigint
        result.setChangesetId(resultSet.getInt(1));
        // TODO:schema defines this as bigint
        result.setSeqNum(resultSet.getInt(2));
        final ActionType actionType = ActionType.fromName(resultSet.getString(3));
        result.setActionType(actionType);
        /*
         * for now, we do support content results, only
         * for alterations to the schema use the liquibase maven plugin or similar such tools
         */
        result.setOldSelector(this.fromJson(resultSet.getCharacterStream(4)));
        result.setNewSelector(this.fromJson(resultSet.getCharacterStream(6)));
        /*
         * THINK:we do not support lobs in the state
         */
        result.setOldValue(this.fromJson(resultSet.getCharacterStream(5)));
        result.setNewValue(this.fromJson(resultSet.getCharacterStream(7)));

        return result;
    }

    private Map<String, String> fromJson(final Reader reader) {
        Map<String, String> result = new HashMap<>();

        throw new RuntimeException("not implemented yet");
    }

    private List<String> asList(final ResultSet resultSet) throws SQLException {
        List<String> result = new ArrayList<>();

        while (resultSet.next()) {
            result.add(resultSet.getString(1));
        }

        return result;
    }
}
