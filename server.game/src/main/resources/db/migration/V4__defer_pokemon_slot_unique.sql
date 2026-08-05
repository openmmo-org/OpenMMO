-- Saves update pokemon rows in place, so moving a monster between slots can pass through a state
-- where two rows share one. Upserts arbitrate on the primary key, which stays non deferrable.

DROP INDEX uq_pokemon_owner_container_slot;

ALTER TABLE pokemon
  ADD CONSTRAINT uq_pokemon_owner_container_slot
  UNIQUE (owner_id, container, container_slot) DEFERRABLE INITIALLY DEFERRED;
