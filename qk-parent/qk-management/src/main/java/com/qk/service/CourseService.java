package com.qk.service;

import com.qk.domain.Course;

import java.util.List;

public interface CourseService {
    /**
     * 查询所有课程信息
     */
    List<Course> list();
}
