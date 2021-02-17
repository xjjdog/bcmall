package cn.xjjdog.bcmall.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */
@Data
public class BasicInfo {
    private String id;
    private Date createdDate;
    private Date lastModifiedDate;
    @JsonIgnore
    private Long version;
}
