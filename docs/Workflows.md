# Workflows

## Content Changes

The plugin supports a semi automated process. Most of the time you will not have to do anything, yet, situations may arise where the
automation fails. Therefore, the overall process of generating the change logs, reviewing them, and merging them with the current source base
and finally committing these changes to the repository are part of your responsibilities.

* run mvn airforce:generate-changelog
* review content under <target>/liquibase, looking good?, continue
* run mvn airforce:merge
   this will merge the generated sources into the existing sources under src/main/resources/liquibase
   * now apply your changes, e.g. add missing preconditions/contexts or remove invalid ones
   * in case of a premature merge, say you forgot something, undo the merge by simply running git checkout src/main/resources/liquibase which will restore the original content
    and start over
   * of course, you're fucked if you already changed existing content
* for now, never run git commit ... before you are absolutely sure that everything is in order
* finally, run git commit

* and never run git push origin until you are absolutely sure unless you are working on your own branch
* if you fucked up and pushed to origin/master, your best bet is to revert the commit and push that to origin and start over


## Schema Changes

This kind of change is not supported by this plugin. For this, the existing liquibase plugin can be used.

TBD: Workflow using liquibase plugin

