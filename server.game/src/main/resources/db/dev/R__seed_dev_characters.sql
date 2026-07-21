-- One character per dev account, each with the Snivy and Patrat starter party.
-- Only applied when db.seedDev is enabled, never in production.
-- Ids are fixed low values with the kind tag in the low 16 bits. Runtime ids from
-- EntityIdService carry a timestamp head, so they can never collide with these.
INSERT INTO characters (
  id, user_id, name, name_prefix, rival_sex, last_login, created_at, money, permissions,
  remaining_safari_steps, remaining_safari_balls, pc_extra_slots, battle_box_extra_slots,
  template_amount, position_region_id, position_bank_id, position_map_id, position_x,
  position_y, repel_left, repel_item_id, lure_left, lure_item_id
)
VALUES
  (102400, 1, 'Test', '', 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 30000, 8,
   0, 0, 0, 0, 0, 1, 51, 3, 4, 4, 0, 0, 0, 0),
  (364544, 2, 'Test2', '', 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 30000, 8,
   0, 0, 0, 0, 0, 1, 51, 3, 4, 4, 0, 0, 0, 0)
ON CONFLICT (id) DO NOTHING;

INSERT INTO pokemon (
  id, owner_id, container, container_slot, dex_id, seed, ot, pokemon_level, hp, xp,
  move1_id, move1_pp, move2_id, move2_pp, caught_at
)
VALUES
  (180224, 102400, 'PARTY', 0, 495, 0, 'Test', 5, 20, 165, 33, 35, 43, 30, CURRENT_TIMESTAMP),
  (311296, 102400, 'PARTY', 1, 504, 0, 'Test', 3, 12, 27, 33, 35, 0, 0, CURRENT_TIMESTAMP),
  (442368, 364544, 'PARTY', 0, 495, 0, 'Test2', 5, 20, 165, 33, 35, 43, 30, CURRENT_TIMESTAMP),
  (507904, 364544, 'PARTY', 1, 504, 0, 'Test2', 3, 12, 27, 33, 35, 0, 0, CURRENT_TIMESTAMP)
ON CONFLICT (id) DO NOTHING;
