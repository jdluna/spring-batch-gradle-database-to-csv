DROP TABLE IF EXISTS r_catalog_size_color; 
DROP TABLE IF EXISTS color_swatch; 
DROP TABLE IF EXISTS size; 
DROP TABLE IF EXISTS catalog_item; 
DROP TABLE IF EXISTS genre; 
DROP TABLE IF EXISTS product; 

DROP SEQUENCE IF EXISTS product_id_seq;
DROP SEQUENCE IF EXISTS genre_id_seq;
DROP SEQUENCE IF EXISTS catalog_item_id_seq;
DROP SEQUENCE IF EXISTS size_id_seq;
DROP SEQUENCE IF EXISTS color_swatch_id_seq;
DROP SEQUENCE IF EXISTS r_catalog_size_color_id_seq;

CREATE TABLE product (
    id BIGINT NOT NULL PRIMARY KEY,
    description VARCHAR(250) NOT NULL,
    product_image VARCHAR(250) NOT NULL
);


CREATE TABLE genre (
	id SMALLINT NOT NULL PRIMARY KEY,
	libelle VARCHAR(250) NOT NULL
);

CREATE TABLE catalog_item (
	id BIGINT NOT NULL PRIMARY KEY,
	item_number VARCHAR(250) NOT NULL,
	price DOUBLE PRECISION NOT NULL,
	fk_genre_id SMALLINT REFERENCES genre (id),
	fk_product_id BIGINT REFERENCES product (id)
);


CREATE TABLE size (
	id SMALLINT NOT NULL PRIMARY KEY,
	description VARCHAR(250) NOT NULL
);

CREATE TABLE color_swatch (
	id BIGINT NOT NULL PRIMARY KEY,
	image VARCHAR(250) NOT NULL,
	color VARCHAR(100) NOT NULL
);


CREATE TABLE r_catalog_size_color (
	id BIGINT NOT NULL PRIMARY KEY,
	fk_catalog_item_id BIGINT REFERENCES catalog_item(id),
	fk_size_id SMALLINT REFERENCES size (id),
	fk_color_swatch_id BIGINT REFERENCES color_swatch(id)
);



CREATE SEQUENCE product_id_seq MAXVALUE 9223372036854775807 NO CYCLE;
ALTER TABLE product ALTER COLUMN id SET DEFAULT nextval('product_id_seq');
ALTER SEQUENCE product_id_seq OWNED BY product.id;   

CREATE SEQUENCE genre_id_seq MAXVALUE 9223372036854775807 NO CYCLE;
ALTER TABLE genre ALTER COLUMN id SET DEFAULT nextval('genre_id_seq');
ALTER SEQUENCE genre_id_seq OWNED BY genre.id;   

CREATE SEQUENCE catalog_item_id_seq MAXVALUE 9223372036854775807 NO CYCLE;
ALTER TABLE catalog_item ALTER COLUMN id SET DEFAULT nextval('catalog_item_id_seq');
ALTER SEQUENCE catalog_item_id_seq OWNED BY catalog_item.id;   

CREATE SEQUENCE size_id_seq MAXVALUE 9223372036854775807 NO CYCLE;
ALTER TABLE size ALTER COLUMN id SET DEFAULT nextval('size_id_seq');
ALTER SEQUENCE size_id_seq OWNED BY size.id;   

CREATE SEQUENCE color_swatch_id_seq MAXVALUE 9223372036854775807 NO CYCLE;
ALTER TABLE color_swatch ALTER COLUMN id SET DEFAULT nextval('color_swatch_id_seq');
ALTER SEQUENCE color_swatch_id_seq OWNED BY color_swatch.id;   

CREATE SEQUENCE r_catalog_size_color_id_seq MAXVALUE 9223372036854775807 NO CYCLE;
ALTER TABLE r_catalog_size_color ALTER COLUMN id SET DEFAULT nextval('r_catalog_size_color_id_seq');
ALTER SEQUENCE r_catalog_size_color_id_seq OWNED BY r_catalog_size_color.id; 

INSERT INTO product (description, product_image)
VALUES ('Cardigan Sweater', 'cardigan.jpg');

INSERT INTO genre (libelle)
VALUES ('Men''s');
INSERT INTO genre (libelle)
VALUES ('Women''s');

INSERT INTO catalog_item (item_number, price, fk_genre_id, fk_product_id)
VALUES ('QWZ5671', 39.95, 1, 1);

INSERT INTO catalog_item (item_number, price, fk_genre_id, fk_product_id)
VALUES ('RRX9856', 39.95, 2, 1);

INSERT INTO size (description)
VALUES ('Small');

INSERT INTO size (description)
VALUES ('Medium');

INSERT INTO size (description)
VALUES ('Large');

INSERT INTO size (description)
VALUES ('Extra Large');

INSERT INTO color_swatch (image, color)
VALUES ('red_cardigan.jpg', 'Red');

INSERT INTO color_swatch (image, color)
VALUES ('burgundy_cardigan.jpg', 'Burgundy');

INSERT INTO color_swatch (image, color)
VALUES ('navy_cardigan.jpg', 'Navy');

INSERT INTO color_swatch (image, color)
VALUES ('black_cardigan.jpg', 'Black');

INSERT INTO r_catalog_size_color (fk_catalog_item_id, fk_size_id, fk_color_swatch_id)
VALUES (1, 2, 1);

INSERT INTO r_catalog_size_color (fk_catalog_item_id, fk_size_id, fk_color_swatch_id)
VALUES (1, 2, 2);

INSERT INTO r_catalog_size_color (fk_catalog_item_id, fk_size_id, fk_color_swatch_id)
VALUES (1, 3, 1);

INSERT INTO r_catalog_size_color (fk_catalog_item_id, fk_size_id, fk_color_swatch_id)
VALUES (1, 3, 2);

INSERT INTO r_catalog_size_color (fk_catalog_item_id, fk_size_id, fk_color_swatch_id)
VALUES (2, 1, 1);

INSERT INTO r_catalog_size_color (fk_catalog_item_id, fk_size_id, fk_color_swatch_id)
VALUES (2, 1, 2);

INSERT INTO r_catalog_size_color (fk_catalog_item_id, fk_size_id, fk_color_swatch_id)
VALUES (2, 1, 3);

INSERT INTO r_catalog_size_color (fk_catalog_item_id, fk_size_id, fk_color_swatch_id)
VALUES (2, 2, 1);

INSERT INTO r_catalog_size_color (fk_catalog_item_id, fk_size_id, fk_color_swatch_id)
VALUES (2, 2, 2);

INSERT INTO r_catalog_size_color (fk_catalog_item_id, fk_size_id, fk_color_swatch_id)
VALUES (2, 2, 3);

INSERT INTO r_catalog_size_color (fk_catalog_item_id, fk_size_id, fk_color_swatch_id)
VALUES (2, 2, 4);

INSERT INTO r_catalog_size_color (fk_catalog_item_id, fk_size_id, fk_color_swatch_id)
VALUES (2, 4, 3);

INSERT INTO r_catalog_size_color (fk_catalog_item_id, fk_size_id, fk_color_swatch_id)
VALUES (2, 4, 4);

INSERT INTO r_catalog_size_color (fk_catalog_item_id, fk_size_id, fk_color_swatch_id)
VALUES (2, 4, 2);

INSERT INTO r_catalog_size_color (fk_catalog_item_id, fk_size_id, fk_color_swatch_id)
VALUES (2, 4, 4);