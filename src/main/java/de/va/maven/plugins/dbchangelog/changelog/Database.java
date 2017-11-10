package de.va.maven.plugins.dbchangelog.changelog;

public class Database extends AbstractStructuralNode<Release, Table> {

    public Database(final String name) {
        super(NodeType.DATABASE, name);
    }

    @Override
    public void accept(final NodeVisitor visitor) {
        visitor.visit(this);
    }
}
