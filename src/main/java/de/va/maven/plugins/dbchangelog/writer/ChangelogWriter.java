package de.va.maven.plugins.dbchangelog.writer;

import de.va.maven.plugins.dbchangelog.changelog.Changelog;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public interface ChangelogWriter {

    boolean writeChangelog();
    void moveResourcesToDebugArea();
    void cleanup();

    void init();
    void destroy();
    void prepare();

    void setChangelog(final Changelog changelog);
    Changelog getChangelog();

    void setProject(final MavenProject project);
    MavenProject getProject();

    void setLog(final Log log);
    Log getLog();
}
