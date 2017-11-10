package de.va.maven.plugins.dbchangelog;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.nio.file.Paths;

/**
 * TBD:document
 *
 * cleans the generated resources from target/liquibase and target/liquibase_debug.
 *
 * TBD:db-changelog:clean
 */
@Mojo(name = "clean", defaultPhase = LifecyclePhase.CLEAN)
public class DbChangelogCleanPlugin extends AbstractMojo {

    @Parameter(defaultValue="${project}", readonly=true, required=true)
    private MavenProject project;

    public void execute() throws MojoExecutionException {
        File rootFolder = new File(Paths.get(this.project.getBasedir().getPath().toString(),
                this.project.getBuild().getOutputDirectory(), Constants.LIQUIBASE).toString());
        if (rootFolder.exists()) {
            FileUtils.deleteDirectory(rootFolder);
        }

        File debugAreaFolder = new File(Paths.get(this.project.getBasedir().getPath().toString(),
                this.project.getBuild().getOutputDirectory(), Constants.DEBUG_AREA).toString());
        if (debugAreaFolder.exists()) {
            FileUtils.deleteDirectory(debugAreaFolder);
        }
    }
}
