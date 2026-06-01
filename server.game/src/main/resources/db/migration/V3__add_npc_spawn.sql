create table npc_spawn (
    id bigserial primary key,
    region_id smallint not null,
    bank_id smallint not null,
    map_id smallint not null,
    entity_idx smallint not null,
    graphics_id int not null,
    x smallint not null,
    y smallint not null,
    elevation smallint not null default 0,
    movement_type smallint not null default 0,
    range smallint not null default 0,
    trainer_type smallint not null default 0,
    facing smallint not null default 0,
    script varchar(128) not null default '0x0',
    flag varchar(128) not null default ''
);

create index idx_npc_spawn_map on npc_spawn(region_id, bank_id, map_id);
