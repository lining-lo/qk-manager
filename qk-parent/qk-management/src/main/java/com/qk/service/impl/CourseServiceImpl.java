package com.qk.service.impl;

import com.qk.domain.Course;
import com.qk.mapper.CourseMapper;
import com.qk.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    /**
     * 查询所有课程信息
     */
    @Override
    public List<Course> list() {
        //调用mapper层之前写好的list方法
        return courseMapper.list(null, null,null);
    }
}
