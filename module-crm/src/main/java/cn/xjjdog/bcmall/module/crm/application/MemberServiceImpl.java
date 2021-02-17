package cn.xjjdog.bcmall.module.crm.application;

import cn.xjjdog.bcmall.module.crm.util.Transform;
import cn.xjjdog.bcmall.module.crm.domain.Member;
import cn.xjjdog.bcmall.module.crm.persistence.MemberEntity;
import cn.xjjdog.bcmall.module.crm.persistence.dao.MemberDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    final MemberDao memberDao;

    @Override
    @Transactional
    public Member saveMember(Member member) {
        MemberEntity memberEntity = Transform.T.fromMember(member);
        memberEntity = memberDao.save(memberEntity);
        member.setId(memberEntity.getId());
        return member;
    }

    @Override
    public Member readMember(String memberId) {
        MemberEntity memberEntity = memberDao.findById(memberId).orElse(null);
        Member member = Transform.T.fromMemberEntity(memberEntity);
        return member;
    }
}

