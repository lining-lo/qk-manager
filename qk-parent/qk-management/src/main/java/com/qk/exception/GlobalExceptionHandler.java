package com.qk.exception;

import com.qk.domain.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice  //表示该类是一个处理异常的类，类中可以定义若干个方法处理不同类型的异常，处理完之后可以返回json结果
public class GlobalExceptionHandler {

    @ExceptionHandler  //表示该方法是一个处理异常的方法，默认处理方法形参中定义的异常及其子类异常。
    public Result doException(Exception ex){
        //1 将来可以将异常信息保存到文件、数据库中--->记录日志。此处暂时打印即可
        //log.error("异常信息：{}",ex.getMessage());
        ex.printStackTrace();
        return Result.error("服务正在升级，请稍后重试！");
    }

    @ExceptionHandler  //表示该方法是一个处理异常的方法，默认处理方法形参中定义的异常及其子类异常。
    //@ExceptionHandler(SQLException.class)//如果指定了要处理的异常类型，就按照注解中指定的异常及其子类异常
    public Result doDuplicateKeyException(DuplicateKeyException ex){
        //1 将来可以将异常信息保存到文件、数据库中--->记录日志。此处暂时打印即可
        //log.error("异常信息：{}",ex.getMessage());
        ex.printStackTrace();
        //2 获取异常原因中的重复的名称，例如：Duplicate entry '市场二部' for key 'dept.name'
        String[] split = ex.getCause().getMessage().split(" "); //["Duplicate","entry","市场二部",...]
        return Result.error(split[2]+"重复了");
    }

    //处理业务异常 BusinessException
    @ExceptionHandler
    public Result doBusinessException(BusinessException ex){
        ex.printStackTrace();
        return Result.error(ex.getMessage());
    }
}