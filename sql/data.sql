INSERT INTO public."user" (id, name, email, password, role) VALUES (3, 'name', 'name@email.com', '$2a$10$TfLT7/ukXWYnJHgALUKU/edC2.XQcH6vDM.iKIqpvPgMEW.B8PRMa', 'CLIENT');
INSERT INTO public."user" (id, name, email, password, role) VALUES (28, 'name', 'name1@email.com', '$2a$10$TfLT7/ukXWYnJHgALUKU/edC2.XQcH6vDM.iKIqpvPgMEW.B8PRMa', 'CLIENT');
INSERT INTO public."user" (id, name, email, password, role) VALUES (29, 'name', 'name2@email.com', '$2a$10$TfLT7/ukXWYnJHgALUKU/edC2.XQcH6vDM.iKIqpvPgMEW.B8PRMa', 'CLIENT');
INSERT INTO public."user" (id, name, email, password, role) VALUES (30, 'name', 'name3@email.com', '$2a$10$TfLT7/ukXWYnJHgALUKU/edC2.XQcH6vDM.iKIqpvPgMEW.B8PRMa', 'CLIENT');
INSERT INTO public."user" (id, name, email, password, role) VALUES (31, 'name', 'name4@email.com', '$2a$10$TfLT7/ukXWYnJHgALUKU/edC2.XQcH6vDM.iKIqpvPgMEW.B8PRMa', 'CLIENT');
INSERT INTO public."user" (id, name, email, password, role) VALUES (32, 'name', 'name5@email.com', '$2a$10$TfLT7/ukXWYnJHgALUKU/edC2.XQcH6vDM.iKIqpvPgMEW.B8PRMa', 'CLIENT');
INSERT INTO public."user" (id, name, email, password, role) VALUES (33, 'name', 'name6@email.com', '$2a$10$TfLT7/ukXWYnJHgALUKU/edC2.XQcH6vDM.iKIqpvPgMEW.B8PRMa', 'CLIENT');
INSERT INTO public."user" (id, name, email, password, role) VALUES (34, 'name', 'name7@email.com', '$2a$10$TfLT7/ukXWYnJHgALUKU/edC2.XQcH6vDM.iKIqpvPgMEW.B8PRMa', 'CLIENT');
INSERT INTO public."user" (id, name, email, password, role) VALUES (35, 'name', 'name8@email.com', '$2a$10$TfLT7/ukXWYnJHgALUKU/edC2.XQcH6vDM.iKIqpvPgMEW.B8PRMa', 'CLIENT');
INSERT INTO public."user" (id, name, email, password, role) VALUES (36, 'asd', 'default@email.com', '$2a$10$Eghb.PWqTJE0TejrOLVZHuA/yyxgDWxGi4ncZrTgqYzZ3rLIxUp6e', 'CLIENT');
INSERT INTO public."user" (id, name, email, password, role) VALUES (22, 'Admin Name', 'admin@email.com', '$2a$10$Eghb.PWqTJE0TejrOLVZHuA/yyxgDWxGi4ncZrTgqYzZ3rLIxUp6e', 'ADMIN');
INSERT INTO public."user" (id, name, email, password, role) VALUES (21, 'Worker Name', 'worker@email.com', '$2a$10$Eghb.PWqTJE0TejrOLVZHuA/yyxgDWxGi4ncZrTgqYzZ3rLIxUp6e', 'WORKER');
INSERT INTO public."user" (id, name, email, password, role) VALUES (20, 'Client Name', 'client@email.com', '$2a$10$Eghb.PWqTJE0TejrOLVZHuA/yyxgDWxGi4ncZrTgqYzZ3rLIxUp6e', 'CLIENT');

INSERT INTO public.duration (id, minutes) VALUES (1, 30);

INSERT INTO public.feedback (id, text, worker_id, status) VALUES (8, 'What?', 21, 'CREATED');
INSERT INTO public.feedback (id, text, worker_id, status) VALUES (7, 'Good.', 21, 'APPROVED');
INSERT INTO public.feedback (id, text, worker_id, status) VALUES (6, 'Very nice!', 21, 'APPROVED');

INSERT INTO public.service (id, name, duration_minutes, price, workspaces) VALUES (12, 'Manicure', 20, 100, 5);
INSERT INTO public.service (id, name, duration_minutes, price, workspaces) VALUES (11, 'SPA', 50, 500, 1);

INSERT INTO public."order" (id, date, worker_id, client_id, service_id) VALUES (16, '2020-02-05 15:21:06.000000', 21, 20, 12);
INSERT INTO public."order" (id, date, worker_id, client_id, service_id) VALUES (17, '2020-02-05 15:21:06.000000', 21, 20, 12);
INSERT INTO public."order" (id, date, worker_id, client_id, service_id) VALUES (15, '2020-02-05 15:21:06.000000', 21, 20, 11);

INSERT INTO public.timeslot (id, from_time, date, order_id, duration_id) VALUES (8, '08:30:00', '2020-02-17', 15, 1);
INSERT INTO public.timeslot (id, from_time, date, order_id, duration_id) VALUES (7, '08:00:00', '2020-02-18', 17, 1);
INSERT INTO public.timeslot (id, from_time, date, order_id, duration_id) VALUES (11, '08:00:00', '2020-02-19', 15, 1);
INSERT INTO public.timeslot (id, from_time, date, order_id, duration_id) VALUES (10, '12:30:00', '2020-02-18', 16, 1);
INSERT INTO public.timeslot (id, from_time, date, order_id, duration_id) VALUES (9, '12:00:00', '2020-02-17', null, 1);
INSERT INTO public.timeslot (id, from_time, date, order_id, duration_id) VALUES (12, '13:00:00', '2020-02-20', null, 1);