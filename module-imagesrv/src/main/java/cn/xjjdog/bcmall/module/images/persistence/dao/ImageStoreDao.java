package cn.xjjdog.bcmall.module.images.persistence.dao;

import cn.xjjdog.bcmall.module.images.persistence.ImageStoreEntity;
import cn.xjjdog.bcmall.utils.db.BaseRepository;
import cn.xjjdog.bcmall.utils.swap.GroupKv;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 * @Description TODO
 */
@Repository
public class ImageStoreDao extends BaseRepository<ImageStoreEntity, String> {

    public ImageStoreDao(EntityManager em) {
        super(ImageStoreEntity.class, em);
    }

    /**
     * 查询分组和图片分组数量
     */
    public List<GroupKv> groupByImageGroup() {
        String hql = "select new " + GroupKv.class.getName() + "(e.imgGroup,count(*)) " +
                "from ImageStoreEntity e group by e.imgGroup";

        Query q = em.createQuery(hql);
        return q.getResultList();
    }

    /**
     * 根据分组，查询图片列表
     *
     * @param group
     * @param pageable
     * @return
     */
    public Page<ImageStoreEntity> findAllByGroup(String group, Pageable pageable) {
        Specification specification = (root, cq, cb) -> {
            Predicate predicate = cb.conjunction();
            predicate.getExpressions().add(cb.equal(root.get("imgGroup"), group));
            cq.where(predicate);
            return predicate;
        };
        return findAll(specification, pageable);
    }
}
