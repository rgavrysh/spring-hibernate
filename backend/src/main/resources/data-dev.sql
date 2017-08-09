--POPULATE ROLE TABLE
INSERT INTO role (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO role (id, name) VALUES (2, 'ROLE_USER');

--POPULATE USER TABLE
INSERT INTO customer (id, name, email, password, phone) VALUES (1, 'admin', 'admin@admin.com', '$2a$11$N6PHp0OR0dtbRsPPU6.Hc.5s3vV2ATV60KkqhOIMuIjjUPdCwWobK', 3615); --BCrypt password:'qwe123'

--ASSIGN 'admin' USER TO 'admin' ROLE
INSERT INTO user2role (customer_id, role_id) VALUES (1, 1);