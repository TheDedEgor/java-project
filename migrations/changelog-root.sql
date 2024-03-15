--liquibase formatted sql

--changeset egor:1
create table chats (
    id bigserial primary key,
    tg_chat_id bigint unique not null
);

create table links (
    id bigserial primary key,
    url varchar(255) unique not null,
    last_check_time timestamp with time zone
);

create table chats_links (
    id bigserial primary key,
    chat_id bigint references chats,
    link_id bigint references links,
    unique (chat_id, link_id)
);
