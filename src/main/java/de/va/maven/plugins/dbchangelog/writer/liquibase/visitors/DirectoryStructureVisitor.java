package de.va.maven.plugins.dbchangelog.writer.liquibase.visitors;

import de.va.maven.plugins.dbchangelog.changelog.*;

import java.io.File;
import java.io.IOException;

public class DirectoryStructureVisitor implements NodeVisitor {

    public DirectoryStructureVisitor() {}

    // to make this testable without the need for creating any directories in the filesystem
    public void setFileFactory() {
    }

    public void getFileFactory() {
    }

    public void visit(final Changelog node) {
        // TODO:create output root folder
        //this.createDirectory();
    }

    public void visit(final Release node) {
        // TODO:create release directory
        //this.createDirectory();
    }

    public void visit(final Database node) {
        // TODO:create database directory
        //this.createDirectory();
    }

    public void visit(final Table node) {
        // TODO:create table directory
        //this.createDirectory();
    }

    @Override
    public void visit(final Changeset node) {
        // not implemented
    }

    @Override
    public void visit(final Insert node) {
        // not implemented
    }

    @Override
    public void visit(final Update node) {
        // not implemented
    }

    @Override
    public void visit(final Delete node) {
        // not implemented
    }

    private void createDirectory() throws IOException {
        // TODO:use maven commons/apache commons instead
        this.fileFactory.createNewFile();
        new File().mkdir();
    }
}
