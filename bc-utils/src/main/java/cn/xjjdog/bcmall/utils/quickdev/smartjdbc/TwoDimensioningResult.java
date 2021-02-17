package cn.xjjdog.bcmall.utils.quickdev.smartjdbc;

import java.util.List;
import java.util.Map;

/**
 * @author xjjdog
 */
public class TwoDimensioningResult extends ExecutorResult<List<Map<String, Object>>> {
    public TwoDimensioningResult(List<Map<String, Object>> result) {
        this.setObject(result);
    }
}
