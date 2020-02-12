INSERT INTO "user" (login, password_hash, type, username) VALUES
    -- password - 1234
  ('user1', '$2a$10$63TIv7TSEcAxzFcpH3Tho.O98OqZ1NrHi1KE9MBL4rJ6frJBSuhsu', 'ADMIN', 'Stepan'),
  ('user2', '$2a$10$63TIv7TSEcAxzFcpH3Tho.O98OqZ1NrHi1KE9MBL4rJ6frJBSuhsu', 'SIMPLE', 'Ivan');

INSERT INTO room(name) values
    ('room1'),
    ('room2');

INSERT INTO message(text, user_id, room_id) values
    ('hello', 1, 1),
    ('hey', 2, 1);

INSERT INTO ban_info(room_id, user_id) values
    (1, 1),
    (2, 1);
