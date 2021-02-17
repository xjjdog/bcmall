package cn.xjjdog.bcmall.utils.quickdev.smartjdbc;

import lombok.Data;

/**
 * @author xjjdog
 * 上下文缓冲数据
 */
@Data
public class ContextPair {
    ExecutorResult result;
    Query query;
}
