package de.va.maven.plugins.dbchangelog.transformer;

import java.io.File;
import java.util.SortedSet;

public interface Resource<T> {

    File getFile();
    SortedSet<Include> getIncludes();
    SortedSet<Dependency> getDependencies();
    T getResource();
}
