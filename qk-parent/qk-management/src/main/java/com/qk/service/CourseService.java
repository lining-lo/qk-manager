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

    /**
     * 新增课程
     * @param course 封装课程信息
     */
    void add(Course course);

    /**
     * 修改课程
     * @param course 课程信息
     */
    void update(Course course);

    /**
     * 根据ID查询课程
     * @param id 课程ID
     * @return 查询结果
     */
    Course getById(Integer id);

    /**
     * 删除课程
     * @param id 课程ID
     */
    void delete(Integer id);

    /**
     * 根据学科查询课程
     * @param subject 课程学科
     */
    List<Course> getBySubject(Integer subject);
}
