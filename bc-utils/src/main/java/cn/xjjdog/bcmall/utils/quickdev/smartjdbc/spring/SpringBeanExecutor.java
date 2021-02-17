package cn.xjjdog.bcmall.utils.quickdev.smartjdbc.spring;

import cn.xjjdog.bcmall.utils.quickdev.smartjdbc.*;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author xjjdog
 */
@Component
public class SpringBeanExecutor implements QueryExecutor, ApplicationContextAware {

    @Override
    public ExecutorResult chain(Query query, ExecutorContext ctx) throws InvokeException {
        SpringBeanQuery q = SpringBeanQuery.class.cast(query);
        String name = q.getName();
        Object bean;
        Class type;
        if (q.getInjectType() == SpringBeanQuery.InjectType.BYNAME) {
            bean = applicationContext.getBean(name);
            type = bean.getClass();
        } else {
            try {
                type = Class.forName(name);
                bean = applicationContext.getBean(type);
            } catch (Exception ex) {
                throw new InvokeException("no class found :" + name + " " + ex.getMessage(), ex);
            }
        }
        Method method = ReflectionUtils.findMethod(type, q.getMethodName(), Map.class);

        Object invokeResult;
        try {
            invokeResult = method.invoke(bean, ctx.getParams());
        } catch (Exception e) {
            throw new InvokeException(e.getMessage(), e);
        }
        if (null != invokeResult && !(invokeResult instanceof ExecutorResult)) {
            throw new InvokeException("method " + name + ":" + method + " should return Type ChainResult");
        }
        return null == invokeResult ? null : ExecutorResult.class.cast(invokeResult);
    }

    @Override
    public ExecutorType type() {
        return ExecutorType.SPRING_BEAN;
    }

    ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
