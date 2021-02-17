package cn.xjjdog.bcmall.module.images.application.location;

import cn.xjjdog.bcmall.module.images.application.ImageProperties;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 * @Description 文件上传，上传到本地目录；如你所见，只支持单机，不支持分布式
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class FileLocation implements
        Location,
        InitializingBean,
        WebMvcConfigurer {


    private final ImageProperties properties;

    private Path rootPath;

    @Override
    public String type() {
        return ImageProperties.STORE_FILE;
    }

    /**
     * 将数据流简单的复制到目标目录，并返回前端可以直接访问的图片地址
     *
     * @param in      数据流
     * @param dstName 目标文件名称
     * @return 可访问链接
     */
    @Override
    public String copy(InputStream in, String dstName) {
        Path dstPath = rootPath.resolve(dstName);
        try {
            Files.copy(in, dstPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new RuntimeException("File to store file", ex);
        }
        String prefix = properties.getFile().getPrefix();
        return prefix.concat(dstName);
    }


    /**
     * 自动在前缀后追加
     *
     * @return 返回修正后的地址
     */
    private void prefixFix() {
        String prefix = properties.getFile().getPrefix();
        if (!prefix.endsWith("/")) {
            prefix = prefix.concat("/");
        }
        properties.getFile().setPrefix(prefix);
    }

    /**
     * 如果开启了文件上传模式，则注册一个全局的图片控制器，用于响应前端的请求
     *
     * @param registry 注册句柄
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!ok()) {
            return;
        }

        String prefix = properties.getFile().getPrefix();
        String root = properties.getFile().getRoot();
        prefix = prefix.concat("**");

        String fineStr = root.replace("\\\\", "\\").replace("\\", "/");
        String rootDir = "file:" + fineStr;
        registry.addResourceHandler(prefix).addResourceLocations(rootDir);

    }

    private boolean ok() {
        return properties.getType().equals(type());
    }

    /**
     * 1. 自动创建目录
     * 2. 修正URL，添加"/"
     */
    @Override
    public void afterPropertiesSet() {
        if (!ok()) {
            return;
        }

        rootPath = Paths.get(properties.getFile().getRoot());
        if (Files.exists(rootPath)) {
            return;
        }

        Try.of(() -> Files.createDirectories(rootPath)).getOrNull();
        //修正URL
        prefixFix();
    }
}
