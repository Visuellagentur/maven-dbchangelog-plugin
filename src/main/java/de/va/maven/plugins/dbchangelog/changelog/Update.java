package de.va.maven.plugins.dbchangelog.changelog;

import java.util.Map;

public class Update extends AbstractContentChangeNode {

    public Update(final Map<String, String> oldSelector, final Map<String, String> newSelector,
                  final Map<String, String> oldState, final Map<String, String> newState) {
        super(NodeType.UPDATE, oldSelector, newSelector, oldState, newState);
    }

    @Override
    public void accept(final NodeVisitor visitor) {
        visitor.visit(this);
    }
}
