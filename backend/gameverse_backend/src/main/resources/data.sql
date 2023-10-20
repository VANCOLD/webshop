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
INSERT INTO users (username, password, role_id, address_id, cart_id, gender, firstname, lastname, email)
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
('Iam8bit', 1),                                 -- 1
('Nintendo', 2),                                -- 2
('Sony', 3),                                    -- 3
('Microsoft', 4),                               -- 4
('Sega', 5),                                    -- 5
('From Soft', 6),                               -- 6
('Naughty Dog', 7),                             -- 7
('Square Enix', 8),                             -- 8
('Game Freak', 9),                              -- 9
('HAL Laboratories', 10),                       -- 10
('Gameverse', 11),                              -- 11
('Ubisoft', 12),                                -- 12
('Aeternum Game Studios S.L', 13),              -- 13
('BANDAI NAMCO Entertainment', 14),             -- 14
('Focus Home Interactive', 15),                 -- 15
('SquareEnix', 16),                             -- 16
('Electronic Arts', 17),                        -- 17
('2K Games', 18),                               -- 18
('Annapurna Interactive', 19),                  -- 19
('NIS America', 20);                            -- 20


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
('Playstation 5', 'Even contains a controller', 359.00, 'wow', 20, 1000000, 'abcdSony', '2020-05-05', 'NONE', 2, 2, 2),                 -- 3
('Sonic Shirt', 'Cool shirt with sonic print', 12.99, 'Gotta go fast.png', 15, 5, 'dafgjnkig', '1990-1-1', 'NONE', NULL, 3, 4),         -- 4
('20€ Giftcard', 'Not worth buying', 30.00, 'lololol', 10, 1, '1337', '2020-01-20', 'NONE', NULL, 5, 10),                               -- 5
('Xbox Headset', 'Headset Xbox', 49.99, 'headset.jpeg', 25, 100, 'a', '2023-01-01', 'NONE', 1, 4, 3),                                   -- 6
('Gameverse', 'Buy us, we are on sale', 3000000.01, 'company.bmp', 30, 1, 'wow', '2012-01-01', 'NONE', NULL, 6, 10),                    -- 7

        -- XBox Series X        id = 1
('Assassins Creed Mirage', 'Ein gewitzter Straßendieb muss in Bagdad im neunten Jahrhundert Antworten und Gerechtigkeit finden. Mithilfe einer geheimnisvollen Organisation wird er zu einem tödlichen Assassinen.'
, 49.99, 'PHOTO', 9.99, 10, '123456', '2023-10-05', 'MATURE17', 1, 1, 12),                                                                   -- 8
('Aeterna Noctis', 'Aeterna Noctis ist ein 2D-Metroidvania-Spiel, in dem du als König der Dunkelheit in einem riesigen Universum deine verlorenen Fähigkeiten wiedererlangen musst.'
, 39.99, 'PHOTO', 9.99, 10, '123457', '2022-11-04', 'EVERYONE_10', 1, 1, 13),                                                                   -- 9
('Naruto x Boruto Ultimate Ninja Storm Connections', 'Feiere das 20-jährige Jubiläum von Narutos Anime-Debüt mit NARUTO X BORUTO Ultimate Ninja STORM CONNECTIONS, dem nächsten Teil der erfolgreichen Ultimate Ninja STORM-Reihe.'
, 59.99, 'PHOTO', 9.99, 10, '123458', '2023-11-17', 'MATURE17', 1, 1, 14),                                                                   -- 10
('Cuphead', 'Cuphead ist ein klassisches Shoot-''em-up-Actionspiel, in dem du als Cuphead oder Mugman seltsame Welten erkundest und gewaltige Bosse bekämpfst, um deine Seele vom Teufel zurückzugewinnen.'
, 39.99, 'PHOTO', 9.99, 0, '125456', '2022-12-06', 'EVERYONE_10', 1, 1, 1),                                                                     -- 11
('Evil West', 'In Red Dead Redemption: Undead Nightmare musst du als Vampirjäger die USA vor einer Zombie-Apokalypse retten.'
, 59.99, 'PHOTO', 9.99, 10, '123966', '2022-11-22', 'ADULTS_ONLY', 1, 1, 15),                                                                   -- 12
('Final Fantasy 7', 'In Final Fantasy VII kämpfst du als ehemaliger SOLDAT gegen die Shinra Electric Power Company, die den Planeten ausbeutet.'
, 17.99, 'PHOTO', 9.99, 10, '123456', '2020-04-10', 'TEEN', 1, 1, 16),                                                                   -- 13
('Fifa23', 'FIFA 23: Realistischer Fußball mit Frauen-Vereinsmannschaften und Cross-Play'
, 34.99, 'PHOTO', 9.99, 10, '126356', '2022-09-30', 'EVERYONE', 1, 1, 17),                                                              -- 14
('NBA2K23', 'In NBA 2K23 kannst du dich gegen die besten Spieler der Welt beweisen und dein Talent entfalten. Kombiniere All-Stars mit Legenden in Mein TEAM.'
, 29.99, 'PHOTO', 9.99, 10, '168456', '2022-09-09', 'EVERYONE_10', 1, 1, 18),                                                                   -- 15
('Stray', 'Streuner-Katze muss Rätsel lösen, um aus verlassener Stadt zu entkommen.'
, 39.99, 'PHOTO', 9.99, 10, '123996', '2022-11-08', 'EVERYONE_10', 1, 1, 19),                                                                   -- 16
('Yomawari: Lost in the Dark', 'Ein junges Mädchen erwacht in einem dunklen Wald. Sie weiß nicht, wie sie dorthin gekommen ist. Sie trifft auf eine mysteriöse Person, die ihr sagt, dass sie verflucht ist.'
, 15.00, 'PHOTO', 9.99, 10, '126566', '2022-10-28', 'EVERYONE_10', 1, 1, 20),                                                                   -- 17

        -- PS5 Games        id = 2
