-- Entity ids come from EntityIdService. The low 16 bits tag the kind
-- (0x9000 characters, 0xC000 monsters) and the wire protocol depends on that tag.

CREATE TABLE characters (
  id                     BIGINT      NOT NULL PRIMARY KEY,
  -- Account id from the login database. That is another database, so no FK.
  user_id                INT         NOT NULL,
  name                   VARCHAR(32) NOT NULL,
  name_prefix            VARCHAR(32) NOT NULL DEFAULT '',
  rival_sex              SMALLINT    NOT NULL,
  last_login             TIMESTAMP   NOT NULL,
  created_at             TIMESTAMP   NOT NULL,
  money                  INT         NOT NULL,
  permissions            INT         NOT NULL,
  remaining_safari_steps SMALLINT    NOT NULL,
  remaining_safari_balls SMALLINT    NOT NULL,
  pc_extra_slots         SMALLINT    NOT NULL,
  battle_box_extra_slots SMALLINT    NOT NULL,
  template_amount        SMALLINT    NOT NULL,
  position_region_id     SMALLINT    NOT NULL,
  position_bank_id       SMALLINT    NOT NULL,
  position_map_id        SMALLINT    NOT NULL,
  position_x             SMALLINT    NOT NULL,
  position_y             SMALLINT    NOT NULL,
  repel_left             SMALLINT    NOT NULL,
  repel_item_id          SMALLINT    NOT NULL,
  lure_left              SMALLINT    NOT NULL,
  lure_item_id           SMALLINT    NOT NULL
);

CREATE INDEX idx_characters_user_id ON characters (user_id);
CREATE INDEX idx_characters_name ON characters (name);

CREATE TABLE pokemon (
  id                   BIGINT      NOT NULL PRIMARY KEY,
  owner_id             BIGINT      NOT NULL REFERENCES characters (id) ON DELETE CASCADE,
  -- PokemonContainer enum name. Renaming an enum entry breaks stored data.
  container            VARCHAR(16) NOT NULL,
  container_slot       SMALLINT    NOT NULL,
  dex_id               INT         NOT NULL,
  -- Nature is derived from the seed and not stored separately.
  seed                 INT         NOT NULL,
  ot                   VARCHAR(32) NOT NULL,
  nickname             VARCHAR(32) NOT NULL DEFAULT '',
  pokemon_level        SMALLINT    NOT NULL,
  hp                   SMALLINT    NOT NULL,
  xp                   INT         NOT NULL,
  ev_hp                SMALLINT    NOT NULL DEFAULT 0,
  ev_atk               SMALLINT    NOT NULL DEFAULT 0,
  ev_def               SMALLINT    NOT NULL DEFAULT 0,
  ev_sp_atk            SMALLINT    NOT NULL DEFAULT 0,
  ev_sp_def            SMALLINT    NOT NULL DEFAULT 0,
  ev_spd               SMALLINT    NOT NULL DEFAULT 0,
  iv_hp                SMALLINT    NOT NULL DEFAULT 0,
  iv_atk               SMALLINT    NOT NULL DEFAULT 0,
  iv_def               SMALLINT    NOT NULL DEFAULT 0,
  iv_sp_atk            SMALLINT    NOT NULL DEFAULT 0,
  iv_sp_def            SMALLINT    NOT NULL DEFAULT 0,
  iv_spd               SMALLINT    NOT NULL DEFAULT 0,
  move1_id             SMALLINT    NOT NULL DEFAULT 0,
  move1_pp             SMALLINT    NOT NULL DEFAULT 0,
  move2_id             SMALLINT    NOT NULL DEFAULT 0,
  move2_pp             SMALLINT    NOT NULL DEFAULT 0,
  move3_id             SMALLINT    NOT NULL DEFAULT 0,
  move3_pp             SMALLINT    NOT NULL DEFAULT 0,
  move4_id             SMALLINT    NOT NULL DEFAULT 0,
  move4_pp             SMALLINT    NOT NULL DEFAULT 0,
  is_shiny             BOOLEAN     NOT NULL DEFAULT FALSE,
  has_hidden_ability   BOOLEAN     NOT NULL DEFAULT FALSE,
  is_alpha             BOOLEAN     NOT NULL DEFAULT FALSE,
  is_secret            BOOLEAN     NOT NULL DEFAULT FALSE,
  is_fateful_encounter BOOLEAN     NOT NULL DEFAULT FALSE,
  is_raid_encounter    BOOLEAN     NOT NULL DEFAULT FALSE,
  is_egg               BOOLEAN     NOT NULL DEFAULT FALSE,
  caught_at            TIMESTAMP   NOT NULL
);

CREATE INDEX idx_pokemon_owner ON pokemon (owner_id);
CREATE UNIQUE INDEX uq_pokemon_owner_container_slot ON pokemon (owner_id, container, container_slot);

CREATE TABLE character_items (
  character_id BIGINT NOT NULL REFERENCES characters (id) ON DELETE CASCADE,
  item_id      INT    NOT NULL,
  quantity     INT    NOT NULL,
  PRIMARY KEY (character_id, item_id)
);
