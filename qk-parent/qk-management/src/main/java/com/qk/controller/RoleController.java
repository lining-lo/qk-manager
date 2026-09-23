package com.qk.controller;

import com.qk.domain.PageResult;
import com.qk.domain.Result;
import com.qk.entity.Role;
import com.qk.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * 查询所有正常状态的角色信息
     */
    @GetMapping("/list")
    public Result list(){
        //1 调用service层方法，查询所有正常状态的角色信息
        List<Role> roleList = roleService.list();
        //2 响应Result
        return Result.success(roleList);
    }

    /**
     * 条件分页查询角色
     * @param name     角色名称
     * @param page     页码
     * @param pageSize 每页记录数
     * @return 分页查询结果
     */
    @GetMapping
    public Result page(String name,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize) {
        //1 接收请求参数 @RequestParam(defaultValue = "1")指定请求参数名称或者设置默认值
        log.info("条件分页查询角色，参数：name={},page={},pageSize={}", name, page, pageSize);
        //2 调用service，得到分页结果PageResult
        PageResult<Role> pageResult = roleService.page(name,page,pageSize);
        //3 响应Result
        return Result.success(pageResult);
    }
}
