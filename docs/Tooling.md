# Tooling


## TTMT

### Short Term Action

Extend the existing stored procedure to write required changelog information to the af_changelog database.
Extend TTMT to provide us with additional meta data (insert, update, delete).

### Mid Term Action

Add support for exporting to liquibase changelogs.

### Long Term Action

Add support for releases, branching and tagging of the existing content.
Direct export to CSV a/o Excel.
Provide for REST APIs to remote control the system.
Get rid of the stored procedure.


## Pricing

### Short Term Action

Extend the existing stored procedure to write required changelog information to the af_changelog database.

### Mid Term Action

Implement a new tool for maintaining the price data and price lists.
Keep everything in a separate database.
Provide us with additional meta data (insert, update, delete).
Export to CSV a/o Excel which can then be used for import with the MTT.

### Long Term Action

Add support for releases, branching and tagging of the existing content.
Provide for REST APIs to remote control the system.
Add support for exporting to liquibase changelogs.
Get rid of the stored procedure.


## Controls

### Short Term Action

Begin designing the new tool which should be most versatile in order to be used for different purposes,
such as maintaining price lists, prices and of course control data.

### Mid Term Action

Implement a new tool for maintaining the price data and price lists.
Keep everything in a separate database.
Provide us with additional meta data (insert, update, delete).
Export to CSV a/o Excel which can then be used for import with the MTT.

### Long Term Action

Add support for releases, branching and tagging of the existing content.
Provide for REST APIs to remote control the system.
Add support for exporting to liquibase changelogs.


## Maintenance Table Tool (MTT)

### Short Term Action

Fix single transaction issue.
Extend the GUI to provide additional information, i.e. RELEASE, RELEASE_TYPE, CHANGE_TYPE, DESCRIPTION.
Write required information to the af_changelog database.

### Mid Term Action

n/a

### Long Term Action

Remove the darn thing.


## Other?

* User Maintenance Tool (UMT)
  Must be replaced by a custom user management plugin which replaces the Tacton plugins.
  Existing Tacton plugins for SiteUsers and Users must be hidden from the GUI.
  Rename to Application Users.

* Modeling: 2D/3D Model data and assets?

...
