SELECT prod.id AS product_id,
       prod.description AS product_description,
       prod.product_image,
       cait.id AS catalog_item_id,
       cait.item_number,
       cait.price
FROM product AS prod
INNER JOIN catalog_item AS cait ON prod.id = cait.fk_product_id
INNER JOIN genre AS genr ON cait.fk_genre_id = genr.id
WHERE 1 = 1