--POPULATE ROLE TABLE
INSERT INTO role (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO role (id, name) VALUES (2, 'ROLE_USER');

--POPULATE USER TABLE
INSERT INTO customer (id, name, email, password, phone) VALUES (1, 'admin', 'admin@admin.com', '$2a$11$N6PHp0OR0dtbRsPPU6.Hc.5s3vV2ATV60KkqhOIMuIjjUPdCwWobK', 3615); --BCrypt password:'qwe123'

--ASSIGN 'admin' USER TO 'admin' ROLE
INSERT INTO user2role (customer_id, role_id) VALUES (1, 1);

--POPULATE ADDRESS TABLE
INSERT INTO address (id, city, street, building_number, postal_code) VALUES (1, 'Lviv', 'Stusa', 5, 78048);
INSERT INTO address (id, city, street, building_number, postal_code) VALUES (2, 'Lviv', 'Pekarska', 15, 78046);

--POPULATE VENUES TABLE
INSERT INTO venue (id, name, phone, address_id, start_work, end_work) VALUES (1, 'Dynamo Blues', 777222, 1, '08:00:00', '22:00:00');
INSERT INTO venue (id, name, phone, address_id, start_work, end_work) VALUES (2, 'Mykhailova', 444222, 2, '08:00:00', '23:00:00');