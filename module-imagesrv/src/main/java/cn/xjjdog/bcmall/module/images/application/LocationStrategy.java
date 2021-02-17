package cn.xjjdog.bcmall.module.images.application;

import cn.xjjdog.bcmall.module.images.application.location.Location;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */
@Component
@RequiredArgsConstructor
public class LocationStrategy implements InitializingBean {
    private final ImageProperties properties;
    /**
     * 所有location类型的Bean，都会出现在这里
     */
    private final List<Location> locations;

    /**
     * 用来缓存图片保存位置策略，快速定位
     */
    private final Map<String, Location> strategy = Maps.newHashMap();

    public Location getLocation(String type) {
        Preconditions.checkNotNull(type);

        return strategy.get(type);
    }

    @Override
    public void afterPropertiesSet() {
        locations.forEach(l -> strategy.put(l.type(), l));

        this.checkConfig();
    }

    /**
     * 一个简单的配置检查，引导完成正确的配置
     */
    void checkConfig() {
        String type = properties.getType();

        Preconditions.checkArgument(strategy.containsKey(type), "can not find image store type: " + properties.getType() +
                " , builtin type is [file,oss] , please check your config");

        boolean error1 = type.equals(ImageProperties.STORE_FILE) && Objects.isNull(properties.getFile());
        boolean error2 = type.equals(ImageProperties.STORE_OSS) && Objects.isNull(properties.getOss());

        Preconditions.checkArgument(!error1 && !error2,
                "You supplied " + type + " , but config is null");
    }
}
