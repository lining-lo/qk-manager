package com.qk.interceptor;

import com.qk.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1 不是登录的请求，就需要获取请求头中的令牌（token）
        String jwt = request.getHeader("token");
        //2 判断令牌是否存在，如果不存在，响应401状态码。
        if(!StringUtils.hasText(jwt)){
            log.info("TokenInterceptor中没有获取到jwt令牌，响应401");
            //HttpServletResponse.SC_UNAUTHORIZED是常量，表示401状态码
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;  //阻止代码继续向下执行
        }
        try {
            //3 解析token，如果解析成功，就放行。
            JwtUtils.parseToken(jwt); //快捷键：ctlr+alt+t--->try...catch
            return true; //放行
        } catch (Exception e) {
            //4 如果解析失败，响应401
            log.info("TokenInterceptor中解析令牌失败，响应401");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false; //阻止执行
        }
    }
}