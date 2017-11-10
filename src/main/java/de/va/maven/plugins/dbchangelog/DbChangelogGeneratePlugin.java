package de.va.maven.plugins.dbchangelog;

import de.va.maven.plugins.dbchangelog.changelog.Changelog;
import de.va.maven.plugins.dbchangelog.changelog.ChangelogBuilder;
import de.va.maven.plugins.dbchangelog.entities.ChangesetManager;
import de.va.maven.plugins.dbchangelog.entities.UncheckedSQLException;
import eu.coldrye.plugins.maven.db_changelog_plugin.models.Changelog;
import de.va.maven.plugins.dbchangelog.writer.liquibase.LiquibaseWriter;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.IOException;
import java.sql.SQLException;

/**
 * TBD:document
 *
 * The actual schema for these tables can be found under resources/liquibase/schema.xml.
 *
 * TBD:db-changelog:generate
 */
@Mojo(name = "generate", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class DbChangelogGeneratePlugin extends AbstractMojo {

    @Parameter(defaultValue="${project}", readonly=true)
    private MavenProject project;

    /**
     * DB connection properties need to be defined in the Maven settings.xml.
     * e.g.
     *
     * <DB_CHANGELOG_USERNAME>USERNAME</DB_CHANGELOG_USERNAME>
     * <DB_CHANGELOG_PASSWORD>PASSWORD</DB_CHANGELOG_PASSWORD>
     * <DB_CHANGELOG_DRIVER>e.g. ...</DB_CHANGELOG_DRIVER>
     * <DB_CHANGELOG_URL>e.g. jdbc:microsoft:sqlserver://HOST:1433;DatabaseName=DATABASE</DB_CHANGELOG_URL>
     */
    @Parameter(defaultValue = "${db_changelog.url}", readonly=true)
    private String url;

    @Parameter(defaultValue = "${db_changelog.driver}", readonly=true)
    private String driver;

    @Parameter(defaultValue = "${db_changelog.username}", readonly=true)
    private String username;

    @Parameter(defaultValue = "${db_changelog.password}", readonly=true)
    private String password;

    @Parameter(defaultValue = "${db_changelog.debug}", readonly=true)
    private boolean isDebugEnabled;

    public void execute() throws MojoExecutionException {
        final ChangesetManager changesetManager = new ChangesetManager();
        changesetManager.setLog(this.getLog());
        changesetManager.setConnectionParameters(this.url, this.driver, this.username, this.password);
        final ChangelogBuilder changelogBuilder = new ChangelogBuilder();
        changelogBuilder.setChangesetManager(changesetManager);
        final ChangelogWriter changelogWriter = new LiquibaseWriter();
        changelogWriter.setProject(this.project);
        changelogWriter.setLog(this.getLog());

        try {
            changesetManager.init();
            changelogBuilder.init();
            Changelog changelog = changelogBuilder.build();
            changelogWriter.setChangelog(changelog);
            changelogWriter.init();
            changelogWriter.prepare();
            changelogWriter.writeChangelog();
        }
        catch (final SQLException ex) {
            throw new MojoExecutionException("database access fail", ex);
        }
        catch (final IOException|UncheckedSQLException ex) {
            if (this.isDebugEnabled || this.getLog().isDebugEnabled()) {
                changelogWriter.moveToDebugArea();
            }
            else {
                changelogWriter.cleanup();
            }
            throw new MojoExecutionException("writing changelog fail", ex);
        }
        finally {
            changelogWriter.destroy();
            changelogBuilder.destroy();
            changesetManager.destroy();
        }
    }
}
