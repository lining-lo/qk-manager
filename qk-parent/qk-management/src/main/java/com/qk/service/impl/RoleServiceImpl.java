package com.qk.service.impl;

import com.qk.entity.Role;
import com.qk.mapper.RoleMapper;
import com.qk.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    /**
     * 查询所有正常状态的角色信息
     */
    @Override
    public List<Role> list() {
        //调用mapper层之前写好的list方法
        return roleMapper.list(null);
    }
}