('Assassins Creed Mirage', 'Ein gewitzter Straßendieb muss in Bagdad im neunten Jahrhundert Antworten und Gerechtigkeit finden. Mithilfe einer geheimnisvollen Organisation wird er zu einem tödlichen Assassinen.'
, 49.99, 'PHOTO', 9.99, 10, '123456', '2023-10-05', 'MATURE17', 2, 1, 12),                                                                   -- 18
('Aeterna Noctis', 'Aeterna Noctis ist ein 2D-Metroidvania-Spiel, in dem du als König der Dunkelheit in einem riesigen Universum deine verlorenen Fähigkeiten wiedererlangen musst.'
, 39.99, 'PHOTO', 9.99, 10, '123457', '2022-11-04', 'EVERYONE_10', 2, 1, 13),                                                                   -- 19
('Naruto x Boruto Ultimate Ninja Storm Connections', 'Feiere das 20-jährige Jubiläum von Narutos Anime-Debüt mit NARUTO X BORUTO Ultimate Ninja STORM CONNECTIONS, dem nächsten Teil der erfolgreichen Ultimate Ninja STORM-Reihe.'
, 59.99, 'PHOTO', 9.99, 10, '123458', '2023-11-17', 'MATURE17', 2, 1, 14),                                                                   -- 20
('Cuphead', 'Cuphead ist ein klassisches Shoot-''em-up-Actionspiel, in dem du als Cuphead oder Mugman seltsame Welten erkundest und gewaltige Bosse bekämpfst, um deine Seele vom Teufel zurückzugewinnen.'
, 39.99, 'PHOTO', 9.99, 0, '125456', '2022-12-06', 'EVERYONE_10', 2, 1, 1),                                                                     -- 21
('Evil West', 'In Red Dead Redemption: Undead Nightmare musst du als Vampirjäger die USA vor einer Zombie-Apokalypse retten.'
, 59.99, 'PHOTO', 9.99, 10, '123966', '2022-11-22', 'ADULTS_ONLY', 2, 1, 15),                                                                   -- 22
('Final Fantasy 7', 'In Final Fantasy VII kämpfst du als ehemaliger SOLDAT gegen die Shinra Electric Power Company, die den Planeten ausbeutet.'
, 17.99, 'PHOTO', 9.99, 10, '123456', '2020-04-10', 'TEEN', 2, 1, 16),                                                                   -- 23
('Fifa23', 'FIFA 23: Realistischer Fußball mit Frauen-Vereinsmannschaften und Cross-Play'
, 34.99, 'PHOTO', 9.99, 10, '126356', '2022-09-30', 'EVERYONE', 2, 1, 17),                                                              -- 24
('NBA2K23', 'In NBA 2K23 kannst du dich gegen die besten Spieler der Welt beweisen und dein Talent entfalten. Kombiniere All-Stars mit Legenden in Mein TEAM.'
, 29.99, 'PHOTO', 9.99, 10, '168456', '2022-09-09', 'EVERYONE_10', 2, 1, 18),                                                                   -- 25
('Stray', 'Streuner-Katze muss Rätsel lösen, um aus verlassener Stadt zu entkommen.'
, 39.99, 'PHOTO', 9.99, 10, '123996', '2022-11-08', 'EVERYONE_10', 2, 1, 19),                                                                   -- 26
('Yomawari: Lost in the Dark', 'Ein junges Mädchen erwacht in einem dunklen Wald. Sie weiß nicht, wie sie dorthin gekommen ist. Sie trifft auf eine mysteriöse Person, die ihr sagt, dass sie verflucht ist.'
, 15.00, 'PHOTO', 9.99, 10, '126566', '2022-10-28', 'EVERYONE_10', 2, 1, 20),                                                                   -- 27

        -- Nintendo Switch      id = 3
