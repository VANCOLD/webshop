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
('141 Avenue Paul Doumer','92120','Montrouge','France'),
('14 Rue de la Fontaine au Roi','75011','Paris','France'),
('6 Route de la Pyramide','92800','Puteaux','France'),
('370 King Street West','L8P 1A1','Hamilton','Canada'),
('105 Grunwaldzka','50-380','Wrocław','Poland'),
('4-1-4 Shinjuku Shinjuku Mitsui Bldg., Nishi-Shinjuku','160-0023','Shinjuku','Japan'),
('209 Redwood Shores Parkway','94065','Redwood City','United States'), -- 7
('1050 Marina Village Parkway','94941','Alameda','United States'),
('165 Rue de Bercy','75012','Paris','France'),
('1-1-88 Kawasemidai','185-0004','Fuchu','Japan'),
('2-6-12 Higashi Shimbashi','105-0023','Minato','Japan'),
('43a St James Street','M16 9DX','Manchester','United Kingdom'),
('11-1 Kamokita-cho','600-8034','Kyoto','Japan'),
('141 Avenue Paul Doumer','92120','Montrouge','France'),
('1-9-11 Minatomirai','231-0003','Yokohama','Japan'),
('3812 NE 20th Ave','97212','Portland','United States'),
('Länsiväylä 16','2150','Espoo','Finland'),
('2-1-1 Nishishinsaibashi','550-0007','Osaka','Japan'),
('Jagiellońska 74','03-719','Warsaw','Poland'),
('1000 Gearbox Road','75038','Frisco','United States'),
('3-17-10 Kamiyacho','813-0043','Fukuoka','Japan'),
('22777 Santa Monica Blvd','90403','Santa Monica','United States'),
('3-5-1 Setagaya','154-0023','Setagaya','Japan'),
('9890 West Santa Monica Boulevard','90069','Beverly Hills','United States'),
('165 Rue de Bercy','75012','Paris','France'),
('2000 Bridgeway','GU14 8LS','Guildford','United Kingdom'),
('14-22 Rodborough Road','SE24 0NG','London','United Kingdom'),
('124 Rue de Clichy','75017','Paris','France'),
('Lüneburger Straße 14','33175','Bad Salzuflen','Germany'),
('141 Avenue Paul Doumer','92120','Montrouge','France'),
('Lindenstraße 13','79686','Maulburg','Germany'),
('2665 South West Temple','84115','Salt Lake City','United States'),
('22777 Santa Monica Blvd','90403','Santa Monica','United States'),
('Vulcan House','Science Park','Cambridge','CB4 0WA'),
('622 Broadway','10012','New York City','United States'),
('358 Pangyo-ro, Bundang-gu, Seongnam-si, Gyeonggi-do','13488','Seongnam','South Korea'),
('22777 Santa Monica Blvd','90403','Santa Monica','United States'),
('Leamington Spa','CV32 6NT','Warwickshire','United Kingdom'),
('Jakobsgatan 2','111 44','Stockholm','Sweden'),
('3000 Epic Campus Drive','27517','Cary','United States'),
('1 Sony Park','SW14 8AJ','London','United Kingdom'),
('1 Microsoft Way','98052','Redmond','United States'),
('Johnstraße 12', '1150','Wien', 'Österreich'),
('Pilzgasse 5', '1210', 'Wien', 'Österreich'),
('Wexstraße 22', '1200','Wien','Österreich'),
('11-1 Kamokita-cho','600-8034','Kyoto','Japan');

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
('Ubisoft', 1),
('Aeternum Game Studio', 2),
('Bandai Namco Entertainment', 3),
('Studio MDHR', 4),
('Flying Wild Hog', 5),
('Square Enix', 6),
('Electronic Arts', 7),
('2K Sports', 8),
('BlueTwelve Studio', 9),
('Nippon Ichi Software', 10),
('PlatinumGames', 11),
('Acid Nerve', 12),
('Nintendo', 13),
('Hazelight Studios', 14),
('The Pokémon Company', 15),
('ConcernedApe', 16),
('10tons', 17),
('Capcom', 18),
('CD Projekt RED', 19),
('Gearbox Software', 20),
('CyberConnect2', 21),
('Infinity Ward', 22),
('FromSoftware', 23),
('Santa Monica Studio', 24),
('Deck Nine Games', 25),
('Criterion Games', 26),
('Animoon Studios', 27),
('Stillalive Studios', 28),
('Astragon Entertainment', 29),
('Herobeat Studios', 30),
('Giants Software', 31),
('Avalanche Software', 32),
('Striking Distance Studios', 33),
('Frontier Developments', 34),
('Rockstar Games', 35),
('PUBG Corporation', 36),
('Treyarch', 37),
('Playground Games', 38),
('Mojang Studios', 39),
('Epic Games', 40),
('EA Sports', 7),
('Visual Concepts', 7),
('Sony', 43),
('Microsoft', 44),
('Gameverse', 45),
('Intelligent Systems', 46);



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




