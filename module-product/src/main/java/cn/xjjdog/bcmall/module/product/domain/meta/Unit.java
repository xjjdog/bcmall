package cn.xjjdog.bcmall.module.product.domain.meta;

import cn.xjjdog.bcmall.utils.BasicInfo;
import lombok.Data;
import lombok.Getter;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 * 商品单位
 */
@Data
public class Unit extends BasicInfo {
    @Getter
    public enum MeasureType {
        Piece("记件", 1),
        Weight("记重", 2),
        Time("记时", 3),
        ;

        private String name;
        private int value;

        MeasureType(String name, int value) {
            this.name = name;
            this.value = value;
        }
    }

    /**
     * 显示名称，比如克、件
     */
    private String unitName;
    /**
     * 单位类型
     */
    private MeasureType measureType;
    /**
     * 排序号
     */
    private Integer sortNum;
}
