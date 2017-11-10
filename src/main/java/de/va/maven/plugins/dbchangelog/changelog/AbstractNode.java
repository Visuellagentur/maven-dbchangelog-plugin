package de.va.maven.plugins.dbchangelog.changelog;

/**
 * The abstract class AbstractNode models the root of a hierarchy of classes that represent nodes in a changelog tree
 * that gets formed from changeset entities retrieved from a database.
 *
 * @param <P>
 */
public abstract class AbstractNode<P extends AbstractParentNode> implements Comparable<AbstractNode<P>>{
    private final NodeType nodeType;
    private P parent;

    protected AbstractNode(final NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public final NodeType getNodeType() {
        return this.nodeType;
    }

    public P getParent() {
        return this.parent;
    }

    public void setParent(final P parent) {
        this.parent = parent;
    }

    public abstract void accept(final NodeVisitor visitor);
}
