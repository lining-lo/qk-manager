package com.qk.service;

import com.qk.domain.Course;
import com.qk.domain.PageResult;

import java.util.List;

public interface CourseService {
    /**
     * 查询所有课程信息
     */
    List<Course> list();

    /**
     * 条件分页查询课程
     * @param subject   课程学科
     * @param name   课程名称
     * @param target   适应人群
     * @param page     页码
     * @param pageSize 每页记录数
     * @return 分页查询结果
     */
    PageResult<Course> page(Integer subject, String name, Integer target, Integer page, Integer pageSize);

}
