SELECT
    id,
    total_price AS totalPrice,
    state, discount,
    member_id AS memberId,
    member_info AS memberInfo,
    skus_info AS skusInfo,
    remark,
    created_date AS createdDate
FROM order_record