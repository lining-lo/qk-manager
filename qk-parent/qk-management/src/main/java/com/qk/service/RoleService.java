package com.qk.service;

import com.qk.domain.PageResult;
import com.qk.entity.Role;

import java.util.List;

public interface RoleService {

    /**
     * 查询所有角色信息
     */
    List<Role> list();

    /**
     * 条件分页查询角色
     *
     * @param name     角色名称
     * @param page     页码
     * @param pageSize 每页记录数
     * @return 分页查询结果
     */
    PageResult<Role> page(String name, Integer page, Integer pageSize);

    /**
     * 新增角色
     * @param role 封装角色信息(包括角色名称、角色标识、备注)
     */
    void add(Role role);

    /**
     * 根据ID查询角色
     * @param id 角色ID
     * @return 查询结果
     */
    Role getById(Integer id);

    /**
     * 修改角色
     * @param role 角色信息
     */
    void update(Role role);
}
