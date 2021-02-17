package cn.xjjdog.bcmall.utils.quickdev.smartjdbc;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */
public class SmartSQLLoader {
    private static final String SQL_DIR = "complexSQL";
    private static LoadingCache<String, String> ld = CacheBuilder
            .newBuilder()
            .weakKeys()
            .weakValues()
            .maximumSize(100)
            .build(new CacheLoader<String, String>() {
        @Override
        public String load(String sql) throws Exception {
            Resource resource = new ClassPathResource(SQL_DIR + "/" + sql);
            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    sb.append("\n");
                }
            }
            return sb.toString();
        }
    });

    public static String loadSQL(String sql) {
        try {
            return ld.get(sql);
        } catch (Exception ex) {
            return "";
        }

    }

    public static void main(String[] args) throws IOException {
        System.out.println(loadSQL("base/getProductList.sql"));
    }
}

