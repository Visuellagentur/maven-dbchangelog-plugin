package de.va.maven.plugins.dbchangelog.writer.liquibase;

import de.va.maven.plugins.dbchangelog.changelog.*;
import de.va.maven.plugins.dbchangelog.writer.ChangelogWriter;
import de.va.maven.plugins.dbchangelog.writer.liquibase.visitors.DirectoryStructureVisitor;

import java.util.Set;

// TODO:use transformer to transform internal representation to liquibase object model
// and then use liquibase serializers to write the results to the filesystem
public class LiquibaseWriter extends ChangelogWriter {

    public boolean writeChangelog(final Changelog changelog) {
        boolean result = true;

        NodeVisitor visitor = new DirectoryStructureVisitor();
        DepthFirstTreeTraversal traversal = new DepthFirstTreeTraversal(new Class<?>[] {Changeset.class});
        traversal.traverse(changelog, new NodeVisitor[] {visitor});

        return result;
    }
}
