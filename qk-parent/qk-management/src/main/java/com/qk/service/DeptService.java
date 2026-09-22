package com.qk.service;

import com.qk.domain.PageResult;
import com.qk.entity.Dept;

public interface DeptService {
    /**
     新增部门
     * @param dept 封装要新增部门信息(只有name和status)
     */
    void add(Dept dept);

    /**
     * 条件分页查询部门
     *
     * @param name     部门名称
     * @param status   状态
     * @param page     页码
     * @param pageSize 每页记录数
     * @return 分页查询结果
     */
    PageResult<Dept> page(String name, Integer status, Integer page, Integer pageSize);

    /**
     * 根据ID查询部门
     * @param id 部门ID
     */
    Dept getById(Integer id);

    /**
     * 修改部门
     * @param dept 部门信息
     */
    void update(Dept dept);

    /**
     * 删除部门
     * @param id 部门ID
     */
    void delete(Integer id);
}