('Assassins Creed Mirage', 'Ein gewitzter Straßendieb muss in Bagdad im neunten Jahrhundert Antworten und Gerechtigkeit finden. Mithilfe einer geheimnisvollen Organisation wird er zu einem tödlichen Assassinen.'
, 49.99, 'PHOTO', 9.99, 10, '123456', '2023-10-05', 'MATURE17', 3, 1, 12),                                                                   -- 28
('Aeterna Noctis', 'Aeterna Noctis ist ein 2D-Metroidvania-Spiel, in dem du als König der Dunkelheit in einem riesigen Universum deine verlorenen Fähigkeiten wiedererlangen musst.'
, 39.99, 'PHOTO', 9.99, 10, '123457', '2022-11-04', 'EVERYONE_10', 3, 1, 13),                                                                   -- 29
('Naruto x Boruto Ultimate Ninja Storm Connections', 'Feiere das 20-jährige Jubiläum von Narutos Anime-Debüt mit NARUTO X BORUTO Ultimate Ninja STORM CONNECTIONS, dem nächsten Teil der erfolgreichen Ultimate Ninja STORM-Reihe.'
, 59.99, 'PHOTO', 9.99, 10, '123458', '2023-11-17', 'MATURE17', 3, 1, 14),                                                                   -- 30
('Cuphead', 'Cuphead ist ein klassisches Shoot-''em-up-Actionspiel, in dem du als Cuphead oder Mugman seltsame Welten erkundest und gewaltige Bosse bekämpfst, um deine Seele vom Teufel zurückzugewinnen.'
, 39.99, 'PHOTO', 9.99, 0, '125456', '2022-12-06', 'EVERYONE_10', 3, 1, 1),                                                                     -- 31
('Evil West', 'In Red Dead Redemption: Undead Nightmare musst du als Vampirjäger die USA vor einer Zombie-Apokalypse retten.'
, 59.99, 'PHOTO', 9.99, 10, '123966', '2022-11-22', 'ADULTS_ONLY', 3, 1, 15),                                                                   -- 32
('Final Fantasy 7', 'In Final Fantasy VII kämpfst du als ehemaliger SOLDAT gegen die Shinra Electric Power Company, die den Planeten ausbeutet.'
, 17.99, 'PHOTO', 9.99, 10, '123456', '2020-04-10', 'TEEN', 3, 1, 16),                                                                   -- 33
('Fifa23', 'FIFA 23: Realistischer Fußball mit Frauen-Vereinsmannschaften und Cross-Play'
, 34.99, 'PHOTO', 9.99, 10, '126356', '2022-09-30', 'EVERYONE', 3, 1, 17),                                                              -- 34
('NBA2K23', 'In NBA 2K23 kannst du dich gegen die besten Spieler der Welt beweisen und dein Talent entfalten. Kombiniere All-Stars mit Legenden in Mein TEAM.'
, 29.99, 'PHOTO', 9.99, 10, '168456', '2022-09-09', 'EVERYONE_10', 3, 1, 18),                                                                   -- 35
('Stray', 'Streuner-Katze muss Rätsel lösen, um aus verlassener Stadt zu entkommen.'
, 39.99, 'PHOTO', 9.99, 10, '123996', '2022-11-08', 'EVERYONE_10', 3, 1, 19),                                                                   -- 36
('Yomawari: Lost in the Dark', 'Ein junges Mädchen erwacht in einem dunklen Wald. Sie weiß nicht, wie sie dorthin gekommen ist. Sie trifft auf eine mysteriöse Person, die ihr sagt, dass sie verflucht ist.'
, 15.00, 'PHOTO', 9.99, 10, '126566', '2022-10-28', 'EVERYONE_10', 3, 1, 20),                                                                   -- 37

        -- Xbox One     id = 4
