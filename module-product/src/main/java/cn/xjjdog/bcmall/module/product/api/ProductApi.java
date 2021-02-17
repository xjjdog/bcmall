package cn.xjjdog.bcmall.module.product.api;

import cn.xjjdog.bcmall.module.product.application.ProductService;
import cn.xjjdog.bcmall.module.product.application.SingleStorageChangeCommand;
import cn.xjjdog.bcmall.module.product.domain.product.StandardProductUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Copyright (c) 2021. All Rights Reserved.
 * <p>
 * 对外接口
 *
 * @author xjjdog
 */

@Service
@RequiredArgsConstructor
public class ProductApi {
    final ProductService productService;

    public StandardProductUnit readSpu(String id) {
        return productService.readSpu(id);
    }

    public void stockOut(SingleStorageChangeCommand request) {
        productService.stockOut(request);
    }
}

