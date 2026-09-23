package com.qk.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qk.domain.PageResult;
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

    /**
     * 条件分页查询角色
     * @param name     角色名称
     * @param page     页码
     * @param pageSize 每页记录数
     * @return 分页查询结果
     */
    @Override
    public PageResult<Role> page(String name, Integer page, Integer pageSize) {
        //1 设置分页参数
        PageHelper.startPage(page, pageSize);
        //2 调用mapper层方法，分页查询角色
        Page<Role> p = (Page<Role>)roleMapper.list(name);
        //3 封装PageResult对象并返回
        return new PageResult<>(p.getTotal(), p.getResult());
    }

    /**
     * 新增角色
     * @param role 封装角色信息(包括角色名称、角色标识、备注)
     */
    @Override
    public void add(Role role) {
        //1 设置基础属性
        role.setCreateTime(LocalDateTime.now());
        role.setUpdateTime(LocalDateTime.now());
        //2 调用mapper层方法，新增角色
        roleMapper.insert(role);
    }

    /**
     * 根据ID查询角色
     * @param id 角色ID
     */
    @Override
    public Role getById(Integer id) {
        //直接调用mapper层方法，根据id查询角色
        return roleMapper.getById(id);
    }
}