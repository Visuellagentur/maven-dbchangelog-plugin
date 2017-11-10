package de.va.maven.plugins.dbchangelog.changelog;

public interface NodeVisitor {
    void visit(final Changelog node);
    void visit(final Release node);
    void visit(final Database node);
    void visit(final Table node);
    void visit(final Changeset node);
    void visit(final Insert node);
    void visit(final Update node);
    void visit(final Delete node);
}
