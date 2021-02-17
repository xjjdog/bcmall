SELECT
    COUNT(category.id) AS count,
    category.category_name AS item
FROM base_spu spu
	LEFT JOIN base_product_category category ON spu.category_id = category.id
GROUP BY category.id