-- PS4 Games        id = 5
('Assassins Creed Mirage', 'Ein gewitzter Straßendieb muss in Bagdad im neunten Jahrhundert Antworten und Gerechtigkeit finden. Mithilfe einer geheimnisvollen Organisation wird er zu einem tödlichen Assassinen.'
, 49.99, 'ps4_assasinscreedmirage.jpg', 10, 10, '123456', '2023-10-05', 'MATURE_17', 5, 1, 1),                                                                   -- 48
('Aeterna Noctis', 'Aeterna Noctis ist ein 2D-Metroidvania-Spiel, in dem du als König der Dunkelheit in einem riesigen Universum deine verlorenen Fähigkeiten wiedererlangen musst.'
, 39.99, 'ps4_aeternanoctis.jpg', 10, 10, '123457', '2022-11-04', 'EVERYONE_10', 5, 1, 2),                                                                   -- 49
('Naruto x Boruto Ultimate Ninja Storm Connections', 'Feiere das 20-jährige Jubiläum von Narutos Anime-Debüt mit NARUTO X BORUTO Ultimate Ninja STORM CONNECTIONS, dem nächsten Teil der erfolgreichen Ultimate Ninja STORM-Reihe.'
, 59.99, 'ps4_naruto.jpg', 10, 10, '123458', '2023-11-17', 'MATURE_17', 5, 1, 3),                                                                   -- 50
('Cuphead', 'Cuphead ist ein klassisches Shoot-''em-up-Actionspiel, in dem du als Cuphead oder Mugman seltsame Welten erkundest und gewaltige Bosse bekämpfst, um deine Seele vom Teufel zurückzugewinnen.'
, 39.99, 'ps4_cuphead.jpg', 10, 0, '125459', '2022-12-06', 'EVERYONE_10', 5, 1, 4),                                                                     -- 51
('Evil West', 'In Evil West musst du als Vampirjäger die USA vor einer Zombie-Apokalypse retten.'
, 59.99, 'ps4_evilwest.jpg', 10, 10, '123960', '2022-11-22', 'ADULTS_ONLY', 5, 1, 5),                                                                   -- 52
('Final Fantasy 7 Crisis Core Reunion', 'In Final Fantasy VII kämpfst du als ehemaliger SOLDAT gegen die Shinra Electric Power Company, die den Planeten ausbeutet.'
, 17.99, 'ps4_finalfantasy7crisiscorereunion.jpg', 10, 10, '123461', '2020-04-10', 'TEEN', 5, 1, 6),                                                                   -- 53
('Fifa 23', 'FIFA 23: Realistischer Fußball mit Frauen-Vereinsmannschaften und Cross-Play'
, 34.99, 'ps4_fifa23.jpg', 10, 10, '123462', '2022-09-30', 'EVERYONE', 5, 1, 7),                                                              -- 54
('NBA 2K23', 'In NBA 2K23 kannst du dich gegen die besten Spieler der Welt beweisen und dein Talent entfalten. Kombiniere All-Stars mit Legenden in Mein TEAM.'
, 29.99, 'ps4_nba2k23.jpg', 10, 10, '123463', '2022-09-09', 'EVERYONE_10', 5, 1, 8),                                                                   -- 55
('Stray', 'Streuner-Katze muss Rätsel lösen, um aus verlassener Stadt zu entkommen.'
, 39.99, 'ps4_stray.jpg', 10, 10, '123464', '2022-11-08', 'EVERYONE_10', 5, 1, 9),                                                                   -- 56
('Yomawari: Lost in the Dark', 'Ein junges Mädchen erwacht in einem dunklen Wald. Sie weiß nicht, wie sie dorthin gekommen ist. Sie trifft auf eine mysteriöse Person, die ihr sagt, dass sie verflucht ist.'
, 15.00, 'ps4_yomawari.jpg', 10, 10, '123465', '2022-10-28', 'EVERYONE_10', 5, 1, 10),

