package com.qk.aop;

import com.qk.entity.OperateLog;
import com.qk.mapper.OperateLogMapper;
import com.qk.utils.CurrentUserHoler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
public class LogAspect {

    @Autowired
    private OperateLogMapper operateLogMapper;

    @Around("@annotation(com.qk.anno.Log)")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        //1 获取日志信息
        //1.1 private Integer operateUserId; //操作用户ID
        Integer operateUserId = CurrentUserHoler.getCurrentUser();
        //1.2 private LocalDateTime operateTime; //操作时间
        LocalDateTime operateTime = LocalDateTime.now();
        //1.3 private String className; //类名称
        String className = joinPoint.getTarget().getClass().getName();
        //1.4 private String methodName; //方法名称
        String methodName = joinPoint.getSignature().getName();
        //1.5 private String methodParams; //方法参数
        String methodParams = Arrays.toString(joinPoint.getArgs());
        long start = System.currentTimeMillis();
        //1.6 private String returnValue; //返回值
        Object result = joinPoint.proceed();
        String returnValue = result.toString();
        //1.7 private Long costTime; //耗时
        Long costTime = System.currentTimeMillis() - start;

        //2 将日志信息封装成OperateLog对象
        OperateLog log = new OperateLog(null, operateUserId, operateTime, className, methodName, methodParams, returnValue, costTime);
        //3 调用mapper层方法保存日志
        operateLogMapper.insert(log);
        return result;
    }
}