INSERT INTO "user" (login, password_hash, type) VALUES
    -- password - 1234
  ('user1', '$2a$10$63TIv7TSEcAxzFcpH3Tho.O98OqZ1NrHi1KE9MBL4rJ6frJBSuhsu', 'ADMIN'),
  ('user2', '$2a$10$63TIv7TSEcAxzFcpH3Tho.O98OqZ1NrHi1KE9MBL4rJ6frJBSuhsu', 'SIMPLE');

INSERT INTO message(text, user_id) values
    ('hello', 1),
    ('hey', 2);