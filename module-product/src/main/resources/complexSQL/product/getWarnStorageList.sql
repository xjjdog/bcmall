SELECT sku.id AS id, sku.spu_id AS spuId, sku.photos AS photos, sku.warn_storage AS warnStorage, sku.keep_storage AS keepStorage
	, sku.price AS price, sku.spec_flat_key AS specFlatKey, sku.spec_label AS specLabel, sku.thumbnail AS thumbnail, sku.storage AS storage
	, c.category_name AS category, u.unit_name AS unit, b.brand_name AS brand
FROM base_sku sku
	LEFT JOIN base_spu spu ON spu.id = sku.spu_id
	LEFT JOIN base_product_category c ON spu.category_id = c.id
	LEFT JOIN base_product_unit u ON spu.unit_id = u.id
	LEFT JOIN base_product_brand b ON spu.brand_id = b.id
WHERE sku.warn_storage >= sku.keep_storage
ORDER BY sku.last_modified_date DESC