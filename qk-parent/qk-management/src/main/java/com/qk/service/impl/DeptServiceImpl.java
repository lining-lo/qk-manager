package com.qk.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qk.domain.PageResult;
import com.qk.entity.Dept;
import com.qk.mapper.DeptMapper;
import com.qk.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptMapper deptMapper;

    /**
     新增部门
     @param dept 封装要新增部门信息(只有name和status)
     */
    @Override
    public void add(Dept dept) {
        //1 补全基础属性(createTime和updateTime)
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        //2 调用mapper新增部门
        deptMapper.insert(dept);
    }

    /**
     * 条件分页查询部门
     * @param name     部门名称
     * @param status   状态
     * @param page     页码
     * @param pageSize 每页记录数
     * @return 分页查询结果
     */
    @Override
    public PageResult<Dept> page(String name, Integer status, Integer page, Integer pageSize) {
        //1 设置分页参数
        PageHelper.startPage(page, pageSize);
        //2 调用mapper层方法，分页查询部门
        Page<Dept> p = (Page<Dept>)deptMapper.list(name, status);
        //3 封装PageResult对象并返回
        return new PageResult<>(p.getTotal(), p.getResult());
    }
}
