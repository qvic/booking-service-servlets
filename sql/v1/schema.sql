drop table if exists role;

create table role
(
	id serial not null
		constraint role_pk
			primary key,
	name varchar(15) not null
);

drop table if exists "user";

create table "user"
(
	id serial not null
		constraint user_pk
			primary key,
	role_id integer not null
		constraint user_role_id_fk
			references role,
	name varchar(200) not null,
	email varchar(254) not null,
	password varchar(128) not null
);

create unique index user_email_uindex
	on "user" (email);

drop table if exists service;

create table service
(
	id serial not null
		constraint service_type_pk
			primary key,
	name varchar(100) not null,
	duration_seconds integer not null,
	available_workplaces integer not null
);

drop table if exists appointment;

create table appointment
(
	id serial not null
		constraint appointment_pk
			primary key,
	date timestamp not null,
	worker_id integer
		constraint appointment_user_id_fk
			references "user",
	client_id integer not null
		constraint appointment_user_id_fk_2
			references "user",
	service_id integer not null
		constraint appointment_service_type_id_fk
			references service
);

drop table if exists review;

create table review
(
	id serial not null
		constraint review_pk
			primary key,
	appointment_id integer
		constraint review_appointment_id_fk
			references appointment,
	text varchar(5000) not null
);