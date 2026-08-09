-- Only applied when db.seedDev is enabled, never in production.
-- Fixed low ids, so they never collide with EntityIdService ids, which carry a timestamp head.
-- Party dex ids must stay in Gen 1 to 3, the only range the species registry covers.
-- The delete cascades, so re-seeding drops whatever these characters caught, carried or unlocked.

DELETE FROM characters WHERE id IN (102400, 364544);

INSERT INTO characters (
  id, user_id, name, name_prefix, rival_sex, last_login, created_at, money, permissions,
  remaining_safari_steps, remaining_safari_balls, pc_extra_slots, battle_box_extra_slots,
  template_amount, position_region_id, position_bank_id, position_map_id, position_x,
  position_y, repel_left, repel_item_id, lure_left, lure_item_id
)
-- 264 is the 8 these characters already carried plus CharacterPermissions.DEVELOPER, 0x100.
VALUES
  (102400, 1, 'Test', '', 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 30000, 264,
   0, 0, 0, 0, 0, 1, 51, 3, 4, 4, 0, 0, 0, 0),
  (364544, 2, 'Test2', '', 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 30000, 264,
   0, 0, 0, 0, 0, 1, 51, 3, 4, 4, 0, 0, 0, 0);

INSERT INTO pokemon (
  id, owner_id, container, container_slot, dex_id, seed, ot, pokemon_level, hp, xp,
  move1_id, move1_pp, move2_id, move2_pp, caught_at
)
VALUES
  (180224, 102400, 'PARTY', 0, 1, 0, 'Test', 5, 19, 135, 33, 35, 45, 40, CURRENT_TIMESTAMP),
  (311296, 102400, 'PARTY', 1, 19, 0, 'Test', 3, 14, 27, 33, 35, 39, 30, CURRENT_TIMESTAMP),
  (442368, 364544, 'PARTY', 0, 1, 0, 'Test2', 5, 19, 135, 33, 35, 45, 40, CURRENT_TIMESTAMP),
  (507904, 364544, 'PARTY', 1, 19, 0, 'Test2', 3, 14, 27, 33, 35, 39, 30, CURRENT_TIMESTAMP);

-- Gives the developer bit to characters made through the client, on the dev accounts only.
UPDATE characters SET permissions = permissions | 256 WHERE user_id IN (1, 2);
