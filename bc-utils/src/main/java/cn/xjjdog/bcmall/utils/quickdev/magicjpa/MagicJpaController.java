package cn.xjjdog.bcmall.utils.quickdev.magicjpa;

import cn.xjjdog.bcmall.utils.db.AbstractEntity;
import cn.xjjdog.bcmall.utils.web.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 * 这是一个工具类，可直接穿透到数据库进行相关操作。
 * 此工具类仅仅用于原型开发测试用，请不要在生产环境使用。
 */
@RestController
@RequestMapping("/magicjpa")
@Slf4j
public class MagicJpaController {
    final static Map<String, Class<? extends AbstractEntity>> REF = Maps.newHashMap();

    public static void register(String key, Class<? extends AbstractEntity> e) {
        REF.put(key, e);
    }

    @Autowired
    MagicJpaService ad;

    final ObjectMapper mapper = new ObjectMapper();


    private void chkCls(Class cls, String E) {
        Preconditions.checkNotNull(cls, E + " can not be find in class register");
    }

    @GetMapping("{E}/read")
    public Result<? extends AbstractEntity> read(@PathVariable String E, @RequestParam Long id) {
        Class cls = REF.get(E);
        chkCls(cls, E);
        return Result.of(ad.read(cls, id));
    }

    @PostMapping("{E}/delete")
    public Result<? extends AbstractEntity> delete(@PathVariable String E, @RequestParam Long id) {
        Class cls = REF.get(E);
        chkCls(cls, E);
        return Result.of(ad.delete(cls, id));
    }

    @PostMapping("{E}/save")
    public Result<? extends AbstractEntity> save(@PathVariable String E, @RequestBody String json) {
        Preconditions.checkNotNull(json, "saved json is null");
        Class cls = REF.get(E);
        chkCls(cls, E);

        try {
            AbstractEntity e = (AbstractEntity) mapper.readValue(json, cls);
            return Result.of(ad.save(cls, e));
        } catch (Exception ex) {
            return Result.of((AbstractEntity) null).status(500).code(500).cause(ex.getMessage());
        }
    }

    @GetMapping("{E}/findAll")
    public Result<List<? extends AbstractEntity>> findAll(@PathVariable String E) {
        Class cls = REF.get(E);
        chkCls(cls, E);
        return Result.of(ad.findAll(cls));
    }

    @GetMapping("{E}/findAllPage")
    public Result<Page<? extends AbstractEntity>> findAll(@PathVariable String E, Integer pageSize, Integer current, String sort) {
        Class cls = REF.get(E);
        chkCls(cls, E);

        Map<String, String> sortMap = Maps.newHashMap();
        if (!Objects.isNull(sort)) {
            try {
                sortMap = mapper.readValue(sort, Map.class);
            } catch (Exception ex) {
                log.warn("error while parse sorted data: " + sort + " | cause " + ex.getMessage());
            }
        }
        return Result.of(ad.findAll(cls, pageSize, current, sortMap));
    }
}
