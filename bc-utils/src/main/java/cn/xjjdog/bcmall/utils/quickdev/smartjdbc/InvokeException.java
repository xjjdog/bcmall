package cn.xjjdog.bcmall.utils.quickdev.smartjdbc;

/**
 * @author xjjdog
 * 执行错误
 */
public class InvokeException extends Exception {
    public InvokeException(String msg) {
        super( msg);
    }

    public InvokeException(String msg, Throwable throwable) {
        super( msg, throwable);
    }
}
