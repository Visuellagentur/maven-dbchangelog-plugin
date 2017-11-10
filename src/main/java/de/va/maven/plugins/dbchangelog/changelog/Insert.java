package de.va.maven.plugins.dbchangelog.changelog;

import java.util.Map;

public class Insert extends AbstractContentChangeNode {

    public Insert(final Map<String, String> newSelector, final Map<String, String> newState) {
        super(NodeType.INSERT, null, newSelector, null, newState);
    }

    @Override
    public void accept(final NodeVisitor visitor) {
        visitor.visit(this);
    }
}
