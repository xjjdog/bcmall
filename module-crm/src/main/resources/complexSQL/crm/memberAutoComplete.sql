SELECT
    id,
    nick_name AS nickName,
    head_url AS headUrl,
    phone
FROM member WHERE nick_name LIKE :q '%'
LIMIT 10