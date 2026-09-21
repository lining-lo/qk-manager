package com.qk.service.impl;

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
}
