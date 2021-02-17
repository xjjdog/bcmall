package cn.xjjdog.bcmall.utils.quickdev.smartjdbc.sql;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xjjdog
 */
@Component
public class RagelModeParserSQL implements ParserSQL {
    @Override
    public Map<String, String> transform(String sql, Map<String, Object> params) {
        Map<String, String> transformMap = new HashMap<>();

       P.debug = false;
        List<Pair> pairs =P.p(sql);
        for (Pair pair : pairs) {
            String namedParams = pair.namedParams;
            String quote = pair.quote;

            String name = namedParams.substring(1);
            String quoteInnner = quote.substring(2, quote.length() - 1);

            if (params.containsKey(name)) {
                transformMap.putIfAbsent(quote, " " + quoteInnner + " ");
            } else {
                transformMap.putIfAbsent(quote, " ");
            }
        }
        return transformMap;
    }
}
