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
import java.util.List;

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

    /**
     * 根据ID查询部门
     * @param id 部门ID
     */
    @Override
    public Dept getById(Integer id) {
        //直接调用mapper层方法，根据id查询部门
        return deptMapper.getById(id);
    }

    /**
     * 修改部门
     * @param dept 部门信息
     */
    @Override
    public void update(Dept dept) {
        //1 设置基础属性(更新时间)
        dept.setUpdateTime(LocalDateTime.now());
        //2 调用mapper层方法，修改部门
        deptMapper.updateById(dept);
    }

    /**
     * 删除部门
     * @param id 部门ID
     */
    @Override
    public void delete(Integer id) {
        //调用mapper层方法，根据id删除部门
        deptMapper.deleteById(id);
    }

    /**
     * 查询所有正常状态的部门信息
     */
    @Override
    public List<Dept> list() {
        //调用mapper层之前写好的list方法
        return deptMapper.list(null, 1);
    }
}
