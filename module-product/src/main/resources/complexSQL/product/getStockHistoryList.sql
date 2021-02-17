SELECT
    his.amount AS amount,
    his.price AS price,
    his.reason AS reason,
    his.event_type AS eventType,
    his.subtotal AS subtotal,
    his.created_date AS createdDate,
    his.sku_id AS skuId,
    his.spu_id AS spuId,
    sku.spec_label AS specLabel,
    sku.spec_flat_key AS specFlatKey,
    sku.thumbnail AS thumbnail
FROM stock_history his
	LEFT JOIN base_sku sku ON his.sku_id = sku.id
WHERE 1=1
    #{ and his.sku_id=:skuId }
    #{ and his.event_type in (:eventType) }
ORDER BY his.created_date DESC