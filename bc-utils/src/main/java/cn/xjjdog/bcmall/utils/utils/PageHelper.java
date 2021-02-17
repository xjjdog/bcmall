package cn.xjjdog.bcmall.utils.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Objects;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */
public final class PageHelper {
    static final int PAGE_SIZE_MIN = 10;
    static final int PAGE_SIZE_MAX = 1000;

    public static Pageable pageableDescById(Integer pageSize, Integer current) {
        return pageableDesc(pageSize, current, "id");
    }

    public static Pageable pageableDesc(Integer pageSize, Integer current, String sort) {
        if (Objects.isNull(pageSize)) {
            pageSize = 0;
        }
        if (Objects.isNull(current)) {
            current = 0;
        }

        if (pageSize < PAGE_SIZE_MIN) {
            pageSize = PAGE_SIZE_MIN;
        } else if (pageSize > PAGE_SIZE_MAX) {
            pageSize = PAGE_SIZE_MAX;
        }

        if (current < 1) {
            current = 1;
        }

        current = current - 1;
        Sort s = Sort.by(sort).descending();
        Pageable pageable = PageRequest.of(current, pageSize, s);
        return pageable;
    }
}
