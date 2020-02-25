INSERT INTO public."user" (id, name, email, password, role) VALUES (100, 'Admin Name', 'admin@email.com', '$2a$10$Eghb.PWqTJE0TejrOLVZHuA/yyxgDWxGi4ncZrTgqYzZ3rLIxUp6e', 'ADMIN');
INSERT INTO public."user" (id, name, email, password, role) VALUES (101, 'Worker Name', 'worker@email.com', '$2a$10$Eghb.PWqTJE0TejrOLVZHuA/yyxgDWxGi4ncZrTgqYzZ3rLIxUp6e', 'WORKER');
INSERT INTO public."user" (id, name, email, password, role) VALUES (102, 'Client Name', 'client@email.com', '$2a$10$Eghb.PWqTJE0TejrOLVZHuA/yyxgDWxGi4ncZrTgqYzZ3rLIxUp6e', 'CLIENT');

INSERT INTO public.duration (id, minutes) VALUES (1, 30);

INSERT INTO public.service (id, name, duration_minutes, price) VALUES (101, 'SPA', 50, 500);
INSERT INTO public.service (id, name, duration_minutes, price) VALUES (102, 'Manicure', 20, 100);

INSERT INTO public."order" (id, date, worker_id, client_id, service_id) VALUES (111, '2020-02-05 15:21:06.000000', 101, 102, 102);
INSERT INTO public."order" (id, date, worker_id, client_id, service_id) VALUES (112, '2020-02-05 15:21:06.000000', 101, 102, 102);
INSERT INTO public."order" (id, date, worker_id, client_id, service_id) VALUES (113, '2020-02-05 15:21:06.000000', 101, 102, 101);

INSERT INTO public.feedback (id, text, order_id, status) VALUES (100, 'What?', 111, 'CREATED');
INSERT INTO public.feedback (id, text, order_id, status) VALUES (101, 'Good.', 111, 'APPROVED');
INSERT INTO public.feedback (id, text, order_id, status) VALUES (102, 'Very nice!', 112, 'APPROVED');

INSERT INTO public.timeslot (id, from_time, date, duration_id) VALUES (101, '08:30:00', '2020-02-17', 1);
INSERT INTO public.timeslot (id, from_time, date, duration_id) VALUES (102, '08:00:00', '2020-02-18', 1);
INSERT INTO public.timeslot (id, from_time, date, duration_id) VALUES (103, '08:00:00', '2020-02-19', 1);
INSERT INTO public.timeslot (id, from_time, date, duration_id) VALUES (104, '12:30:00', '2020-02-18', 1);
INSERT INTO public.timeslot (id, from_time, date, duration_id) VALUES (105, '12:00:00', '2020-02-17', 1);
INSERT INTO public.timeslot (id, from_time, date, duration_id) VALUES (106, '13:00:00', '2020-02-20', 1);
INSERT INTO public.timeslot (id, from_time, date, duration_id) VALUES (107, '13:30:00', '2020-02-20', 1);

INSERT INTO public.order_timeslot (id, order_id, timeslot_id) VALUES (101, 111, 101);
INSERT INTO public.order_timeslot (id, order_id, timeslot_id) VALUES (102, 112, 103);
INSERT INTO public.order_timeslot (id, order_id, timeslot_id) VALUES (103, 113, 106);
INSERT INTO public.order_timeslot (id, order_id, timeslot_id) VALUES (104, 113, 107);