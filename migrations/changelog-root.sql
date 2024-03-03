--liquibase formatted sql

--changeset egor:1
create table chats (
    id bigserial primary key,
    chat_id bigint not null
);

create table links (
    id bigserial primary key,
    url varchar(255) not null
);

create table chats_links (
    id bigserial primary key,
    chat_id bigint references chats,
    link_id bigint references links
);
