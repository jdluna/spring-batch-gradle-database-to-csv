DROP TABLE IF EXISTS product.r_catalog_size_color; 
DROP TABLE IF EXISTS product.color_swatch; 
DROP TABLE IF EXISTS product.size; 
DROP TABLE IF EXISTS product.catalog_item; 
DROP TABLE IF EXISTS product.genre; 
DROP TABLE IF EXISTS product.product; 

DROP SEQUENCE IF EXISTS product.product_id_seq;
DROP SEQUENCE IF EXISTS product.genre_id_seq;
DROP SEQUENCE IF EXISTS product.catalog_item_id_seq;
DROP SEQUENCE IF EXISTS product.size_id_seq;
DROP SEQUENCE IF EXISTS product.color_swatch_id_seq;
DROP SEQUENCE IF EXISTS product.r_catalog_size_color_id_seq;

DROP SCHEMA IF EXISTS product CASCADE;

CREATE SCHEMA product;

CREATE TABLE product.product (
    id BIGINT NOT NULL PRIMARY KEY,
    description VARCHAR(250) NOT NULL,
    product_image VARCHAR(250) NOT NULL
);

CREATE TABLE product.genre (
	id SMALLINT NOT NULL PRIMARY KEY,
	libelle VARCHAR(250) NOT NULL
);

CREATE TABLE product.catalog_item (
	id BIGINT NOT NULL PRIMARY KEY,
	item_number VARCHAR(250) NOT NULL,
	price DOUBLE PRECISION NOT NULL,
	fk_genre_id SMALLINT REFERENCES product.genre (id),
	fk_product_id BIGINT REFERENCES product.product (id)
);


CREATE TABLE product.size (
	id SMALLINT NOT NULL PRIMARY KEY,
	description VARCHAR(250) NOT NULL
);

CREATE TABLE product.color_swatch (
	id BIGINT NOT NULL PRIMARY KEY,
	image VARCHAR(250) NOT NULL,
	color VARCHAR(100) NOT NULL
);

CREATE TABLE product.r_catalog_size_color (
	id BIGINT NOT NULL PRIMARY KEY,
	fk_catalog_item_id BIGINT REFERENCES product.catalog_item(id),
	fk_size_id SMALLINT REFERENCES product.size (id),
	fk_color_swatch_id BIGINT REFERENCES product.color_swatch(id)
);

CREATE SEQUENCE product.product_id_seq MAXVALUE 9223372036854775807 NO CYCLE;
ALTER TABLE product.product ALTER COLUMN id SET DEFAULT nextval('product.product_id_seq');
ALTER SEQUENCE product.product_id_seq OWNED BY product.product.id;   

CREATE SEQUENCE product.genre_id_seq MAXVALUE 9223372036854775807 NO CYCLE;
ALTER TABLE product.genre ALTER COLUMN id SET DEFAULT nextval('product.genre_id_seq');
ALTER SEQUENCE product.genre_id_seq OWNED BY product.genre.id;   

CREATE SEQUENCE product.catalog_item_id_seq MAXVALUE 9223372036854775807 NO CYCLE;
ALTER TABLE product.catalog_item ALTER COLUMN id SET DEFAULT nextval('product.catalog_item_id_seq');
ALTER SEQUENCE product.catalog_item_id_seq OWNED BY product.catalog_item.id;   

CREATE SEQUENCE product.size_id_seq MAXVALUE 9223372036854775807 NO CYCLE;
ALTER TABLE product.size ALTER COLUMN id SET DEFAULT nextval('product.size_id_seq');
ALTER SEQUENCE product.size_id_seq OWNED BY product.size.id;   

CREATE SEQUENCE product.color_swatch_id_seq MAXVALUE 9223372036854775807 NO CYCLE;
ALTER TABLE product.color_swatch ALTER COLUMN id SET DEFAULT nextval('product.color_swatch_id_seq');
ALTER SEQUENCE product.color_swatch_id_seq OWNED BY product.color_swatch.id;   

CREATE SEQUENCE product.r_catalog_size_color_id_seq MAXVALUE 9223372036854775807 NO CYCLE;
ALTER TABLE product.r_catalog_size_color ALTER COLUMN id SET DEFAULT nextval('product.r_catalog_size_color_id_seq');
ALTER SEQUENCE product.r_catalog_size_color_id_seq OWNED BY product.r_catalog_size_color.id;

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