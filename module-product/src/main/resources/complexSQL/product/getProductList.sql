SELECT sum(sku.storage) AS totalStorage,
    spu.id AS id,
    spu.thumbnail AS thumbnail,
    spu.short_name AS shortName,
    spu.state AS state ,
    spu.spec_defined AS specDefined,
    spu.flag_hot AS flagHot,
    spu.flag_new AS flagNew,
    spu.flag_recommend AS flagRecommend,
    c.category_name AS category,
    u.unit_name AS unit,
    b.brand_name AS brand
FROM base_spu spu
	LEFT JOIN base_sku sku ON spu.id = sku.spu_id
	LEFT JOIN base_product_category c ON spu.category_id = c.id
	LEFT JOIN base_product_unit u ON spu.unit_id = u.id
	LEFT JOIN base_product_brand b ON spu.brand_id = b.id
WHERE  1=1
    #{ and spu.category_id = :categoryId }
    #{ and spu.brand_id = :brandId }
    #{ and spu.state in (:state) }
GROUP BY spu.id
ORDER BY spu.created_date desc