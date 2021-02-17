package cn.xjjdog.bcmall.module.images.application.location;

import java.io.InputStream;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 * @Description 把输入流保存到特定的存储位置，可以是单机，或者分布式
 */
public interface Location {
    /**
     * 拷贝文件流到目标位置，并保存到数据库中
     *
     * @param in
     * @param dstName
     * @return
     */
    String copy(InputStream in, String dstName);

    /**
     * 用来表明当前的组件类型
     *
     * @return
     */
    String type();
}
