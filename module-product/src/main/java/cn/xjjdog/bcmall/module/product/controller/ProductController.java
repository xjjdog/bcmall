package cn.xjjdog.bcmall.module.product.controller;

import cn.xjjdog.bcmall.module.product.application.ProductService;
import cn.xjjdog.bcmall.module.product.application.SingleStorageChangeCommand;
import cn.xjjdog.bcmall.module.product.application.TransferCommand;
import cn.xjjdog.bcmall.module.product.controller.request.BatchStockInFormRequest;
import cn.xjjdog.bcmall.module.product.controller.request.ChangeProductStateRequest;
import cn.xjjdog.bcmall.module.product.controller.request.ProductSaveFormRequest;
import cn.xjjdog.bcmall.module.product.domain.product.StandardProductUnit;
import cn.xjjdog.bcmall.utils.quickdev.smartjdbc.SmartJDBC;
import cn.xjjdog.bcmall.utils.quickdev.smartjdbc.TwoDimensioningResult;
import cn.xjjdog.bcmall.utils.quickdev.smartjdbc.TwoDimensioningResultWithPage;
import cn.xjjdog.bcmall.utils.utils.JSON;
import cn.xjjdog.bcmall.utils.web.Result;
import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */
@RestController
@RequestMapping("/api/product")
@Api("产品管理-后台管理专用")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    SmartJDBC smartJDBC;

    @ApiOperation("保存/修改商品基本信息、规格信息、规格明细")
    @PostMapping("/saveSpu")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Result<Boolean> saveSpu(@Valid @RequestBody ProductSaveFormRequest saveRequest) {
        StandardProductUnit spu;
        if (StringUtils.hasLength(saveRequest.getId())) {
            spu = productService.readSpu(saveRequest.getId());
            Preconditions.checkNotNull(spu, "未找到这个商品");
        } else {
            spu = new StandardProductUnit();
        }
        spu.setShortName(saveRequest.getShortName());
        spu.setThumbnail(saveRequest.getThumbnail());
        spu.setPhotos(saveRequest.getPhotos());
        spu.setFlagHot(saveRequest.isFlagHot());
        spu.setFlagNew(saveRequest.isFlagNew());
        spu.setFlagRecommend(saveRequest.isFlagRecommend());
        if (StringUtils.hasLength(saveRequest.getBrandId())) {
            spu.setBrand(productService.readBrand(saveRequest.getBrandId()));
        }
        spu.setCategory(productService.readCategory(saveRequest.getCategoryId()));
        spu.setUnit(productService.readUnit(saveRequest.getUnitId()));

        boolean ok = productService.specificationRelocate(spu, saveRequest.getRelocateCommand());
        return Result.of(ok);
    }

    @ApiOperation("快速调整仓位，从一个规格转移到另一个规格")
    @PostMapping("/transfer")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Result<Boolean> transfer(@Valid @RequestBody TransferCommand request) {
        productService.transfer(request.getSpuId(), request.getFromSku(), request.getToSku(), request.getAmount(), request.getReason());
        return Result.of(true);
    }

    @ApiOperation("获取一个商品数据")
    @GetMapping("/getSpu")
    public Result<StandardProductUnit> getSpu(@Valid @RequestParam @NotEmpty String id) {
        return Result.of(productService.readSpu(id));
    }

    @ApiOperation("快速入库操作，仅针对单件商品")
    @PostMapping("/quickStockIn")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Result<Boolean> quickStockIn(@Valid @RequestBody SingleStorageChangeCommand request) {
        productService.stockIn(request);
        return Result.of(true);
    }

    @ApiOperation("批量入库操作，可一次性提交多件商品")
    @PostMapping("/batchStockIn")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Result<Boolean> batchStockIn(@Valid @RequestBody BatchStockInFormRequest request) {
        final String reason = request.getReason();
        List<SingleStorageChangeCommand> commands = request.getItems().stream().map(v -> {
            SingleStorageChangeCommand command = new SingleStorageChangeCommand();
            String skuId = Try.of(() -> JSON.OM.readValue(v.getSkuInfo(), Map.class).get("id") + "").getOrElse("");
            Preconditions.checkArgument(StringUtils.hasLength(skuId), "未提供正确的sku");
            command.setAmount(v.getAmount());
            command.setPrice(v.getPrice());
            command.setSkuId(skuId);
            command.setReason(reason);
            return command;
        }).collect(Collectors.toList());
        productService.batchStockIn(commands);
        return Result.of(true);
    }

    @ApiOperation("获取商品列表-主界面使用")
    @GetMapping("/getProductList")
    public Result<TwoDimensioningResultWithPage> getProductList(Integer pageSize,
                                                                Integer current,
                                                                String category,
                                                                String brand,
                                                                String filter) throws Exception {
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.hasLength(category)) {
            params.put("categoryId", category);
        }
        if (StringUtils.hasLength(brand)) {
            params.put("brandId", brand);
        }
        if (StringUtils.hasLength(filter)) {
            Map<String, List> filters = JSON.OM.readValue(filter, Map.class);
            for (String key : filters.keySet()) {
                List values = filters.get(key);
                if (!CollectionUtils.isEmpty(values)) {
                    params.put(key, values);
                }
            }
        }
        params.put("pageSize", pageSize);
        params.put("current", current);
        TwoDimensioningResultWithPage result = smartJDBC.queryWithPage("product/getProductList.sql", params);
        return Result.of(result);
    }

    @ApiOperation("获取商品库存变更历史")
    @GetMapping("/getStockHistoryList")
    public Result<TwoDimensioningResultWithPage> getStockHistoryList(Integer pageSize,
                                                                     Integer current,
                                                                     String spuId,
                                                                     String filter) throws Exception {
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.hasLength(spuId)) {
            params.put("spuId", spuId);
        }
        if (StringUtils.hasLength(filter)) {
            Map<String, List> filters = JSON.OM.readValue(filter, Map.class);
            for (String key : filters.keySet()) {
                List values = filters.get(key);
                if (!CollectionUtils.isEmpty(values)) {
                    params.put(key, values);
                }
            }
        }
        params.put("pageSize", pageSize);
        params.put("current", current);
        TwoDimensioningResultWithPage result = smartJDBC.queryWithPage("product/getStockHistoryList.sql", params);
        return Result.of(result);
    }

    @ApiOperation("获取商品列表(sku纬度)")
    @GetMapping("/getSkuListModal")
    public Result<TwoDimensioningResultWithPage> getSkuListModal(Integer pageSize,
                                                                 Integer current,
                                                                 String category,
                                                                 String brand) throws Exception {
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.hasLength(category)) {
            params.put("categoryId", category);
        }
        if (StringUtils.hasLength(brand)) {
            params.put("brandId", brand);
        }
        params.put("pageSize", pageSize);
        params.put("current", current);
        TwoDimensioningResultWithPage result = smartJDBC.queryWithPage("product/getSkuListModal.sql", params);
        return Result.of(result);
    }

    @ApiOperation("获取商品列表(spu纬度)")
    @GetMapping("/getSpuListModal")
    public Result<TwoDimensioningResultWithPage> getSpuListModal(Integer pageSize,
                                                                 Integer current,
                                                                 String category,
                                                                 String brand) throws Exception {
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.hasLength(category)) {
            params.put("categoryId", category);
        }
        if (StringUtils.hasLength(brand)) {
            params.put("brandId", brand);
        }
        params.put("pageSize", pageSize);
        params.put("current", current);
        TwoDimensioningResultWithPage result = smartJDBC.queryWithPage("product/getSpuListModal.sql", params);
        return Result.of(result);
    }

    @ApiOperation("商品分类和占比表")
    @GetMapping("/chartCategory")
    public Result<TwoDimensioningResult> chartCategory() throws Exception {
        TwoDimensioningResult result = smartJDBC.query("product/chartCategory.sql", new HashMap<>());
        return Result.of(result);
    }

    @ApiOperation("改变商品的状态(spu纬度)")
    @PostMapping("/changeProductState")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Result<Void> changeProductState(@Valid @RequestBody ChangeProductStateRequest request) {
        productService.changeProductState(request.getSpuId(), request.getState());
        return Result.of(null);
    }

    @GetMapping("/chartPriceHistory")
    public Result<TwoDimensioningResultWithPage> chartPriceHistory(String spuId) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("spuId", spuId);
        TwoDimensioningResultWithPage result = smartJDBC.queryWithPage("product/chartPriceHistory.sql", params);
        return Result.of(result);
    }

    @GetMapping("/getWarnStorageList")
    public Result<TwoDimensioningResultWithPage> warnStorageList(Integer pageSize, Integer current) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("pageSize", pageSize);
        params.put("current", current);
        TwoDimensioningResultWithPage result = smartJDBC.queryWithPage("product/getWarnStorageList.sql", params);
        return Result.of(result);
    }
}
