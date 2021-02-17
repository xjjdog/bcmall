package cn.xjjdog.bcmall.utils.quickdev.magicjpa;

import cn.xjjdog.bcmall.utils.db.AbstractEntity;
import cn.xjjdog.bcmall.utils.utils.BeanUtils;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 * @Description TODO
 */
@Service
@Slf4j
@Transactional(readOnly = true)
public class MagicJpaServiceImpl implements MagicJpaService {

    @Autowired
    EntityManager em;


    @Override
    public <T extends AbstractEntity> T read(Class<T> cls, Serializable id) {
        return em.find(cls, id);
    }

    @Override
    @Transactional
    public <T extends AbstractEntity> T delete(Class<T> cls, Serializable id) {
        T e = em.find(cls, id);
        if (!Objects.isNull(e)) {
            em.remove(e);
            return e;
        }
        return null;
    }

    @Override
    @Transactional
    public <T extends AbstractEntity> T save(Class<T> cls, T e) {
        // 不在这里做JSON解析，是因为想要缩小事务的作用范围
        String id = e.getId();
        if (!StringUtils.hasLength(id)) {
            em.persist(e);
            return e;
        }

        //update
        T oldValue = em.find(cls, id);
        Preconditions.checkNotNull(oldValue);
        BeanUtils.copyPropertiesPassNullValue(e, oldValue);
        em.persist(oldValue);
        return oldValue;
    }

    @Override
    public <T extends AbstractEntity> List<T> findAll(Class<T> cls) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(cls);

        Root root = cq.from(cls);
        CriteriaQuery all = cq.select(root);

        TypedQuery allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }

    @Override
    public <T extends AbstractEntity> Page<List<T>> findAll(Class<T> cls, Integer pageSize, Integer current, Map<String, String> sort) {
        //fix page
        if (Objects.isNull(pageSize) || pageSize < 10) {
            pageSize = 10;
        }
        if (Objects.isNull(current) || current < 1) {
            current = 1;
        }
        current--;

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(cls);

        Root root = cq.from(cls);
        CriteriaQuery all = cq.select(root);

        Sort pageSort;
        //order ======
        if (CollectionUtils.isEmpty(sort)) {
            pageSort = Sort.by("id").descending();

            cq.orderBy(cb.desc(root.get("id")));
        } else {
            List<Sort.Order> pageOrders = Lists.newArrayList();
            List<Order> orders = Lists.newArrayList();
            sort.entrySet().stream().forEach(e -> {
                String k = e.getKey();
                String v = e.getValue().toLowerCase();

                if (Objects.isNull(ReflectionUtils.findField(cls, k))) {
                    log.warn("filed {} is not exists in {}", k, cls.getName());
                } else {
                    pageOrders.add(v.startsWith("asc") ? Sort.Order.asc(k) : Sort.Order.desc(k));
                    orders.add(v.startsWith("asc") ? cb.asc(root.get(k)) : cb.desc(root.get(k)));
                }
            });
            pageSort = Sort.by(pageOrders);
            cq.orderBy(orders);
        }
        //order ======

        TypedQuery allQuery = em.createQuery(all);

        allQuery.setFirstResult(current * pageSize);
        allQuery.setMaxResults(pageSize);

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cb.count(countQuery.from(cls)));
        Long count = em.createQuery(countQuery).getSingleResult();


        PageRequest pageable = PageRequest.of(current, pageSize, pageSort);
        Page page = new PageImpl(allQuery.getResultList(), pageable, count);

        return page;
    }
}
