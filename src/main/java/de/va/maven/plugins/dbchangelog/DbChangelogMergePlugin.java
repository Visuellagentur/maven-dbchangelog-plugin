package de.va.maven.plugins.dbchangelog;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

/**
 * TBD:document
 *
 * TBD:db-changelog:merge
 */
@Mojo(name = "merge", defaultPhase = LifecyclePhase.NONE)
public class DbChangelogMergePlugin extends AbstractMojo {

    @Parameter(defaultValue="${project}", readonly=true, required=true)
    private MavenProject project;

    public void execute() throws MojoExecutionException {
    }
}
