package com.qk.service;

import com.qk.entity.Dept;

public interface DeptService {
    /**
     新增部门
     * @param dept 封装要新增部门信息(只有name和status)
     */
    void add(Dept dept);
}
