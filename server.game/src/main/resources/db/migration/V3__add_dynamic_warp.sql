-- Runtime warp destination set by a script (the decomp setdynamicwarp). A MAP_DYNAMIC warp tile
-- resolves to this when the player steps on it. All null means no dynamic warp is set.

ALTER TABLE characters
  ADD COLUMN dynamic_warp_region SMALLINT,
  ADD COLUMN dynamic_warp_bank   SMALLINT,
  ADD COLUMN dynamic_warp_map    SMALLINT,
  ADD COLUMN dynamic_warp_x      SMALLINT,
  ADD COLUMN dynamic_warp_y      SMALLINT,
  ADD COLUMN dynamic_warp_facing SMALLINT;
