/* Needed to be able to truncate without violating referential integrity */
SET FOREIGN_KEY_CHECKS = 0;

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
TRUNCATE TABLE addresses;

INSERT INTO privileges (name) VALUES
('view_profile'),        -- 1
('view_carts'),           -- 2
('view_users'),          -- 3
('edit_profile'),        -- 4
('edit_carts'),           -- 5
('edit_users'),          -- 6
('edit_products'),       -- 7
('view_orders');         -- 8

INSERT INTO addresses (street, postalcode, city, country) VALUES
('Johnstraße 12', '1150','Vienna','Austria'),                                   -- 1
('11-1 Hokotate-cho','601-8501','Kyoto','Japan'),                               -- 2
('1-7-1 Konan','108-0075','Tokyo','Japan'),                                     -- 3
('Microsoft Building 92, NE 36th St','98052','Redmond','Vereinigte Staaten'),   -- 4
('1-1-1 Nishi-Shinagawa,','141-0033','Tokyo','Japan'),                          -- 5
('21-1,Kita-Shinjuku 2-chome,Shinjuku-ku','169-0074','Tokyo','Japan'),          -- 6
('2425 Olympic Blvd Ste 3000W','CA 90404','Santa Monica','Vereinigte Staaten'), -- 7
('SHINJUKU EASTSIDE SQUARE 6-27-30 Shinjuku','141-0033','Tokyo','Japan'),       -- 8
('2-2-1 Kandanishiki-cho, Chiyoda','101-0054','Tokyo','Japan'),                 -- 9
('1-22 Kandasuda-cho Chiyoda-ku','101-0054','Tokyo','Japan'),                   -- 10
('Pilzgasse 12','1210','Wien','Österreich');                                    -- 11


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
INSERT INTO users (username, password, role_id, address_id, cart_id, gender, first_name, last_name, email)
VALUES
('user', '$2a$12$p4lekWiTI3LZHx8b1cOVQ.IyRPDZhuZBHw3fGazVotMq2iVBGuwPq', 1, 1, 1, 'Male', 'John', 'Doe', 'user@example.com'),      -- User 1
('admin', '$2a$12$p4lekWiTI3LZHx8b1cOVQ.IyRPDZhuZBHw3fGazVotMq2iVBGuwPq', 2, 1, 2, 'Male', 'Admin', 'Admin', 'admin@example.com');  -- User 2



INSERT INTO carts(id) VALUES
(1),
(2);

INSERT INTO console_generations (name) VALUES
('XBox Series X'),      -- 1
('Playstation 5'),      -- 2
('Nintendo Switch'),    -- 3
('Xbox One'),           -- 4
('Playstation 4');      -- 5


INSERT INTO producers (name, address_id) VALUES
('Nintendo', 2),           -- 1
('Sony', 3),               -- 2
('Microsoft', 4),          -- 3
('Sega', 5),               -- 4
('From Soft', 6),          -- 5
('Naughty Dog', 7),        -- 6
('Square Enix', 8),        -- 7
('Game Freak', 9),         -- 8
('HAL Laboratories', 10),  -- 9
('Gameverse', 11);         -- 10


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
('Playstation 5', 'Even contains a controller', 359.00, 'wow', 20, 1000000, 'abcdSony', '2020-05-05', 'NONE', 2, 2, 2),          		-- 3
('Sonic Shirt', 'Cool shirt with sonic print', 12.99, 'Gotta go fast.png', 15, 5, 'dafgjnkig', '1990-1-1', 'NONE', NULL, 3, 4),         -- 4
('20€ Giftcard', 'Not worth buying', 30.00, 'lololol', 10, 1, '1337', '2020-01-20', 'NONE', NULL, 5, 10),                               -- 5
('Xbox Headset', 'Headset Xbox', 49.99, 'headset.jpeg', 25, 100, 'a', '2023-01-01', 'NONE', 1, 4, 3),                                   -- 6
('Gameverse', 'Buy us, we are on sale', 3000000.01, 'company.bmp', 30, 1, 'wow', '2012-01-01', 'NONE', NULL, 6, 10);                    -- 7

INSERT INTO products_to_carts (product_id, cart_id) VALUES
(2, 1), -- 1
(3, 1); -- 2


INSERT INTO products_to_genres (product_id, genre_id) VALUES
(1, 2),     -- 1
(1, 3),     -- 2
(2, 2),     -- 3
(2, 3),     -- 4
(2, 9);     -- 5

SET FOREIGN_KEY_CHECKS = 1;