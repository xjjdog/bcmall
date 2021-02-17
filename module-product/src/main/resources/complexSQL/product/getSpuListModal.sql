SELECT
	spu.id,
	spu.thumbnail,
    spu.short_name AS shortName,
    c.category_name AS category,
    u.unit_name AS unit,
    b.brand_name AS brand
FROM base_spu spu
	LEFT JOIN base_product_category c ON spu.category_id = c.id
	LEFT JOIN base_product_unit u ON spu.unit_id = u.id
	LEFT JOIN base_product_brand b ON spu.brand_id = b.id
WHERE spu.state=1
    #{ and spu.category_id = :categoryId }
    #{ and spu.brand_id = :brandId }
ORDER BY spu.last_modified_date DESC