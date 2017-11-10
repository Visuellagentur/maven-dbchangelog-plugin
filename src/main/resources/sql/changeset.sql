-- Stored procedure for creating new changesetEntities
-- NOTE: this must be run inside a transaction in order to keep
-- the actual changeEntities, the changesetEntities and the changeEntities thereof consistent

DROP PROCEDURE afp_create_changeset;
CREATE PROCEDURE afp_create_changeset
  (
    @provider     VARCHAR(10),
    @user         VARCHAR(255),
    @description  VARCHAR(1024),
    -- one of hotfix, release or patch, defaults to patch
    @release_type VARCHAR(10) = 'patch',
    -- one of content or schema, defaults to content
    @change_type  VARCHAR(10) = 'content',
    -- the target release when known, e.g. 2.1.1, defaults to next
    -- release manager must replace 'unspecified' by the actual release based on the release_type, e.g.
    -- hotfix: this will apply to the release currently in production
    -- patch: this will apply to the release currently in prerelease
    -- release: will be rolled out with either the release currently under development or some future release
    --          for change_type content this always applies to the release currently in daily
    --          for change_type schema this will have to be negotiated
    --          other identifiers are possible, e.g. EPIC ids from youtrack, e.g. DA-123 to make it optional for
    --          a specific developer environment / testing environment to include such changeEntities to the database
    @release      VARCHAR(20) = 'unspecified'
  )
WITH EXECUTE AS OWNER
AS
  SET NOCOUNT ON;

  -- returns the ID of the changesetEntity
  INSERT INTO AirforceHistory.dbo.af_changeset
    ([provider], [timestamp], [user], [description], [release_type], [change_type], [release])
    OUTPUT INSERTED.ID
    VALUES (@provider, GETDATE(), @user, @description, @release_type, @change_type, @release);
GO
