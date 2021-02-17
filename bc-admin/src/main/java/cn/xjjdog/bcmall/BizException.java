package cn.xjjdog.bcmall;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 * @Description TODO
 */
public class BizException extends RuntimeException {
    public BizException(String msg) {
        super(msg);
    }
    public BizException(String message, Throwable cause) {
        super(message, cause);
    }
}