-- Nintendo Switch Games        id = 3
('Bayonetta 3', 'Bayonetta ist zurück! Die beliebte Hexe kehrt zurück und muss erneut gegen übernatürliche Mächte kämpfen, um die Welt zu retten.'
, 59.99, 'sw_bayonetta3.jpg', 10, 10, '123466', '2023-12-01', 'MATURE_17', 3, 1, 11),
('Death\'s Door', 'In Death''s Door spielst du als Rabe und musst die Seelen der Verstorbenen einsammeln. Ein düsteres Abenteuer erwartet dich in dieser actiongeladenen Welt.'
, 24.99, 'sw_deathsdoor.jpg', 10, 10, '123467', '2022-07-20', 'TEEN', 3, 1, 12),
('Digimon World: Next Order', 'In Digimon World: Next Order erlebst du ein packendes Abenteuer, in dem du mit deinen Digimon-Partnern gegen gefährliche Kreaturen kämpfst und Entscheidungen triffst, die den Verlauf der Geschichte beeinflussen.'
, 49.99, 'sw_digimon.jpg', 10, 10, '123468', '2023-03-31', 'EVERYONE_10', 3, 1, 3),
('Dragon Quest Treasures', 'Begib dich auf ein episches Abenteuer in einer offenen Welt und bestimme das Schicksal deiner Reise. Kämpfe gegen furchterregende Monster und entdecke eine tiefgründige Handlung in Dragon Quest Treasures.'
, 59.99, 'sw_dragonquest.jpg', 10, 10, '123469', '2023-06-30', 'TEEN', 3, 1, 6),
('Fire Emblem: Echoes of Valentia', 'Schlüpfe in die Rolle von Alm und Celica, während du in einem epischen Krieg um das Königreich Zofia kämpfst. Strategische Kämpfe und tiefgreifende Charaktere erwarten dich.'
, 39.99, 'sw_fireemblem.jpg', 10, 10, '123470', '2017-05-19', 'TEEN', 3, 1, 46),
('It Takes Two', 'Erlebe ein einzigartiges Koop-Abenteuer, in dem du und dein Partner in die Miniaturwelt von zwei Puppen eintaucht. Gemeinsam müsst ihr Rätsel lösen und eure Beziehung stärken.'
, 39.99, 'sw_ittakestwo.jpg', 10, 10, '123471', '2021-03-26', 'EVERYONE_10', 3, 1, 14),
('Pokémon Scarlet', 'Begib dich in die Galar-Region und fange neue Pokémon in dieser aufregenden Erweiterung der Pokémon-Welt.'
, 49.99, 'sw_pkmkarmesin.jpg', 10, 10, '123472', '2023-12-31', 'EVERYONE', 3, 1, 15),
('Pokémon Shining Pearl', 'Reise in die Sinnoh-Region und werde zum Pokémon-Champion. Sammle und trainiere Pokémon in diesem spannenden Abenteuer.'
, 49.99, 'sw_pkmpearl.jpg', 10, 10, '123473', '2023-12-31', 'EVERYONE', 3, 1, 15),
('Pokémon Violet', 'Entdecke die neue region von pasio in dieser neuesten erweiterung der pokemon-welt.  Sammle neue pokemon und besiege starke gegner in dieser epischen reise.'
, 49.99, 'sw_pkmpurpur.jpg', 10, 10, '123474', '2023-12-31', 'EVERYONE', 3, 1, 15),
('Stardew Valley', 'Erbe ein heruntergekommenes Bauernhof und bringe es wieder zum Blühen. Baue Pflanzen an, kümmere dich um Tiere und interagiere mit den Einwohnern von Pelican Town.'
, 14.99, 'sw_stardewvalley.jpg', 10, 10, '123475', '2016-02-26', 'EVERYONE', 3, 1, 16),
('Tears of the Kingdom', 'Begib dich in eine Welt voller Magie und Abenteuer, in der du als Held gegen finstere Mächte kämpfen musst. Löse Rätsel und erkunde eine faszinierende Welt.'
, 29.99, 'sw_zeldatotk.jpg', 10, 10, '123476', '2023-09-15', 'TEEN', 3, 1, 13),

-- Playstation 5 Games id = 2
('Resident Evil Village', 'In dem Survival-Horrorspiel von Capcom kehrt Ethan Winters nach dem Vorfall in Raccoon City nach Europa zurück, um seine entführte Tochter zu finden.',
49.99, 'ps5_resivillage.jpg', 10, 10, '123477', '2023-09-15', 'ADULTS_ONLY', 2,  1, 18),
('Witcher 3', 'In dem Action-Rollenspiel von CD Projekt Red reist Geralt von Riva durch die Welt von The Witcher, um seine Adoptivtochter Ciri zu finden.',
 49.99, 'ps5_witcher3.jpg', 10, 10, '123478', '2023-09-15', 'ADULTS_ONLY', 2,  1, 19),
