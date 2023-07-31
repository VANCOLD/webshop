/* Needed to be able to truncate without violating referential integrity */
SET FOREIGN_KEY_CHECKS=0;

/* The actual truncation part;  */
TRUNCATE TABLE cart;
TRUNCATE TABLE category;
TRUNCATE TABLE console_generation;
TRUNCATE TABLE genre;
TRUNCATE TABLE position;
TRUNCATE TABLE producer;
TRUNCATE TABLE product;
TRUNCATE TABLE status;
TRUNCATE TABLE user;
TRUNCATE TABLE roles;
TRUNCATE TABLE privileges;



/* Insert section; After truncating we should be safe to insert */
INSERT INTO category ( name ) VALUES
("game"),
("console"),
("merchandise"),
("accessory"),
("giftcards"),
("sale");

INSERT INTO console_generation ( name, icon_path ) VALUES
("Playstation 5","icon_ps5.png"),
("Playstation 4","icon_ps4.png"),
("XBOX Series","icon_xbs.png"),
("XBOX One","icon_xbo.png"),
("Nintendo Switch","icon_ns.png");


INSERT INTO genre ( name ) VALUES
("Action"),
("Adventure"),
("Beat ''em up"),
("Jump and Run"),
("Racing"),
("Roleplay"),
("Simulation"),
("Sports"),
("Puzzle"),
("Shooter");


/* Password encrypted with bscript, user = password */
INSERT INTO user ( username, password, admin ) VALUES
("user","$2a$12$OYGJkmTY7HfiVNgi3yRB3OwIErDYI1b8g6VwAoY5jkhfBpEFxlxTG",false),
("admin","$2a$12$/BL/ZgCUW7z.GzFYsatgZ.nfAuuyNHRpEbmkVbe5UWryxc0495jQO",true);


INSERT INTO producer ( name ) VALUES
("Nintendo"),
("Sony"),
("Microsoft"),
("Square Enix"),
("Naughty Dog");


SET FOREIGN_KEY_CHECKS=1;