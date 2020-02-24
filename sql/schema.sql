create table if not exists "user"
(
    id       serial       not null
        constraint user_pk
            primary key,
    name     varchar(200) not null,
    email    varchar(254) not null,
    password varchar(128) not null,
    role     varchar(10)  not null
);

create unique index if not exists user_email_uindex
    on "user" (email);

create table if not exists service
(
    id               serial       not null
        constraint service_type_pk
            primary key,
    name             varchar(100) not null,
    duration_minutes integer      not null,
    price            integer      not null
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
    service_id integer   not null
        constraint order_service_id_fk
            references service
);

create table if not exists feedback
(
    id       serial        not null
        constraint review_pk
            primary key,
    text     varchar(5000) not null,
    status   varchar(10)   not null,
    order_id integer       not null
        constraint feedback_order_id_fk
            references "order"
);

create table if not exists duration
(
    id      serial  not null
        constraint duration_pk
            primary key,
    minutes integer not null
);

create table if not exists timeslot
(
    id          serial  not null
        constraint timeslot_pk
            primary key,
    from_time   time(0) not null,
    date        date    not null,
    duration_id integer not null
        constraint timeslot_duration_id_fk
            references duration
);

create table if not exists notification
(
    id       serial  not null
        constraint notification_pk
            primary key,
    order_id integer not null
        constraint notification_order_id_fk
            references "order",
    read     boolean not null
);

create table if not exists order_timeslot
(
    id          serial  not null
        constraint order_timeslot_pk
            primary key,
    order_id    integer not null
        constraint order_timeslot_order_id_fk
            references "order",
    timeslot_id integer not null
        constraint order_timeslot_timeslot_id_fk
            references timeslot
);

