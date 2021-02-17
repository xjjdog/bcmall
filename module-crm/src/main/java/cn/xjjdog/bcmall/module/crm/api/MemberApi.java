package cn.xjjdog.bcmall.module.crm.api;

import cn.xjjdog.bcmall.module.crm.util.Transform;
import cn.xjjdog.bcmall.module.crm.domain.Member;
import cn.xjjdog.bcmall.module.crm.persistence.MemberEntity;
import cn.xjjdog.bcmall.module.crm.persistence.dao.MemberDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */

@Service
@RequiredArgsConstructor
public class MemberApi {
    final MemberDao memberDao;

    public Member readMember(String memberId){
        MemberEntity memberEntity = memberDao.findById(memberId).orElse(null);
        Member member = Transform.T.fromMemberEntity(memberEntity);
        return member;
    }
}

