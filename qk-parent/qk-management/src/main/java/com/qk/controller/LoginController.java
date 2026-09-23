package com.qk.controller;

import com.qk.domain.Result;
import com.qk.dto.UserLoginDto;
import com.qk.service.UserService;
import com.qk.vo.LoginResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoginController {
    @Autowired
    private UserService userService;
    /**
     用户登录
     * @param loginDto 封装用户名和密码
     */
    @PostMapping("/login")
    public Result login(@RequestBody UserLoginDto loginDto){
        //1 接收请求参数-->(@RequestBody UserLoginDto loginDto
        log.info("用户登录：{}", loginDto);
        //2 调用service，用户登录，获取登录结果LoginResultVo
        LoginResultVo loginResultVo = userService.login(loginDto);
        //3 响应结果
        //if(loginResultVo==null){
        // return Result.error("登录失败！");
        //}
        return Result.success(loginResultVo);
    }
}