package cn.xjjdog.bcmall.module.order.persistence.dao;

import cn.xjjdog.bcmall.module.order.persistence.ExpressCompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */

public interface ExpressCompanyDao extends JpaRepository<ExpressCompanyEntity,String> {
}

