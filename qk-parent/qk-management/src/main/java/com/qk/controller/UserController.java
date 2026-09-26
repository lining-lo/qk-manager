package com.qk.controller;

import com.qk.domain.PageResult;
import com.qk.domain.Result;
import com.qk.dto.UserQueryDto;
import com.qk.entity.User;
import com.qk.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     批量删除
     * @param ids 保存批量删除的ID们。例如：/users/2,3,4
     */
    @DeleteMapping("/{ids}")
    //public Result delete(@PathVariable Integer[] ids){
    public Result delete(@PathVariable List<Integer> ids){
        //1 接收请求参数-->(@PathVariable List<Integer> ids)
        log.info("批量删除用户，ids：{}", ids);
        //2 调用service，批量删除
        userService.delete(ids);
        //3 响应Result
        return Result.success();
    }

    /**
     * 根据ID查询用户信息
     * @param id 用户ID
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable("id") Integer id) {
        //1 获取请求参数--->(@PathVariable("id") Integer id)
        log.info("根据ID查询用户: {}", id);
        //2 调用service层方法，根据ID查询用户
        User user = userService.getById(id);
        //3 响应Result
        return Result.success(user);
    }

    /**
     * 修改用户信息
     * @param user 用户信息
     */
    @PutMapping
    public Result update(@RequestBody User user) {
        //1 获取请求参数--->(@RequestBody User user)
        log.info("修改用户: {}", user);
        //2 调用service层方法，修改用户
        userService.update(user);
        //3 响应Result
        return Result.success();
    }

    /**
     * 根据角色查询用户列表
     * @param roleLabel 角色标签
     */
    @GetMapping("/role/{roleLabel}")
    public Result getByRole(@PathVariable String roleLabel){
        //1 获取请求参数--->(@PathVariable String roleLabel)
        log.info("根据角色查询用户列表: {}", roleLabel);
        //2 调用service层方法，根据角色查询用户列表
        List<User> userList = userService.getByRole(roleLabel);
        //3 响应Result
        return Result.success(userList);
    }
}