('Anno 1800', 'In dem Aufbauspiel von Ubisoft gründest du eine Stadt im 19. Jahrhundert und versuchst, sie zu einer florierenden Metropole zu machen.',
 39.99, 'ps5_anno1800.jpg', 10, 10, '123479', '2023-09-15', 'TEEN', 2,  1, 1),
('New Tales From Borderlands', 'In dem Action-Rollenspiel von 2K Games reist du mit deinen Freunden durch eine Fantasiewelt, die von Tiny Tina erschaffen wurde.',
 49.99, 'ps5_newtalesborderlands.jpg', 10, 10, '123480', '2023-09-15', 'TEEN', 2,  1, 20),
('Dragonball Z: Kakarot', 'In dem Action-Rollenspiel von Bandai Namco Entertainment erlebst du die Geschichte von Dragon Ball Z aus der Perspektive von Goku.',
 39.99, 'ps5_dbzkakarot.jpg', 10, 10, '123481', '2023-09-15', 'TEEN', 2,  1, 3),
('Call Of Duty: Modern Warfare 2', 'In dem Ego-Shooter von Activision kehrst du in die Rolle von Task Force 141 zurück, um die Welt vor einer neuen Bedrohung zu retten.',
 69.99, 'ps5_codmw2.jpg', 10, 10, '123482', '2023-09-15', 'MATURE_17', 2,  1, 22),
('Eldenring', 'In dem Action-Rollenspiel von FromSoftware erkundest du eine riesige Welt voller Gefahren und Geheimnisse.',
 59.99, 'ps5_eldenring.jpg', 10, 10, '123483', '2023-09-15', 'MATURE_17', 2,  1, 23),
('God Of War: Ragnarok', 'In der Fortsetzung des Action-Adventures von Sony Interactive Entertainment kehrst du als Kratos und Atreus nach Midgard zurück, um sich dem Ragnarök zu stellen.',
 69.99, 'ps5_gow.jpg', 10, 10, '123484', '2023-09-15', 'MATURE_17', 2,  1, 1),
('Life Is Strange_ True Colors', 'In dem Adventure-Spiel von Deck Nine Games spielst du als Alex Chen, eine junge Frau, die die Fähigkeit hat, die Emotionen anderer Menschen zu spüren.',
 39.99, 'ps5_lifeisstrangetruecolors.jpg', 10, 10, '123485', '2023-09-15', 'TEEN', 2,  1, 25),
('Need For Speed: Unbund', 'In dem Rennspiel von Electronic Arts stürzt du dich in atemberaubende Rennen durch eine offene Welt.',
 59.99, 'ps5_nfsunbound.jpg', 10, 10, '123486', '2023-09-15', 'TEEN', 2,  1, 26),

-- XBOX SX    id = 1
('Assassins Creed Valhalla', 'In dem Action-Rollenspiel von Ubisoft reist du als Eivor durch die Wikingerzeit, um England zu erobern.',
 69.99, 'xboxsx_assassinscreedvalhalla.jpg', 10, 10, '123487', '2023-09-15', 'MATURE_17', 1,  1, 1),
('Bee Simulator', 'In dem Action-Adventure von Animoon Studios erlebst du die Welt aus der Perspektive einer Biene.',
 29.99, 'xboxsx_beesimulator.jpg', 10, 10, '123488', '2023-09-15', 'EVERYONE', 1,  1, 27),
('Bus Simulator', 'In dem Bus-Simulator von Stillalive Studios steuerst du einen Bus durch eine offene Welt.',
 29.99, 'xboxsx_bussimulator.jpg', 10, 10, '123489', '2023-09-15', 'EVERYONE', 1,  1, 28),
('Construction Simulator', 'In dem Construction Simulator von Astragon Entertainment steuerst du eine Reihe von Baufahrzeugen.',
 29.99, 'xboxsx_constructionsimulator.jpg', 10, 10, '123490', '2023-09-15', 'EVERYONE', 1,  1, 29),
('Endling: Extinction Forever', 'In dem 2D-Action-Adventure von Herobeat Studios spielst du als Mutter-Fuchs, die ihre Jungen vor der Ausrottung beschützen muss.',
 29.99, 'xboxsx_endlingextionforever.jpg', 10, 10, '123491', '2023-09-15', 'EVERYONE', 1,  1, 30),
('Farming Simulator 22', 'In dem Landwirtschafts-Simulator von Giants Software verwaltest du einen Bauernhof.',
 29.99, 'xboxsx_farminsimulator22.jpg', 10, 10, '123492', '2023-09-15', 'EVERYONE', 1,  1, 31),
