package cn.xjjdog.bcmall.module.images.application.location;

import cn.xjjdog.bcmall.module.images.application.ImageProperties;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 * @Description 上传到阿里的OSS服务，然后通过对bucket的公网读权限进行开放，提供直接访问
 */
@Component
@RequiredArgsConstructor
public class OssLocation implements Location, InitializingBean {
    private final ImageProperties properties;

    private OSSClient ossClient;

    @Override
    public String type() {
        return ImageProperties.STORE_OSS;
    }

    /**
     * 直接将文件流上传到阿里云OSS，并返回可以直接访问的地址
     *
     * @param in      数据流
     * @param dstName 目标文件名称
     * @return 可访问链接
     */
    @Override
    public String copy(InputStream in, String dstName) {
        try {
            String bucketName = properties.getOss().getBucketName();
            PutObjectRequest request = new PutObjectRequest(bucketName, dstName, in);
            ossClient.putObject(request);
        } catch (Exception ex) {
            throw new RuntimeException("Error while upload to OSS ", ex);
        }
        return properties.getOss().getPrefix().concat(dstName);
    }


    @Override
    public void afterPropertiesSet() {
        if (!properties.getType().equals(type())) {
            return;
        }
        ossClient = new OSSClient(
                properties.getOss().getEndpoint(),
                properties.getOss().getAccessKeyId(),
                properties.getOss().getSecretAccessKey());
    }
}
