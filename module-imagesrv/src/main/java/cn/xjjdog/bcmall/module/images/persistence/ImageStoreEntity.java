package cn.xjjdog.bcmall.module.images.persistence;

import cn.xjjdog.bcmall.utils.db.AbstractEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 * 图片实体
 */
@Data
@Entity
@Table(name = "image_store")
public class ImageStoreEntity extends AbstractEntity {
    /**
     * 存储类型
     */
    @NotBlank
    String storageType;
    /**
     * 原始的文件名称
     */
    @NotBlank
    String originalFilename;
    /**
     * 真实的路径，用于寻址
     */
    @NotBlank
    String realPath;
    /**
     * 类型
     */
    @NotBlank
    String contentType;
    /**
     * 文件大小
     */
    @NotNull
    Long fileSize;
    /**
     * 图片被引用数量
     */
    Integer refCount;
    /**
     * 图片分组，我们只支持一层分组
     */
    String imgGroup;
    /**
     * 最终可引用的图片地址
     */
    String refUri;
}
