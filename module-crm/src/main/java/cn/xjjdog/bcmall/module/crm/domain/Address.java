package cn.xjjdog.bcmall.module.crm.domain;

import com.google.common.base.Joiner;
import lombok.Data;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */

@Data
public class Address {
    private String province;
    private String city;
    private String region;
    private String location;
    private String zipCode;
    private boolean defaultAddress = false;

    /**
     * 将地址拼接为空格分离的实际地址
     * @return
     */
    public String toSingleLineAddress() {
        return Joiner.on(" ")
                .skipNulls()
                .join(province, city, region, location);
    }
}

