package de.va.maven.plugins.dbchangelog.changelog;

import java.util.Map;

public class Delete extends AbstractContentChangeNode {

    public Delete(final Map<String, String> oldSelector, final Map<String, String> oldState) {
        super(NodeType.DELETE, oldSelector, null, oldState, null);
    }

    @Override
    public void accept(final NodeVisitor visitor) {
        visitor.visit(this);
    }
}
