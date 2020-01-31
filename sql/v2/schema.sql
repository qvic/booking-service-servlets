create table role
(
    id   serial      not null
        constraint role_pk
            primary key,
    name varchar(15) not null
);

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

create table order_status
(
    id   serial      not null
        constraint order_status_pk
            primary key,
    name varchar(20) not null
);

create table timeslot
(
    id        serial  not null
        constraint timeslot_pk
            primary key,
    weekday   integer not null,
    from_time time(0) not null,
    to_time   time(0) not null
);

create table review_status
(
    id   serial      not null
        constraint review_status_pk
            primary key,
    name varchar(20) not null
);

create table user_status
(
    id   serial      not null
        constraint user_status_pk
            primary key,
    name varchar(20) not null
);

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

create table "order"
(
    id          serial  not null
        constraint appointment_pk
            primary key,
    date        date    not null,
    worker_id   integer
        constraint order_worker_id_fk
            references "user",
    client_id   integer not null
        constraint order_client_id_fk
            references "user",
    timeslot_id integer not null
        constraint order_timeslot_id_fk
            references timeslot,
    status_id   integer not null
        constraint order_order_status_id_fk
            references order_status
);

create unique index user_email_uindex
    on "user" (email);

create table review
(
    id        serial        not null
        constraint review_pk
            primary key,
    order_id  integer
        constraint review_order_id_fk
            references "order",
    text      varchar(5000) not null,
    status_id integer       not null
        constraint reviews_review_status_id_fk
            references review_status
);

create table order_service
(
    id         serial  not null
        constraint order_service_pk
            primary key,
    order_id   integer not null
        constraint order_service_order_id_fk
            references "order",
    service_id integer not null
        constraint order_service_service_id_fk
            references service
);

