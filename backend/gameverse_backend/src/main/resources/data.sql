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
INSERT INTO category ( cid, name ) VALUES
(0,"game"),
(1,"console"),
(2,"merchandise"),
(3,"accessory"),
(4,"giftcards"),
(5,"sale");


INSERT INTO status ( sid, status ) VALUES
(0,"ongoing"),
(1,"aborted"),
(2,"completed"),
(3,"terminated"),
(4,"in transaction"),
(5,"network error");


INSERT INTO console_generation ( cg_id, name, icon_path ) VALUES
(0,"Playstation 5","icon_ps5.png"),
(1,"Playstation 4","icon_ps4.png"),
(2,"XBOX Series","icon_xbs.png"),
(3,"XBOX One","icon_xbo.png"),
(4,"Nintendo Switch","icon_ns.png");


INSERT INTO genre ( gid, name ) VALUES
(0,"Action"),
(1,"Adventure"),
(2,"Beat ''em up"),
(3,"Jump and Run"),
(4,"Racing"),
(5,"Roleplay"),
(6,"Simulation"),
(7,"Sports"),
(8,"Puzzle"),
(9,"Shooter");


/* Password encrypted with bscript, user = password */
INSERT INTO user ( uid, username, password, admin ) VALUES
(0,"user","$2a$12$OYGJkmTY7HfiVNgi3yRB3OwIErDYI1b8g6VwAoY5jkhfBpEFxlxTG",false),
(1,"admin","$2a$12$/BL/ZgCUW7z.GzFYsatgZ.nfAuuyNHRpEbmkVbe5UWryxc0495jQO",true);


INSERT INTO producer ( proid, name ) VALUES
(0,"Nintendo"),
(1,"Sony"),
(2,"Microsoft"),
(3,"Square Enix"),
(4,"Naughty Dog");


SET FOREIGN_KEY_CHECKS=1;