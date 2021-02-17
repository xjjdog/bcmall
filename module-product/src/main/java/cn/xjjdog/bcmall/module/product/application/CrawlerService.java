package cn.xjjdog.bcmall.module.product.application;

import cn.xjjdog.bcmall.module.product.domain.product.StandardProductUnit;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;

/**
 * Copyright (c) 2021. All Rights Reserved.
 * 爬虫/爬取淘宝、天猫、京东数据
 * <p>
 * TODO
 *
 * @author xjjdog
 * @date 2021/2/16
 */
public class CrawlerService {
    public static void main(String[] args) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

        String imageHost = "http://img12.360buyimg.com/n1/s450x450_";
        String url = "https://item.jd.com/100008348542.html";
        Connection conn = Jsoup.connect(url);
        conn.header(
                "User-Agent",
                "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2 Googlebot/2.1");
        Document doc = conn.timeout(3 * 1000).get();
        Elements scripts = doc.getElementsByTag("script");
        String info = scripts.get(0).data();

        String[] splits = info.split("\n");

        String colorSize = findByTag(splits, "colorSize:");
        String name = findByTag(splits, "name:");
        String src = findByTag(splits, "src:");
        String imageList = findByTag(splits, "imageList:");

        System.out.println(name);
        System.out.println(objectMapper.readValue(colorSize, List.class));
        System.out.println(src);
        System.out.println(objectMapper.readValue(imageList, List.class));

        StandardProductUnit spu = new StandardProductUnit();

    }

    static String findByTag(String[] lines, String tag) {
        for (String line : lines) {
            if (line.contains(tag)) {
                if (line.endsWith(",")) {
                    line = line.substring(0, line.lastIndexOf(","));
                }
                return line
                        .replace(tag, "")
                        ;
            }
        }
        return "";
    }
}