('Assassins Creed Mirage', 'Ein gewitzter Straßendieb muss in Bagdad im neunten Jahrhundert Antworten und Gerechtigkeit finden. Mithilfe einer geheimnisvollen Organisation wird er zu einem tödlichen Assassinen.'
, 49.99, 'PHOTO', 9.99, 10, '123456', '2023-10-05', 'MATURE17', 4, 1, 12),                                                                   -- 38
('Aeterna Noctis', 'Aeterna Noctis ist ein 2D-Metroidvania-Spiel, in dem du als König der Dunkelheit in einem riesigen Universum deine verlorenen Fähigkeiten wiedererlangen musst.'
, 39.99, 'PHOTO', 9.99, 10, '123457', '2022-11-04', 'EVERYONE_10', 4, 1, 13),                                                                   -- 39
('Naruto x Boruto Ultimate Ninja Storm Connections', 'Feiere das 20-jährige Jubiläum von Narutos Anime-Debüt mit NARUTO X BORUTO Ultimate Ninja STORM CONNECTIONS, dem nächsten Teil der erfolgreichen Ultimate Ninja STORM-Reihe.'
, 59.99, 'PHOTO', 9.99, 10, '123458', '2023-11-17', 'MATURE17', 4, 1, 14),                                                                   -- 40
('Cuphead', 'Cuphead ist ein klassisches Shoot-''em-up-Actionspiel, in dem du als Cuphead oder Mugman seltsame Welten erkundest und gewaltige Bosse bekämpfst, um deine Seele vom Teufel zurückzugewinnen.'
, 39.99, 'PHOTO', 9.99, 0, '125456', '2022-12-06', 'EVERYONE_10', 4, 1, 1),                                                                     -- 41
('Evil West', 'In Red Dead Redemption: Undead Nightmare musst du als Vampirjäger die USA vor einer Zombie-Apokalypse retten.'
, 59.99, 'PHOTO', 9.99, 10, '123966', '2022-11-22', 'ADULTS_ONLY', 4, 1, 15),                                                                   -- 42
('Final Fantasy 7', 'In Final Fantasy VII kämpfst du als ehemaliger SOLDAT gegen die Shinra Electric Power Company, die den Planeten ausbeutet.'
, 17.99, 'PHOTO', 9.99, 10, '123456', '2020-04-10', 'TEEN', 4, 1, 16),                                                                   -- 43
('Fifa23', 'FIFA 23: Realistischer Fußball mit Frauen-Vereinsmannschaften und Cross-Play'
, 34.99, 'PHOTO', 9.99, 10, '126356', '2022-09-30', 'EVERYONE', 4, 1, 17),                                                              -- 44
('NBA2K23', 'In NBA 2K23 kannst du dich gegen die besten Spieler der Welt beweisen und dein Talent entfalten. Kombiniere All-Stars mit Legenden in Mein TEAM.'
, 29.99, 'PHOTO', 9.99, 10, '168456', '2022-09-09', 'EVERYONE_10', 4, 1, 18),                                                                   -- 45
('Stray', 'Streuner-Katze muss Rätsel lösen, um aus verlassener Stadt zu entkommen.'
, 39.99, 'PHOTO', 9.99, 10, '123996', '2022-11-08', 'EVERYONE_10', 4, 1, 19),                                                                   -- 46
('Yomawari: Lost in the Dark', 'Ein junges Mädchen erwacht in einem dunklen Wald. Sie weiß nicht, wie sie dorthin gekommen ist. Sie trifft auf eine mysteriöse Person, die ihr sagt, dass sie verflucht ist.'
, 15.00, 'PHOTO', 9.99, 10, '126566', '2022-10-28', 'EVERYONE_10', 4, 1, 20),                                                                   -- 47

        -- PS4 Games        id = 5
