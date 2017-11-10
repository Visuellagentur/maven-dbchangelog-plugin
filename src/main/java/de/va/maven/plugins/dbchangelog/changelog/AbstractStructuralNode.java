package de.va.maven.plugins.dbchangelog.changelog;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * TBD:document
 *
 * structural nodes are of NodeType#RELEASE, NodeType#DATABASE, and NodeType#TABLE.
 */
public abstract class AbstractStructuralNode<P extends AbstractParentNode, C extends AbstractNode> extends AbstractParentNode<P, C> {

    private SortedSet<C> children = new TreeSet<>();
    private final String name;

    protected AbstractStructuralNode(final NodeType nodeType, final String name) {
        super(nodeType);
        this.name = name;
    }

    public Iterator<C> getChildren() {
        return this.children.iterator();
    }

    @Override
    public void addChildNode(final C childNode) {
        if (childNode.getParent() != null && childNode.getParent() != this) {
            throw new IllegalStateException();
        }
        childNode.setParent(this);
        this.children.add(childNode);
    }

    @Override
    public C findChildByName(final String name) {
        C result = null;

        for (final C node : this.children) {
            if (node instanceof AbstractStructuralNode) {
                if (((AbstractStructuralNode) node).getName().equals(name)) {
                    result = node;
                    break;
                }
            }
            else {
                break;
            }
        }

        return result;
    }

    @Override
    public int compareTo(final AbstractNode<P> other) {
        int result;

        if (other == null) {
            throw new NullPointerException();
        }
        if (this.getClass().isAssignableFrom(other.getClass())) {
            result = this.getName().compareTo(((AbstractStructuralNode) other).getName());
        }
        else {
            result = -1;
        }

        return result;
    }

    public String getName() {
        return this.name;
    }
}
