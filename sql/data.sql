INSERT INTO role (id, name) VALUES (1, 'CLIENT');
INSERT INTO role (id, name) VALUES (2, 'WORKER');
INSERT INTO role (id, name) VALUES (3, 'ADMIN');
INSERT INTO user_status (id, name) VALUES (1, 'ACTIVE');
INSERT INTO user_status (id, name) VALUES (2, 'DEACTIVATED');
INSERT INTO "user" (id, role_id, name, email, password, status_id) VALUES (20, 1, 'Client Name', 'client@email.com', '2908d2c28dfc047741fc590a026ffade237ab2ba7e1266f010fe49bde548b5987a534a86655a0d17f336588e540cd66f67234b152bbb645b4bb85758a1325d64', 1);
INSERT INTO "user" (id, role_id, name, email, password, status_id) VALUES (21, 2, 'Worker Name', 'worker@email.com', '2908d2c28dfc047741fc590a026ffade237ab2ba7e1266f010fe49bde548b5987a534a86655a0d17f336588e540cd66f67234b152bbb645b4bb85758a1325d64', 1);
INSERT INTO "user" (id, role_id, name, email, password, status_id) VALUES (22, 3, 'Admin Name', 'admin@email.com', '2908d2c28dfc047741fc590a026ffade237ab2ba7e1266f010fe49bde548b5987a534a86655a0d17f336588e540cd66f67234b152bbb645b4bb85758a1325d64', 1);

INSERT INTO feedback_status (id, name) VALUES (2, 'APPROVED');
INSERT INTO feedback_status (id, name) VALUES (1, 'CREATED');
INSERT INTO feedback (id, text, status_id, worker_id) VALUES (6, 'Very nice!', 2, 21);
INSERT INTO feedback (id, text, status_id, worker_id) VALUES (7, 'Good.', 2, 21);
INSERT INTO feedback (id, text, status_id, worker_id) VALUES (8, 'What?', 1, 21);

INSERT INTO service (id, name, duration_timeslots, price, workspaces) VALUES (11, 'SPA', 2, 500, 1);
INSERT INTO service (id, name, duration_timeslots, price, workspaces) VALUES (12, 'Manicure', 1, 100, 5);

INSERT INTO order_status (id, name) VALUES (1, 'CREATED');
INSERT INTO order_status (id, name) VALUES (2, 'COMPLETED');
INSERT INTO "order" (id, date, worker_id, client_id, status_id, service_id) VALUES (15, '2020-02-05 15:21:06.000000', 21, 20, 1, 11);
INSERT INTO "order" (id, date, worker_id, client_id, status_id, service_id) VALUES (16, '2020-02-05 15:21:06.000000', 21, 20, 1, 12);
INSERT INTO "order" (id, date, worker_id, client_id, status_id, service_id) VALUES (17, '2020-02-05 15:21:06.000000', 21, 20, 1, 12);

INSERT INTO timeslot (id, from_time, to_time, date, order_id) VALUES (8, '08:30:00', '09:00:00', '2020-02-10', 15);
INSERT INTO timeslot (id, from_time, to_time, date, order_id) VALUES (9, '12:00:00', '12:30:00', '2020-02-11', 17);
INSERT INTO timeslot (id, from_time, to_time, date, order_id) VALUES (10, '12:30:00', '13:00:00', '2020-02-11', 16);
INSERT INTO timeslot (id, from_time, to_time, date, order_id) VALUES (7, '08:00:00', '08:30:00', '2020-02-10', 15);
