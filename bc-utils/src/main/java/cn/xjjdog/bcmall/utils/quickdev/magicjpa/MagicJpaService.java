package cn.xjjdog.bcmall.utils.quickdev.magicjpa;

import cn.xjjdog.bcmall.utils.db.AbstractEntity;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */
public interface MagicJpaService {
    /**
     * 根据主键读取数据
     *
     * @param cls 实体类型
     * @param id  ID
     * @return 返回查询到的对象
     */
    <T extends AbstractEntity> T read(Class<T> cls, Serializable id);

    /**
     * 根据主键删除数据
     *
     * @param cls 实体类型
     * @param id  ID
     * @return 删除对象
     */
    <T extends AbstractEntity> T delete(Class<T> cls, Serializable id);

    /**
     * 创建记录
     *
     * @param cls 实体类型
     * @param e   要保存的对象
     * @return 返回保存后的对象, ID已填充
     */
    <T extends AbstractEntity> T save(Class<T> cls, T e) ;

    /**
     * 一次性查询所有数据
     *
     * @param cls 实体类型
     * @return 返回所有数据
     */
    <T extends AbstractEntity> List<T> findAll(Class<T> cls);

    /**
     * 分页查询数据
     *
     * @param cls      实体类型
     * @param pageSize 每页条数
     * @param current  当前页数（1开始）
     * @param sort     排序参数
     * @return 返回分页查询的对象Page
     */
    <T extends AbstractEntity> Page<List<T>> findAll(Class<T> cls, Integer pageSize, Integer current, Map<String, String> sort);
}
