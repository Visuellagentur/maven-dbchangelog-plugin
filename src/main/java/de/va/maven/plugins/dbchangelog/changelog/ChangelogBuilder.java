package de.va.maven.plugins.dbchangelog.changelog;

import de.va.maven.plugins.dbchangelog.ReleaseType;
import de.va.maven.plugins.dbchangelog.entities.ChangeEntity;
import de.va.maven.plugins.dbchangelog.entities.ChangesetManager;
import de.va.maven.plugins.dbchangelog.entities.ChangesetEntity;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.Callable;

public class ChangelogBuilder {

    private ChangesetManager changesetManager;

    public Changelog build() throws SQLException {
        final Changelog result = new Changelog();

        SortedSet<ChangesetEntity> changesets = this.changesetManager.retrieveChangesets();

        for (final ChangesetEntity changesetEntity : changesets) {
            Release release = this.findOrCreateRelease(
                    result, changesetEntity.getRelease(), changesetEntity.getReleaseType());
            Map<String, Set<String>> databaseTableMap = changesetEntity.getDatabaseTableMap();
            for (final String databaseName : new TreeSet<>(databaseTableMap.keySet())) {
                Database database = this.findOrCreateDatabase(release, databaseName);
                for (final String tableName : databaseTableMap.get(databaseName)) {
                    Table table = this.findOrCreateTable(database, tableName);
                    Iterator<ChangeEntity> changeEntities = changesetEntity.getChanges(databaseName, tableName);
                    Changeset changeset = new Changeset(changesetEntity.getId(), changesetEntity.getChangeType(),
                            changesetEntity.getProvider(), changesetEntity.getUsername(),
                            changesetEntity.getDescription());
                    Iterator<AbstractChangeNode> changes = new Iterator<AbstractChangeNode>() {
                        @Override
                        public boolean hasNext() {
                            return changeEntities.hasNext();
                        }

                        @Override
                        public AbstractChangeNode next() {
                            return this.createChangeNode(changeEntities.next());
                        }

                        private AbstractChangeNode createChangeNode(final ChangeEntity changeEntity) {
                            AbstractChangeNode result;
                            switch (changeEntity.getActionType()) {
                                case INSERT: {
                                    result = new Insert(changeEntity.getNewSelector(), changeEntity.getNewValue());
                                    break;
                                }
                                case DELETE: {
                                    result = new Delete(changeEntity.getOldSelector(), changeEntity.getOldValue());
                                    break;
                                }
                                case UPDATE: {
                                    result = new Update(changeEntity.getOldSelector(), changeEntity.getNewSelector(),
                                            changeEntity.getOldValue(), changeEntity.getNewValue());
                                    break;
                                }
                                default: {
                                    throw new UnsupportedOperationException();
                                }
                            }
                            result.setParent(changeset);
                            return result;
                        }
                    };
                    changeset.setChildren(changes);
                    table.addChildNode(changeset);
                }
            }
        }

        return result;
    }

    public void setChangesetManager(final ChangesetManager changesetManager) {
        this.changesetManager = changesetManager;
    }

    public void init() {
    }

    public void destroy() {
        this.changesetManager = null;
    }

    private Release findOrCreateRelease(final Changelog changelog, final String version, final ReleaseType releaseType) {
        Release result = changelog.findChildByName(Release.releaseName(version, releaseType));
        if (result == null) {
            result = new Release(version, releaseType);
            changelog.addChildNode(result);
        }
        return result;
    }

    private Database findOrCreateDatabase(final Release release, final String name) {
        Database result = release.findChildByName(name);
        if (result == null) {
            result = new Database(name);
            release.addChildNode(result);
        }
        return result;
    }

    private Table findOrCreateTable(final Database database, final String name) {
        Table result = database.findChildByName(name);
        if (result == null) {
            result = new Table(name);
            database.addChildNode(result);
        }
        return result;
    }
}
