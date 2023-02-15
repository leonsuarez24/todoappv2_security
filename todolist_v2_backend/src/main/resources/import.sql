INSERT INTO todo_lists(id, name, content) VALUES (1, "Tarea 1", "Esta es mi primera tarea");
INSERT INTO todo_lists(id, name, content) VALUES (2, "Tarea 2", "Esta es mi segunda tarea");
INSERT INTO todo_lists(id, name, content) VALUES (3, "Tarea 3", "Esta es mi tercera tarea");

INSERT INTO authorities(id, authority) VALUES (1, "ROLE_USER");
INSERT INTO authorities(id, authority) VALUES (2, "ROLE_ADMIN");
INSERT INTO authorities(id, authority) VALUES (3, "ROLE_DEVELOPER");

INSERT INTO users(id, username, email, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES (1, "leon", "leon@gmail.com", "$2a$12$Y.XTtMjMn57ZWctliy2Pw.gYVCUvyI9eewOGi.umEMup/OksX78fS", true, true, true, true);
INSERT INTO users(id, username, email, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES (2, "zeus", "zeus@gmail.com", "$2a$12$Y.XTtMjMn57ZWctliy2Pw.gYVCUvyI9eewOGi.umEMup/OksX78fS", true, true, true, true);
INSERT INTO users(id, username, email, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES (3, "horus", "horus@gmail.com", "$2a$12$Y.XTtMjMn57ZWctliy2Pw.gYVCUvyI9eewOGi.umEMup/OksX78fS", true, true, true, true);

INSERT INTO users_authorities (users_id, authorities_id) VALUES (1, 1);
INSERT INTO users_authorities (users_id, authorities_id) VALUES (1, 2);
INSERT INTO users_authorities (users_id, authorities_id) VALUES (1, 3);
INSERT INTO users_authorities (users_id, authorities_id) VALUES (2, 1);
INSERT INTO users_authorities (users_id, authorities_id) VALUES (2, 2);
INSERT INTO users_authorities (users_id, authorities_id) VALUES (3, 3);