package de.va.maven.plugins.dbchangelog.changelog;

public class Table extends AbstractStructuralNode<Database, Changeset> {

    public Table(final String name) {
        super(NodeType.TABLE, name);
    }

    @Override
    public void accept(final NodeVisitor visitor) {
        visitor.visit(this);
    }
}
