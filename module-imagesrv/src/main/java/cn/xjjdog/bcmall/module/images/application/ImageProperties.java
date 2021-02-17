package cn.xjjdog.bcmall.module.images.application;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 * @Description 图片上传的配置
 */
@Data
@ConfigurationProperties(prefix = "bcmall.upload.image")
@Component
public class ImageProperties {
    /**
     * 文件存储，适合单机
     */
    public static final String STORE_FILE = "file";
    /**
     * OSS存储，阿里云oss
     */
    public static final String STORE_OSS = "oss";

    /**
     * 当前选用的存储配置
     */
    private String type = STORE_FILE;
    /**
     * 配置oss的属性
     */
    private OssProperties oss;
    /**
     * 配置本地上传的属性
     */
    private FileProperties file;

    @Data
    public static class OssProperties {
        private String endpoint;
        private String accessKeyId;
        private String secretAccessKey;
        private String bucketName;

        /**
         * 阿里云OSS公网前缀，用来下载文件
         */
        private String prefix;
    }

    @Data
    public static class FileProperties {
        /**
         * 存放的本地目录，如果没有则自动创建一个
         */
        private String root;
        /**
         * 本地目录映射图片的URL地址
         */
        private String prefix;
    }
}
