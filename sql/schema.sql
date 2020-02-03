drop table if exists role;

create table role
(
    id   serial      not null
        constraint role_pk
            primary key,
    name varchar(15) not null
);

drop table if exists service;

create table service
(
    id                 serial       not null
        constraint service_type_pk
            primary key,
    name               varchar(100) not null,
    duration_timeslots integer      not null,
    price              integer      not null,
    workspaces         integer      not null
);

drop table if exists order_status;

create table order_status
(
    id   serial      not null
        constraint order_status_pk
            primary key,
    name varchar(20) not null
);

drop table if exists timeslot;

create table timeslot
(
    id        serial  not null
        constraint timeslot_pk
            primary key,
    from_time time(0) not null,
    to_time   time(0) not null,
    date      date    not null
);

drop table if exists feedback_status;

create table feedback_status
(
    id   serial      not null
        constraint review_status_pk
            primary key,
    name varchar(20) not null
);

drop table if exists user_status;

create table user_status
(
    id   serial      not null
        constraint user_status_pk
            primary key,
    name varchar(20) not null
);

drop table if exists "user";

create table "user"
(
    id        serial       not null
        constraint user_pk
            primary key,
    role_id   integer      not null
        constraint user_role_id_fk
            references role,
    name      varchar(200) not null,
    email     varchar(254) not null,
    password  varchar(128) not null,
    status_id integer      not null
        constraint user_user_status_id_fk
            references user_status
);

drop table if exists "order";

create table "order"
(
    id          serial    not null
        constraint order_pk
            primary key,
    date        timestamp not null,
    worker_id   integer
        constraint order_worker_id_fk
            references "user",
    client_id   integer   not null
        constraint order_client_id_fk
            references "user",
    timeslot_id integer   not null
        constraint order_timeslot_id_fk
            references timeslot,
    status_id   integer   not null
        constraint order_order_status_id_fk
            references order_status,
    service_id  integer   not null
        constraint order_service_id_fk
            references order_status
);

create unique index user_email_uindex
    on "user" (email);

drop table if exists feedback;

create table feedback
(
    id        serial        not null
        constraint feedback_pk
            primary key,
    text      varchar(5000) not null,
    status_id integer       not null
        constraint feedback_feedback_status_id_fk
            references feedback_status,
    worker_id integer       not null
        constraint feedback_user_id_fk
            references "user"
);