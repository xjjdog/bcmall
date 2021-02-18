SELECT
	sku.id AS id,
	sku.spu_id AS spuId,
	sku.barcode AS barcode,
	sku.spec_code AS specCode,
    sku.thumbnail AS thumbnail,
    sku.spec_label AS specLabel,
    sku.spec_flat_key AS specFlatKey,
    sku.storage AS storage,
    sku.warn_storage AS warnStorage,
    sku.keep_storage AS keepStorage,
    sku.price AS price,

    spu.state AS state ,
    spu.short_name AS shortName,
    c.category_name AS category,
    u.unit_name AS unit,
    b.brand_name AS brand
FROM base_sku sku
	LEFT JOIN base_spu spu ON sku.spu_id = spu.id
	LEFT JOIN base_product_category c ON spu.category_id = c.id
	LEFT JOIN base_product_unit u ON spu.unit_id = u.id
	LEFT JOIN base_product_brand b ON spu.brand_id = b.id
WHERE  spu.state=1
    #{ and spu.category_id = :categoryId }
    #{ and spu.brand_id = :brandId }
ORDER BY sku.last_modified_date DESC