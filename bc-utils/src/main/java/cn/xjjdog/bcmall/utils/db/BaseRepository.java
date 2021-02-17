package cn.xjjdog.bcmall.utils.db;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 * 配置了EntityManager，可能会有QueryDSL
 */
public abstract class BaseRepository<T, ID> extends SimpleJpaRepository<T, ID> {
    protected final EntityManager em;


    public BaseRepository(Class<T> cls, EntityManager em) {
        super(cls, em);
        this.em = em;
    }
}
