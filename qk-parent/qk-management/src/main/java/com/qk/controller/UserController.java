package com.qk.controller;

import com.qk.domain.PageResult;
import com.qk.domain.Result;
import com.qk.dto.UserQueryDto;
import com.qk.entity.User;
import com.qk.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理控制器
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     带条件分页查询
     * @param queryDto 封装查询条件、当前页码、每页条数
     * @return 分页结果
     * 注意：形参前面不能使用@RequestBody注解，因为该注解是接收请求体中的json数据，当前请求参数不在请求体中，也不是json数据，所以不能用。
     */
    @GetMapping
    public Result page(UserQueryDto queryDto){
        //1 接收请求参数-->(UserQueryDto queryDto)
        log.info("分页查询参数：{}" ,queryDto);
        //2 调用service层方法分页查询，得到PageResult分页结果
        PageResult<User> pageResult = userService.page(queryDto);
        //3 响应Result对象
        return Result.success(pageResult);
    }

    /**
     新增用户
     * @param user 封装用户信息(不包含password、createTime、updateTime)
     */
    @PostMapping
    public Result add(@RequestBody User user){
        //1 接收请求参数-->(@RequestBody User user)
        log.info("新增用户：{}", user);
        //2 调用service，新增用户
        userService.add(user);
        //3 响应Result
        return Result.success();
    }
}