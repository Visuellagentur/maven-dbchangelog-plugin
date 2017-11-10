package de.va.maven.plugins.dbchangelog.changelog;

import java.util.Iterator;

/**
 * The final class DepthFirstTreeTraversal models a strategy for traversing the changelog tree using a depth first
 * approach. During the traversal, the specified visitors will be applied to each node in the tree.
 */
public final class DepthFirstTreeTraversal {

    private final Class<?>[] stopConditions;

    public DepthFirstTreeTraversal(final Class<?>[] stopConditions) {
        this.stopConditions = stopConditions;
    }

    public void traverse(final AbstractParentNode root, final NodeVisitor[] visitors) {
        this.applyVisitors(root, visitors);
        this.traverseParentNodes(root.getChildren(), visitors);
    }

    private void traverseParentNodes(final Iterator<? extends AbstractParentNode> nodes, final NodeVisitor[] visitors) {
        while (nodes.hasNext()) {
            AbstractParentNode node = nodes.next();
            this.applyVisitors(node, visitors);
            if (!this.mustStop(node)) {
                this.traverseParentNodes(node.getChildren(), visitors);
            }
            else {
                this.traverseLeaveNodes(node.getChildren(), visitors);
            }
        }
    }

    private void traverseLeaveNodes(final Iterator<? extends AbstractChangeNode> nodes, final NodeVisitor[] visitors) {
        while (nodes.hasNext()) {
            this.applyVisitors(nodes.next(), visitors);
        }
    }

    private boolean mustStop(final AbstractNode node) {
        boolean result = false;
        for (final Class<?> stopCondition : this.stopConditions) {
            if (stopCondition.isAssignableFrom(node.getClass())) {
                result = true;
                break;
            }
        }
        return result;
    }

    private void applyVisitors(final AbstractNode node, final NodeVisitor[] visitors) {
        for (final NodeVisitor visitor : visitors) {
            node.accept(visitor);
        }
    }
}
