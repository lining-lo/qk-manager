package com.qk.controller;

import com.qk.domain.Result;
import com.qk.entity.Role;
import com.qk.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
