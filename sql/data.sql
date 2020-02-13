INSERT INTO "user" (id, name, email, password, role) VALUES (20, 'Client Name', 'client@email.com', '$2a$10$Eghb.PWqTJE0TejrOLVZHuA/yyxgDWxGi4ncZrTgqYzZ3rLIxUp6e', 'CLIENT');
INSERT INTO "user" (id, name, email, password, role) VALUES (21, 'Worker Name', 'worker@email.com', '$2a$10$Eghb.PWqTJE0TejrOLVZHuA/yyxgDWxGi4ncZrTgqYzZ3rLIxUp6e', 'WORKER');
INSERT INTO "user" (id, name, email, password, role) VALUES (22, 'Admin Name', 'admin@email.com', '$2a$10$Eghb.PWqTJE0TejrOLVZHuA/yyxgDWxGi4ncZrTgqYzZ3rLIxUp6e', 'ADMIN');

INSERT INTO feedback (id, text, worker_id, status) VALUES (6, 'Very nice!', 21, 'APPROVED');
INSERT INTO feedback (id, text, worker_id, status) VALUES (7, 'Good.', 21, 'APPROVED');
INSERT INTO feedback (id, text, worker_id, status) VALUES (8, 'What?', 21, 'CREATED');

INSERT INTO service (id, name, duration_timeslots, price, workspaces) VALUES (11, 'SPA', 2, 500, 1);
INSERT INTO service (id, name, duration_timeslots, price, workspaces) VALUES (12, 'Manicure', 1, 100, 5);

INSERT INTO "order" (id, date, worker_id, client_id, service_id) VALUES (15, '2020-02-05 15:21:06.000000', 21, 20, 11);
INSERT INTO "order" (id, date, worker_id, client_id, service_id) VALUES (16, '2020-02-05 15:21:06.000000', 21, 20, 12);
INSERT INTO "order" (id, date, worker_id, client_id, service_id) VALUES (17, '2020-02-05 15:21:06.000000', 21, 20, 12);

INSERT INTO timeslot (id, from_time, to_time, date, order_id) VALUES (8, '08:30:00', '09:00:00', '2020-02-10', 15);
INSERT INTO timeslot (id, from_time, to_time, date, order_id) VALUES (9, '12:00:00', '12:30:00', '2020-02-11', 17);
INSERT INTO timeslot (id, from_time, to_time, date, order_id) VALUES (10, '12:30:00', '13:00:00', '2020-02-11', 16);
INSERT INTO timeslot (id, from_time, to_time, date, order_id) VALUES (7, '08:00:00', '08:30:00', '2020-02-10', 15);
