package com.qk.interceptor;

import com.qk.utils.CurrentUserHoler;
import com.qk.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1. 获取请求头中的令牌（token）。
        String jwt = request.getHeader("token");
        //2. 判断令牌是否存在，如果不存在，返回错误结果（未登录）。
        if(!StringUtils.hasText(jwt)){ //jwt为空
            log.info("获取到jwt令牌为空, 返回错误结果");
            response.setStatus(HttpStatus.SC_UNAUTHORIZED);
            return false;
        }
        //3. 解析token，如果解析失败，返回错误结果（未登录）。
        try {
            Claims claims = JwtUtils.parseToken(jwt);
            //4. 校验成功放行。
            log.info("令牌合法, 放行");
            //获取用户id, 存入ThreadLocal
            Integer userId = claims.get("id", Integer.class);
            CurrentUserHoler.setCurrentUser(userId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            //5 校验失败，返回错误结果（未登录）。
            log.info("解析令牌失败, 返回错误结果");
            response.setStatus(HttpStatus.SC_UNAUTHORIZED);
            return false;
        }
    }

    //在controller层方法执行之后无论是否出现异常时都执行
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //从ThreadLocal中移除用户id
        CurrentUserHoler.removeCurrentUser();
    }
}