('Assassins Creed Mirage', 'Ein gewitzter Straßendieb muss in Bagdad im neunten Jahrhundert Antworten und Gerechtigkeit finden. Mithilfe einer geheimnisvollen Organisation wird er zu einem tödlichen Assassinen.'
, 49.99, 'PHOTO', 9.99, 10, '123456', '2023-10-05', 'MATURE17', 5, 1, 12),                                                                   -- 48
('Aeterna Noctis', 'Aeterna Noctis ist ein 2D-Metroidvania-Spiel, in dem du als König der Dunkelheit in einem riesigen Universum deine verlorenen Fähigkeiten wiedererlangen musst.'
, 39.99, 'PHOTO', 9.99, 10, '123457', '2022-11-04', 'EVERYONE_10', 5, 1, 13),                                                                   -- 49
('Naruto x Boruto Ultimate Ninja Storm Connections', 'Feiere das 20-jährige Jubiläum von Narutos Anime-Debüt mit NARUTO X BORUTO Ultimate Ninja STORM CONNECTIONS, dem nächsten Teil der erfolgreichen Ultimate Ninja STORM-Reihe.'
, 59.99, 'PHOTO', 9.99, 10, '123458', '2023-11-17', 'MATURE17', 5, 1, 14),                                                                   -- 50
('Cuphead', 'Cuphead ist ein klassisches Shoot-''em-up-Actionspiel, in dem du als Cuphead oder Mugman seltsame Welten erkundest und gewaltige Bosse bekämpfst, um deine Seele vom Teufel zurückzugewinnen.'
, 39.99, 'PHOTO', 9.99, 0, '125456', '2022-12-06', 'EVERYONE_10', 5, 1, 1),                                                                     -- 51
('Evil West', 'In Red Dead Redemption: Undead Nightmare musst du als Vampirjäger die USA vor einer Zombie-Apokalypse retten.'
, 59.99, 'PHOTO', 9.99, 10, '123966', '2022-11-22', 'ADULTS_ONLY', 5, 1, 15),                                                                   -- 52
('Final Fantasy 7', 'In Final Fantasy VII kämpfst du als ehemaliger SOLDAT gegen die Shinra Electric Power Company, die den Planeten ausbeutet.'
, 17.99, 'PHOTO', 9.99, 10, '123456', '2020-04-10', 'TEEN', 5, 1, 16),                                                                   -- 53
('Fifa23', 'FIFA 23: Realistischer Fußball mit Frauen-Vereinsmannschaften und Cross-Play'
, 34.99, 'PHOTO', 9.99, 10, '126356', '2022-09-30', 'EVERYONE', 5, 1, 17),                                                              -- 54
('NBA2K23', 'In NBA 2K23 kannst du dich gegen die besten Spieler der Welt beweisen und dein Talent entfalten. Kombiniere All-Stars mit Legenden in Mein TEAM.'
, 29.99, 'PHOTO', 9.99, 10, '168456', '2022-09-09', 'EVERYONE_10', 5, 1, 18),                                                                   -- 55
('Stray', 'Streuner-Katze muss Rätsel lösen, um aus verlassener Stadt zu entkommen.'
, 39.99, 'PHOTO', 9.99, 10, '123996', '2022-11-08', 'EVERYONE_10', 5, 1, 19),                                                                   -- 56
('Yomawari: Lost in the Dark', 'Ein junges Mädchen erwacht in einem dunklen Wald. Sie weiß nicht, wie sie dorthin gekommen ist. Sie trifft auf eine mysteriöse Person, die ihr sagt, dass sie verflucht ist.'
, 15.00, 'PHOTO', 9.99, 10, '126566', '2022-10-28', 'EVERYONE_10', 5, 1, 20);                                                                   -- 57

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