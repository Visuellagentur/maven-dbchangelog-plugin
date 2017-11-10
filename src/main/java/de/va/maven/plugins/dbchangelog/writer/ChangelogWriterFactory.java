package de.va.maven.plugins.dbchangelog.writer;

import de.va.maven.plugins.dbchangelog.writer.liquibase.LiquibaseWriter;

public class ChangelogWriterFactory {

    public ChangelogWriter createNewInstance(final String backend) {
        ChangelogWriter result = null;

        switch (backend) {
            case "liquibase":
            {
                result = new LiquibaseWriter();
                break;
            }
            default: {
                throw new IllegalArgumentException("unsupported backend " + backend);
            }
        }

        return result;
    }
}
