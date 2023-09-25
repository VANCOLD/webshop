/* Needed to be able to truncate without violating referential integrity */
SET REFERENTIAL_INTEGRITY FALSE;

TRUNCATE TABLE categories;
TRUNCATE TABLE console_generations;
TRUNCATE TABLE genres;
TRUNCATE TABLE producers;
TRUNCATE TABLE products;
TRUNCATE TABLE product_to_genre;
TRUNCATE TABLE privileges;
TRUNCATE TABLE roles;
TRUNCATE TABLE privileges_in_role;
TRUNCATE TABLE users;


INSERT INTO privileges (name)
VALUES ('view_profile'),  -- 1
       ('view_cart'),     -- 2
       ('view_users'),    -- 3
       ('edit_profile'),  -- 4
       ('edit_cart'),     -- 5
       ('edit_users'),    -- 6
       ('edit_products'); -- 7

INSERT INTO roles (name)
VALUES ('user'),    -- 1
       ('support'), -- 2
       ('admin');   -- 3

INSERT INTO privileges_in_role (role_id, privilege_id)
VALUES (1, 1), -- 1
       (1, 2), -- 2
       (1, 4), -- 3
       (2, 1), -- 4
       (2, 2), -- 5
       (2, 3), -- 6
       (2, 4), -- 7
       (2, 5), -- 8
       (2, 7), -- 9
       (3, 1), -- 10
       (3, 2), -- 11
       (3, 3), -- 12
       (3, 4), -- 13
       (3, 5), -- 14
       (3, 6), -- 15
       (3, 7); -- 16


/* password is always the word password for each user */
INSERT INTO users (username, password, role_id) VALUES
       ('user', '$2a$12$p4lekWiTI3LZHx8b1cOVQ.IyRPDZhuZBHw3fGazVotMq2iVBGuwPq', 1),      -- 1
       ('moderator', '$2a$12$p4lekWiTI3LZHx8b1cOVQ.IyRPDZhuZBHw3fGazVotMq2iVBGuwPq', 2), -- 2
       ('admin', '$2a$12$p4lekWiTI3LZHx8b1cOVQ.IyRPDZhuZBHw3fGazVotMq2iVBGuwPq', 3);     -- 3

SET REFERENTIAL_INTEGRITY TRUE;