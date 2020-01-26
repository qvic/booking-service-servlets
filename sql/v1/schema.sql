create table role
(
	id serial not null
		constraint role_pk
			primary key,
	name varchar(16) not null
);

create table "user"
(
	id serial not null
		constraint user_pk
			primary key,
	role_id integer not null
		constraint user_role_id_fk
			references role,
	name varchar(256) not null,
	email varchar(256) not null,
	password text not null
);

create unique index user_email_uindex
	on "user" (email);

create table service_type
(
	id serial not null
		constraint service_type_pk
			primary key,
	name text not null,
	duration_seconds integer not null
);

create table appointment
(
	id serial not null
		constraint appointment_pk
			primary key,
	date timestamp not null,
	worker_id integer not null
		constraint appointment_user_id_fk
			references "user",
	client_id integer not null
		constraint appointment_user_id_fk_2
			references "user",
	service_type_id integer not null
		constraint appointment_service_type_id_fk
			references service_type
);

create table review
(
	id serial not null
		constraint review_pk
			primary key,
	appointment_id integer not null
		constraint review_appointment_id_fk
			references appointment,
	text text not null
);