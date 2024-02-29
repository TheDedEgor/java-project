--liquibase formatted sql

--changeset egor:1
create table chats (
    id int primary key,
    name varchar(255)
);

create table links (
    id int primary key,
    url varchar(255) not null,
    chat_id int references chats on delete cascade
)
