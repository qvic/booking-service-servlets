create table if not exists role
(
    id   serial      not null
        constraint role_pk
            primary key,
    name varchar(15) not null
);

create table if not exists service
(
    id                 serial       not null
        constraint service_type_pk
            primary key,
    name               varchar(100) not null,
    duration_timeslots integer      not null,
    price              integer      not null,
    workspaces         integer      not null
);

create table if not exists order_status
(
    id   serial      not null
        constraint order_status_pk
            primary key,
    name varchar(20) not null
);

create table if not exists feedback_status
(
    id   serial      not null
        constraint review_status_pk
            primary key,
    name varchar(20) not null
);

create table if not exists user_status
(
    id   serial      not null
        constraint user_status_pk
            primary key,
    name varchar(20) not null
);

create table if not exists "user"
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

create table if not exists "order"
(
    id         serial    not null
        constraint appointment_pk
            primary key,
    date       timestamp not null,
    worker_id  integer
        constraint order_worker_id_fk
            references "user",
    client_id  integer   not null
        constraint order_client_id_fk
            references "user",
    status_id  integer   not null
        constraint order_order_status_id_fk
            references order_status,
    service_id integer   not null
        constraint order_service_id_fk
            references service
);

create unique index if not exists user_email_uindex
    on "user" (email);

create table if not exists feedback
(
    id        serial        not null
        constraint review_pk
            primary key,
    text      varchar(5000) not null,
    status_id integer       not null
        constraint reviews_review_status_id_fk
            references feedback_status,
    worker_id integer       not null
        constraint review_user_id_fk
            references "user"
);

create table if not exists timeslot
(
    id        serial  not null
        constraint timeslot_pk
            primary key,
    from_time time(0) not null,
    to_time   time(0) not null,
    date      date    not null,
    order_id  integer
        constraint timeslot_order_id_fk
            references "order"
);