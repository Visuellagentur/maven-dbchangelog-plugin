package de.va.maven.plugins.dbchangelog.writer;

import de.va.maven.plugins.dbchangelog.changelog.Changelog;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class AbstractChangelogWriter implements ChangelogWriter {

    private MavenProject project;
    private Log log;
    private Changelog changelog;
    private Path rootPath;
    private Path debugAreaPath;

    protected AbstractChangelogWriter() {}

    public void setChangelog(final Changelog changelog) {
        this.changelog = changelog;
    }

    public Changelog getChangelog() {
        return this.changelog;
    }

    public void setLog(final Log log) {
        this.log = log;
    }

    public Log getLog() {
        return this.log;
    }

    public void setProject(final MavenProject project) {
        this.project = project;
    }

    public MavenProject getProject() {
        return this.project;
    }

    public void init() {
        this.rootPath = Paths.get(this.getProject().getBuild().getOutputDirectory(), LIQUIBASE);
        this.debugAreaPath = Paths.get(this.getProject().getBuild().getOutputDirectory(), DEBUG_AREA);
    }

    public void destroy() {
        this.project = null;
        this.log = null;
        this.rootPath = null;
        this.debugAreaPath = null;
        //this.debugAreaFolder = null;
        //this.rootFolder = null;
    }

    public abstract void writeChangelog() throws IOException;

    public void prepare() throws IOException {
    }

    public abstract void moveToDebugArea() throws IOException;

    public abstract void cleanup() throws IOException;

}
