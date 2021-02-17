package cn.xjjdog.bcmall;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 * @Description TODO
 */
public class ConfigException extends RuntimeException {
    public ConfigException(String msg) {
        super(msg);
    }
    public ConfigException(String message, Throwable cause) {
        super(message, cause);
    }
}
