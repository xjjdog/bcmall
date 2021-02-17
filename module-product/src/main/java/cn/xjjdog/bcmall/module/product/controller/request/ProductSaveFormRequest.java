package cn.xjjdog.bcmall.module.product.controller.request;

import cn.xjjdog.bcmall.module.product.domain.product.SpecificationRelocateCommand;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */
@Data
public class ProductSaveFormRequest {
    /**
     * 规格表
     */
    @Valid
    @NotNull
    SpecificationRelocateCommand relocateCommand;
    /**
     * id
     */
    private String id ;
    /**
     * 商品简短的名称
     */
    @NotBlank(message = "请提供一个简短的名称")
    private String shortName;
    /**
     * 分类信息
     */
    @NotBlank
    private String categoryId;
    /**
     * 保存品牌关系
     */
    private String brandId;
    /**
     * 保存单位关系
     */
    @NotBlank
    private String unitId;
    /**
     * 使用，分割的图像列表
     */
    private String photos;
    /**
     * 缩略图
     */
    @NotBlank
    private String thumbnail;


    private boolean flagHot = false;
    private boolean flagNew = false;
    private boolean flagRecommend = false;
}
