alter table "character" add column trainer_id int not null default 0;
alter table "character" drop constraint "character_name_key";
alter table "character" add constraint uq_character_name_trainer unique (name, trainer_id);
