package cn.xjjdog.bcmall.config;

import cn.xjjdog.bcmall.utils.utils.IdUtil;
import cn.xjjdog.bcmall.utils.web.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (c) 2021. All Rights Reserved.
 * <p>
 * 发生异常时的信息返回
 *
 * @author xjjdog
 */

@ControllerAdvice
@ResponseBody
@Slf4j
public class ExceptionResultHandler {

    /**
     * 这个异常，一般发生在通过hibernate validator 验证失败后的返回
     *
     * @param ex 异常信息，包含详细错误信息
     * @return 返回给前端的验证结果
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        String traceCode = IdUtil.genSpecCode();
        Result<String> result = Result.of(traceCode);
        result.setCode(400);
        result.setStatus(400);
        result.setMsg(String.valueOf(errors));
        result.setCause(result.getMsg());

        log.warn("{}|validator error: {}", traceCode, errors);
        return result;
    }

    /**
     * 无权限返回
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    public Result<String> accessDenied(AccessDeniedException ex) {
        String traceCode = IdUtil.genSpecCode();
        Result<String> result = Result.of(traceCode);
        result.setMsg("你没有权限访问此接口");
        result.setCode(4403);
        result.setStatus(500);
        result.setCause(ex.getMessage());
        return result;
    }

    /**
     * 默认异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result<String> commonException(Exception ex) {
        String traceCode = IdUtil.genSpecCode();
        Result<String> result = Result.of(traceCode);
        result.setMsg(ex.getMessage());
        result.setCode(500);
        result.setStatus(500);
        result.setCause(ex.toString());
        log.error(traceCode, ex);
        return result;
    }


}

