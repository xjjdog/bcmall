package cn.xjjdog.bcmall.utils.quickdev.smartjdbc.sql;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xjjdog
 */
@Component
public class RegexModeParserSQL implements ParserSQL {
    private static final Pattern PARAM_SEGMENT_REG =
            Pattern.compile("#\\{(.*?:([a-zA-Z0-9_]+).*?)\\}", Pattern.CASE_INSENSITIVE);


    @Override
    public Map<String, String> transform(String sql, Map<String, Object> params) {
        Map<String, String> transformMap = new HashMap<>(10);

        Matcher matcher = PARAM_SEGMENT_REG.matcher(sql);
        while (matcher.find()) {
            String group0 = matcher.group(0);
            String group1 = matcher.group(1);
            String group2 = matcher.group(2);

            if (params.containsKey(group2)) {
                transformMap.putIfAbsent(group0, " " + group1 + " ");
            } else {
                transformMap.putIfAbsent(group0, " ");
            }
        }
        return transformMap;
    }
}
