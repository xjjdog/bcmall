package cn.xjjdog.bcmall.utils.utils;

import com.google.common.collect.ObjectArrays;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.FeatureDescriptor;
import java.util.stream.Stream;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */
public final class BeanUtils {
    final static String[] ignoreProperties = new String[]{
            "id",
            "createdDate",
            "lastModifiedDate",
            "version"
    };

    static String[] getNullPropertyNames(Object source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        return Stream.of(wrappedSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }

    public static void copyPropertiesPassNullValue(Object source, Object target) {
        final String[] nullProperties = getNullPropertyNames(source);
        final String[] ignores = ObjectArrays.concat(nullProperties, ignoreProperties, String.class);
        org.springframework.beans.BeanUtils.copyProperties(source, target, ignores);
    }

    public static void copyProperties(Object source, Object target) {
        org.springframework.beans.BeanUtils.copyProperties(source, target, ignoreProperties);
    }
}
