package cn.xjjdog.bcmall.module.images.application;

import cn.xjjdog.bcmall.module.images.persistence.ImageStoreEntity;
import cn.xjjdog.bcmall.module.images.persistence.dao.ImageStoreDao;
import cn.xjjdog.bcmall.utils.swap.GroupKv;
import cn.xjjdog.bcmall.utils.utils.Snowflake;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 * 图片信息
 */
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageStoreDao imageStoreDao;
    private final ImageProperties properties;
    private final LocationStrategy locationStrategy;

    private static final String DEFAULT_GROUP_NAME = "未分组";

    @Override
    public ImageStoreEntity save(MultipartFile file, String groupName) {

        Preconditions.checkState(!file.isEmpty());

        String type = properties.getType();

        String orig = StringUtils.cleanPath(file.getOriginalFilename());
        String name = Snowflake.createId() + "";
        String ref;

        try (InputStream in = file.getInputStream()) {
            ref = locationStrategy.getLocation(type).copy(in, name);
        } catch (Exception ex) {
            throw new RuntimeException("error while upload " + orig, ex);
        }

        ImageStoreEntity image = new ImageStoreEntity();
        image.setImgGroup(Strings.isNullOrEmpty(groupName) ? DEFAULT_GROUP_NAME : groupName);
        image.setRefCount(0);
        image.setRefUri(ref);
        image.setRealPath(name);
        image.setFileSize(file.getSize());
        image.setStorageType(type);
        image.setContentType(file.getContentType());
        image.setOriginalFilename(orig);
        return imageStoreDao.save(image);
    }


    @Override
    public Page<ImageStoreEntity> findAll(Pageable pageable) {
        return imageStoreDao.findAll(pageable);
    }

    @Override
    public List<GroupKv> groupByImageGroup() {
        return imageStoreDao.groupByImageGroup();
    }

    @Override
    public Page<ImageStoreEntity> findAllByGroup(String group, Pageable pageable) {
        return imageStoreDao.findAllByGroup(group, pageable);
    }
}
