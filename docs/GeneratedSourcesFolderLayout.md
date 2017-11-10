# Folder Layout of the Generated Changelogs

## Newly Generated Changelogs

When invoking the target airforce:generate-changelogs, all change logs will be generated into a liquibase subfolder of the configured target folder.

Folder Structure

```
target/liquibase/
  all.xml
  releases.xml
  hotfixes.xml
  patches.xml
  <release>/
    all.xml
    releases.xml
    hotfixes.xml
    patches.xml
    <databasename>/
      all.xml
      releases.xml
      hotfixes.xml
      patches.xml
      <tablename>/
        all.xml
        releases.xml
        hotfixes.xml
        patches.xml
        <changeset_id>/
          all.xml
          releases.xml
          hotfixes.xml
          patches.xml
          <release>_<id>_<change-type>_<release-type>.xml
```

One may now use these generated changelogs to for example test these first prior to merging them into the
existing sources.

BE AWARE that these generated resources live under the target folder and will be removed once mvn clean
is invoked. So do not change existing generated content unless you know what you are doing.


Example

```
target/liquibase/
  all.xml
  2_1_0/
    all.xml
    ...
    AirforceProductData/
      all.xml
      ...
      IMTS_GUITEXT/
        ...
  2.2.0/
    ...
```


## Merging Into the Existing Codebase

When invoking airforce:merge-changelogs, all generated change logs will be merged into the existing sources.

Folder Structure

```
src/main/resources/liquibase/
  all.xml
  releases.xml
  hotfixes.xml
  patches.xml
  <release>/
    ...
```
