package cn.xjjdog.bcmall.module.crm.persistence.dao;

import cn.xjjdog.bcmall.module.crm.persistence.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */

public interface MemberDao extends JpaRepository<MemberEntity,String> {
}

