INSERT INTO customer (name, email, mobile_number, pwd, role, create_dt) VALUES ('Happy','happy@example.com','9876548337', '$2y$12$oRRbkNfwuR8ug4MlzH5FOeui.//1mkd.RsOAJMbykTSupVy.x/vb2', 'company', CURDATE());
INSERT INTO customer (name, email, mobile_number, pwd, role, create_dt) VALUES ('Jayati', 'jayati@gmail.com', '+3163392606', '$2a$10$0KbKjtLjBTeMZC4cym7apuesK3sq6sXkUVcFaloSFVTifjJNc6jJW', 'broker', CURDATE());
INSERT INTO authorities (customer_id, name) VALUES (1, 'ROLE_COMPANY');
INSERT INTO authorities (customer_id, name) VALUES (2, 'ROLE_BROKER');
