package de.va.maven.plugins.dbchangelog.changelog;

import java.util.Iterator;

public abstract class AbstractParentNode<P extends AbstractParentNode, C extends AbstractNode> extends AbstractNode<P> {

    protected AbstractParentNode(final NodeType nodeType) {
        super(nodeType);
    }

    public abstract C findChildByName(final String name);
    public abstract Iterator<C> getChildren();
    public abstract void addChildNode(final C childNode);
}
