/* Needed to be able to truncate without violating referential integrity */
SET REFERENTIAL_INTEGRITY FALSE;

TRUNCATE TABLE categories;
TRUNCATE TABLE console_generations;
TRUNCATE TABLE genres;
TRUNCATE TABLE producers;
TRUNCATE TABLE products;
TRUNCATE TABLE products_to_genres;
TRUNCATE TABLE privileges;
TRUNCATE TABLE roles;
TRUNCATE TABLE privileges_in_role;
TRUNCATE TABLE users;
TRUNCATE TABLE orders;
TRUNCATE TABLE ordered_products;
TRUNCATE TABLE carts;
TRUNCATE TABLE products_to_carts;


INSERT INTO privileges (name) VALUES
('view_profile'),        -- 1
('view_cart'),           -- 2
('view_users'),          -- 3
('edit_profile'),        -- 4
('edit_cart'),           -- 5
('edit_users'),          -- 6
('edit_products'),       -- 7
('view_orders');         -- 8


INSERT INTO roles (name) VALUES
('user'),    -- 1
('admin');   -- 2


INSERT INTO privileges_in_role (role_id, privilege_id) VALUES
(1, 1), -- 1
(1, 2), -- 2
(1, 8), -- 3
(2, 1), -- 4
(2, 2), -- 5
(2, 3), -- 6
(2, 4), -- 7
(2, 5), -- 8
(2, 6), -- 9
(2, 7), -- 10
(2, 8); -- 11


/* password is always the word password for each user */
INSERT INTO users (username, password, role_id) VALUES
('user', '$2a$12$p4lekWiTI3LZHx8b1cOVQ.IyRPDZhuZBHw3fGazVotMq2iVBGuwPq', 1),      -- 1
('admin', '$2a$12$p4lekWiTI3LZHx8b1cOVQ.IyRPDZhuZBHw3fGazVotMq2iVBGuwPq', 2);     -- 2


INSERT INTO console_generations (name) VALUES
('XBox Series X'),      -- 1
('Playstation 5'),      -- 2
('Nintendo Switch'),    -- 3
('Xbox One'),           -- 4
('Playstation 4');      -- 5


INSERT INTO producers (name) VALUES
('Nintendo'),           -- 1
('Sony'),               -- 2
('Microsoft'),          -- 3
('Sega'),               -- 4
('From Soft'),          -- 5
('Naughty Dog'),        -- 6
('Square Enix'),        -- 7
('Game Freak'),         -- 8
('HAL Laboratories'),   -- 9
('Gameverse');          -- 10


INSERT INTO genres (name) VALUES
('First Person Shooters'),      -- 1
('Role Playing Games'),         -- 2
('Adventure'),                  -- 3
('Simulation'),                 -- 4
('Strategy'),                   -- 5
('Sports & Fitness'),           -- 6
('Fighting'),                   -- 7
('Platformers'),                -- 8
('Survival & Horror'),          -- 9
('Stealth');                    -- 10


INSERT INTO categories (name) VALUES
('Games'),          -- 1
('Consoles'),       -- 2
('Merchandise'),    -- 3
('Accessories'),    -- 4
('Giftcards'),      -- 5
('Sale');           -- 6


INSERT INTO products
(name, description, price, image, tax, stock, gtin, available, esrb_rating, console_generation_id, category_id, producer_id)
VALUES
('The Legend Of Zelda: Breath Of The Wild','Cool zelda', 60.00, 'Cool Image', 20, 200, '1234', '2022-01-01', 'EVERYONE', 3, 1, 1),      -- 1
('Elden Ring','Dodges', 59.00, 'Hellish Image', 20, 2000, '4321', '2023-01-01', 'MATURE17', 2, 1, 5),                                   -- 2
('Playstation 5 Console', 'Even contains a controler', 359.00, 'wow', 20, 1000000, 'abcdSony', '2020-05-05', 'NONE', 2, 2, 2),          -- 3
('Sonic Shirt', 'Cool shirt with sonic print', 12.99, 'Gotta go fast.png', 15, 5, 'dafgjnkig', '1990-1-1', 'NONE', NULL, 3, 4),         -- 4
('20€ Giftcard', 'Not worth buying', 30.00, 'lololol', 10, 1, '1337', '2020-01-20', 'NONE', NULL, 5, 10),                               -- 5
('Xbox Headset', 'Headset Xbox', 49.99, 'headset.jpeg', 25, 100, 'a', '2023-01-01', 'NONE', 1, 4, 3),                                   -- 6
('Gameverse', 'Buy us, we are on sale', 3000000.01, 'company.bmp', 30, 1, 'wow', '2012-01-01', 'NONE', NULL, 6, 10);                    -- 7


INSERT INTO products_to_genres (product_id, genre_id) VALUES
(1, 2),     -- 1
(1, 3),     -- 2
(2, 2),     -- 3
(2, 3),     -- 4
(2, 9);     -- 5

SET REFERENTIAL_INTEGRITY TRUE;