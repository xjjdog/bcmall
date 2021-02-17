SELECT
his.price as price,
his.sku_id AS skuId,
his.created_date  AS createdDate,
sku.spec_label AS specLabel,
sku.spec_flat_key AS specFlatKey
FROM base_product_price_history his
LEFT JOIN base_sku sku ON sku.id = his.sku_id
WHERE
his.spu_id = :spuId
ORDER BY his.created_date ASC