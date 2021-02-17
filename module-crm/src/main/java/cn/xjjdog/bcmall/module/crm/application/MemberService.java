package cn.xjjdog.bcmall.module.crm.application;

import cn.xjjdog.bcmall.module.crm.domain.Member;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */

public interface MemberService {
    Member saveMember(Member member);
    Member readMember(String memberId);
}

