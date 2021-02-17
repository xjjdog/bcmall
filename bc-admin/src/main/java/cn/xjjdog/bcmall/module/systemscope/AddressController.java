package cn.xjjdog.bcmall.module.systemscope;

import cn.xjjdog.bcmall.utils.utils.JSON;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */

@RestController
@RequestMapping("/api/tools/address")
public class AddressController implements InitializingBean {
    private Map<String, Map<String,Map<String, List<String>>>> map = new HashMap<>();

    @GetMapping("/getProvinces")
    public List<String> getProvince(){
        return new LinkedList<>(map.keySet());
    }

    @GetMapping("/getCitys")
    public List<String> getCitys(String province){
        return new LinkedList<>(map.get(province).keySet());
    }

    @GetMapping("/getAreas")
    public List<String> getAreas(String province,String city){
        return new LinkedList<>(map.get(province).get(city).keySet());
    }

    @GetMapping("/getStreets")
    public List<String> getStreets(String province,String city,String area){
        return map.get(province).get(city).get(area);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Resource resource = new ClassPathResource("common/pcas.json");
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        }
        map = JSON.OM.readValue(sb.toString(), Map.class);
    }
}

