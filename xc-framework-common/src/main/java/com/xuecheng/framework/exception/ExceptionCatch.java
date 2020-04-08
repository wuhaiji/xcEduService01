package com.xuecheng.framework.exception;

import com.google.common.collect.ImmutableMap;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author whj
 * @createTime 2020-02-09 17:38
 * @description 统一异常捕获类
 **/
@ControllerAdvice
@Slf4j
public class ExceptionCatch {

    private static ImmutableMap<Class<? extends Throwable>,ResultCode> exceptionMap;

    protected static ImmutableMap.Builder<Class<? extends Throwable>,ResultCode> builder = ImmutableMap.builder();

    static{
        builder.put(HttpMessageNotReadableException.class,CommonCode.INVALID_PARAM);
    }

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseResult customException(CustomException e){
        log.error("catch exception:{}",e.getMessage());
        e.printStackTrace();
        ResultCode resultCode = e.getResultCode();
        return new ResponseResult(resultCode);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult exception(Exception e) {
        log.error("catch exception:{}", e.getMessage());
        e.printStackTrace();
        if(exceptionMap==null){
            exceptionMap = builder.build();

        }
        //判断异常是否有对应resultCode
        ResultCode resultCode = exceptionMap.get(e.getClass());
        if(resultCode!=null){
            return new ResponseResult(resultCode);
        }
        return new ResponseResult(CommonCode.SERVER_ERROR);
    }
}