('Hunting Simulator 2', 'In dem Jagd-Simulator von Avalanche Software jagst du wilde Tiere in einer offenen Welt.',
 29.99, 'xboxsx_hunting2simulator.jpg', 10, 10, '123493', '2023-09-15', 'MATURE_17', 1,  1, 32),
('The Callisto Protocol', 'In dem Horror-Spiel von Striking Distance Studios spielst du als Isaac Clarke, der in einer Gefängniskolonie auf dem Jupitermond Callisto gegen Monster kämpft.',
 59.99, 'xboxsx_thecallistoprotocol.jpg', 10, 10, '123494', '2023-09-15', 'MATURE_17', 1,  1, 33),
('Werkstatt Simulator', 'In dem Werkstatt-Simulator von astragon Entertainment verwaltest du eine Werkstatt.',
 29.99, 'xboxsx_werkstattsimulator.jpg', 10, 10, '123495', '2023-09-15', 'EVERYONE', 1,  1, 29),
('Zoo Tycoon', 'In dem Zoo-Simulationsspiel von Frontier Developments verwaltest du einen Zoo.',
 49.99, 'xboxsx_zootycoon.jpg', 10, 10, '123496', '2023-09-15', 'EVERYONE', 1,  1, 34),


-- XBOX ONE    id = 4
('Grand Theft Auto V', 'In dem Action-Adventure von Rockstar Games spielst du als Michael De Santa, Franklin Clinton und Trevor Philips, die versuchen, in Los Santos ein neues Leben zu beginnen.',
69.99, 'xboxo_grandtheftauto5.jpg', 10, 10, '123496', '2023-09-15', 'MATURE_17', 4,  1, 35),
('PlayerUnknown\'s Battlegrounds', 'In dem Battle Royale-Spiel von PUBG Corporation kämpfst du als einer von 100 Spielern ums Überleben auf einer kleinen Insel.',
29.99, 'xboxo_playerunknownsbattlegrounds.jpg', 10, 10, '123496', '2023-09-15', 'MATURE_17', 4,  1, 36),
('Red Dead Redemption 2', 'In dem Action-Adventure von Rockstar Games reist du als Arthur Morgan durch den Wilden Westen, um für die Van der Linde-Bande zu arbeiten.',
 69.99, 'xboxo_reddeadredemption2.jpg', 10, 10, '123496', '2023-09-15', 'MATURE_17', 4,  1, 35),
('Call of Duty: Modern Warfare', 'In dem Ego-Shooter von Infinity Ward kämpfst du als Teil einer Elite-Einheit gegen Terroristen.',
 69.99, 'xboxo_callofdutymodernwarfare.jpg', 10, 10, '123496', '2023-09-15', 'MATURE_17', 4,  1, 22),
('Call of Duty: Black Ops 4', 'In dem Ego-Shooter von Treyarch kämpfst du in verschiedenen Modi, darunter Multiplayer, Zombies und Blackout.',
 69.99, 'xboxo_callofdutyblackops4.jpg', 10, 10, '123496', '2023-09-15', 'MATURE_17', 4,  1, 37),
('Forza Horizon 4', 'In dem Rennspiel von Playground Games fährst du durch eine offene Welt in Mexiko.',
 69.99, 'xboxo_forzahorizon4.jpg', 10, 10, '123496', '2023-09-15', 'EVERYONE', 4,  1, 38),
('Minecraft', 'In dem Sandbox-Spiel von Mojang Studios kannst du deiner Kreativität freien Lauf lassen.',
 29.99, 'xboxo_minecraft.jpg', 10, 10, '123496', '2023-09-15', 'EVERYONE', 4,  1, 39),
('Fortnite', 'In dem Battle Royale-Spiel von Epic Games kämpfst du als einer von 100 Spielern ums Überleben.',
 29.99, 'xboxo_fortnite.jpg', 10, 10, '123496', '2023-09-15', 'TEEN', 4,  1, 40),
('FIFA 22', 'In dem Fußballsimulationsspiel von EA Sports spielst du als einer deiner Lieblingsspieler oder -mannschaften.',
 59.99, 'xboxo_fifa22.jpg', 10, 10, '123496', '2023-09-15', 'EVERYONE', 4,  1, 7),
('NBA 2K23', 'In dem Basketballsimulationsspiel von Visual Concepts spielst du als einer deiner Lieblingsspieler oder -mannschaften.',
 59.99, 'xboxo_nba2k23.jpg', 10, 10, '123496', '2023-09-15', 'EVERYONE', 4,  1, 7);


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