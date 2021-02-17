package cn.xjjdog.bcmall.utils.quickdev.smartjdbc;

import cn.xjjdog.bcmall.utils.quickdev.smartjdbc.sql.Paginate;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * @author xjjdog
 */
@Getter
@Setter
public class TwoDimensioningResultWithPage extends TwoDimensioningResult {
    public TwoDimensioningResultWithPage(List<Map<String, Object>> result) {
        super(result);
    }
    private Paginate paginate;
}
