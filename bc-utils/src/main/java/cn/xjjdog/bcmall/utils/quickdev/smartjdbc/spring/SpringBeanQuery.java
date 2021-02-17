package cn.xjjdog.bcmall.utils.quickdev.smartjdbc.spring;

import cn.xjjdog.bcmall.utils.quickdev.smartjdbc.ExecutorType;
import cn.xjjdog.bcmall.utils.quickdev.smartjdbc.Query;
import lombok.Data;

/**
 * @author xjjdog
 * 通过Spring的某个Bean，调用相应的方法进行计算
 */
@Data
public class SpringBeanQuery implements Query {
    public static enum InjectType {
        BYNAME("byName"),
        BYTYPE("byType");

        private String key;

        InjectType(String key) {
            this.key = key;
        }

    }

    /**
     * 要调用bean全路径或者bean的名称
     */
    private String name;
    /**
     * 要调用的方法，不允许重载
     */
    private String methodName;
    /**
     * 获取bean的方式，byName或者byType
     */
    private InjectType injectType;

    @Override
    public ExecutorType type() {
        return ExecutorType.SPRING_BEAN;
    }
}
