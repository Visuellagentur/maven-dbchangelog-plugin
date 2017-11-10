package de.va.maven.plugins.dbchangelog.changelog;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * TBD:document
 *
 * <pre>
 * CHANGELOG
 *   RELEASE
 *     DATABASE
 *       TABLE
 *         CHANGESET
 *           UPDATE|INSERT|DELETE
 *           ...
 *         ...
 *       ...
 *     ...
 *   ...
 * </pre>
 */
public class Changelog extends AbstractParentNode<Changelog, Release> {

    private SortedSet<Release> children = new TreeSet<>();

    public Changelog() {
        super(NodeType.CHANGELOG);
    }

    @Override
    public Changelog getParent() {
        return this;
    }

    @Override
    public void setParent(final Changelog parent) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addChildNode(final Release childNode) {
        if (childNode.getParent() != null && childNode.getParent() != this) {
            throw new IllegalStateException();
        }
        childNode.setParent(this);
        this.children.add(childNode);
    }

    @Override
    public Release findChildByName(final String name) {
        Release result = null;

        for (final Release release : this.children) {
            if (release.getName().equals(name)) {
                result = release;
                break;
            }
        }

        return result;
    }

    @Override
    public Iterator<Release> getChildren() {
        return this.children.iterator();
    }

    @Override
    public void accept(final NodeVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public int compareTo(final AbstractNode<Changelog> other) {
        throw new UnsupportedOperationException();
    }
}
