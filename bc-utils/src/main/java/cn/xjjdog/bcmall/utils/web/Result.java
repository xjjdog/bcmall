package cn.xjjdog.bcmall.utils.web;

import lombok.Data;

/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */
@Data
public class Result<T> {
    private int status = 200;
    private int code = 0;
    private String msg;
    private String cause;
    private T data;


    public Result<T> status(int status) {
        this.status = status;
        return this;
    }

    public Result<T> code(int code) {
        this.code = code;
        return this;
    }

    public Result<T> msg(String msg) {
        this.msg = msg;
        return this;
    }

    public Result<T> cause(String cause) {
        this.cause = cause;
        return this;
    }

    public Result<T> data(T data) {
        this.data = data;
        return this;
    }

    public static <T> Result<T> of(T obj) {
        Result<T> result = new Result<>();
        result.setData(obj);
        return result;
    }
}
