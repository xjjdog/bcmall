package cn.xjjdog.bcmall.module.images.controller;

import cn.xjjdog.bcmall.module.images.persistence.ImageStoreEntity;
import cn.xjjdog.bcmall.module.images.application.ImageService;
import cn.xjjdog.bcmall.utils.swap.GroupKv;
import cn.xjjdog.bcmall.utils.web.Result;
import cn.xjjdog.bcmall.utils.utils.PageHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 * 图片文件管理
 */
@RestController
@RequestMapping("/api/img")
@RequiredArgsConstructor
public class ImageController {
    final ImageService imageService;

    /**
     * 上传图片到默认分组
     *
     * @param file 上传的文件
     */
    @PostMapping("upload")
    @PreAuthorize("hasRole('ROLE_USER')")
    Result<ImageStoreEntity> handleFileUpload(@RequestPart("file") MultipartFile file) {

        ImageStoreEntity data = imageService.save(file, null);

        Result<ImageStoreEntity> result = new Result<>();
        result.setMsg("You successfully uploaded " + file.getOriginalFilename());
        result.setData(data);

        return result;
    }

    /**
     * 上传图片到指定分组
     *
     * @param file 上传的文件
     */
    @PostMapping("upload/{group}")
    @PreAuthorize("hasRole('ROLE_USER')")
    Result<ImageStoreEntity> handleFileUploadToGroup(@PathVariable String group, @RequestPart("file") MultipartFile file) {
        Result<ImageStoreEntity> result = new Result<>();

        if (StringUtils.isEmpty(group)) {
            result.setStatus(200);
            result.setCode(500);
            result.setMsg("分组不能为空");
            return result;
        }

        ImageStoreEntity data = imageService.save(file, group);

        result.setMsg("You successfully uploaded " + file.getOriginalFilename());
        result.setData(data);

        return result;
    }

    @GetMapping("findAllPage")
    Result<Page<ImageStoreEntity>> handleList(Integer pageSize, Integer current, String group) {
        Result<Page<ImageStoreEntity>> data = new Result<>();
        Pageable pageable = PageHelper.pageableDescById(pageSize, current);
        Page<ImageStoreEntity> page;
        if (StringUtils.isEmpty(group)) {
            page = imageService.findAll(pageable);
        } else {
            page = imageService.findAllByGroup(group, pageable);
        }
        data.setData(page);
        return data;
    }

    @GetMapping("groups")
    Result<List<GroupKv>> handleImageGroup() {
        return Result.of(imageService.groupByImageGroup());
    }

}