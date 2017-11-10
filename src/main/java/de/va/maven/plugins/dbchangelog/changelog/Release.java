package de.va.maven.plugins.dbchangelog.changelog;

import de.va.maven.plugins.dbchangelog.ReleaseType;

public class Release extends AbstractStructuralNode<Changelog, Database> {

    private final String version;
    private final ReleaseType releaseType;

    // release names will have the name, which is the version, and their release type as a postfix
    public Release(final String version, final ReleaseType releaseType) {
        super(NodeType.RELEASE, Release.releaseName(version, releaseType));
        this.version = version;
        this.releaseType = releaseType;
    }

    public String getVersion() {
        return this.version;
    }

    public ReleaseType getReleaseType() {
        return this.releaseType;
    }

    @Override
    public void accept(final NodeVisitor visitor) {
        visitor.visit(this);
    }

    public static String releaseName(final String version, final ReleaseType releaseType) {
        return version + "-" + releaseType.name().toLowerCase();
    }
}
