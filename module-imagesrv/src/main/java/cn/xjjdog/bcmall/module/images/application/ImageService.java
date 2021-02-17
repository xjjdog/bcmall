package cn.xjjdog.bcmall.module.images.application;

import cn.xjjdog.bcmall.module.images.persistence.ImageStoreEntity;
import cn.xjjdog.bcmall.utils.swap.GroupKv;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 * @Description 图片服务
 */
public interface ImageService {
    /**
     * 保存文件
     *
     * @param file web文件
     */
    ImageStoreEntity save(MultipartFile file, String groupName);

    /**
     * 获取分组信息：图片分组名称（key）=> 分组图片数量 (value)
     *
     * @return 分组信息
     */
    List<GroupKv> groupByImageGroup();

    /**
     * 查询所有的图片
     *
     * @param pageable 分页信息
     * @return 根据分页查询结果
     */
    Page<ImageStoreEntity> findAll(Pageable pageable);

    /**
     * 根据分组查询分页信息
     *
     * @param group    分组名称
     * @param pageable 分页信息
     * @return 根据分页查询的结果
     */
    Page<ImageStoreEntity> findAllByGroup(String group, Pageable pageable);
}
