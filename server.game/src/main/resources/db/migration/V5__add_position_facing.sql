-- The way the character is turned, so a relogin does not always face them down. Values are
-- Direction ordinals, and 0 is DOWN, which is what every existing row was effectively using.

ALTER TABLE characters
  ADD COLUMN position_facing SMALLINT NOT NULL DEFAULT 0;
