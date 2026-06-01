create table npc_script (
    id bigserial primary key,
    script_name varchar(128) not null unique,
    script_type smallint not null default 0,
    data jsonb not null default '{}'
);

create table story_flag (
    id bigserial primary key,
    character_id bigint not null references "character"(id) on delete cascade,
    flag_name varchar(128) not null,
    flag_value int not null default 0,
    unique(character_id, flag_name)
);

create index idx_story_flag_character on story_flag(character_id);
