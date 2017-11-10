package de.va.maven.plugins.dbchangelog.changelog;

import de.va.maven.plugins.dbchangelog.ChangeType;

import java.util.Iterator;

// TODO:this will fail on very large changesets (memory constraint), so we have to stream existing change child nodes
public class Changeset extends AbstractParentNode<Table, AbstractChangeNode> {

    private Iterator<AbstractChangeNode> children;
    private final int id;
    private final ChangeType changeType;
    private final String provider;
    private final String author;
    private final String description;

    public Changeset(final int id, final ChangeType changeType, final String provider, final String author,
                     final String description) {
        super(NodeType.CHANGESET);
        this.id = id;
        this.changeType = changeType;
        this.provider = provider;
        this.author = author;
        this.description = description;
    }

    public int getId() {
        return this.id;
    }

    /*
     * e.g.
     * 0000000010-content-hotfix
     * 0000000011-schema-patch
     */
    public String getQid() {
        final Release release = this.getParent().getParent().getParent();
        return String.format("%010d", this.getId()) + "-" + this.getChangeType() + "-" + release.getReleaseType();
    }

    void setChildren(final Iterator<AbstractChangeNode> children) {
        this.children = children;
    }

    public ChangeType getChangeType() {
        return this.changeType;
    }

    public String getProvider() {
        return this.provider;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public void accept(final NodeVisitor visitor) {
        visitor.visit(this);
    }

    // Since we stream changes from the database to the filesystem we don't expect this to be called at all
    @Override
    public AbstractChangeNode findChildByName(final String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<AbstractChangeNode> getChildren() {
        return this.children;
    }

    @Override
    public void addChildNode(final AbstractChangeNode childNode) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int compareTo(final AbstractNode<Table> other) {
        int result;

        if (other == null) {
            throw new NullPointerException();
        }
        if (other instanceof Changeset) {
            result = this.getId() - ((Changeset) other).getId();
        }
        else {
            result = -1;
        }

        return result;
    }
}
