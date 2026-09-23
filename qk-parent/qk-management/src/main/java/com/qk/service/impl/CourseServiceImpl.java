package com.qk.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qk.domain.Course;
import com.qk.domain.PageResult;
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

    /**
     * 条件分页查询课程
     * @param subject   课程学科
     * @param name   课程名称
     * @param target   适应人群
     * @param page     页码
     * @param pageSize 每页记录数
     * @return 分页查询结果
     */
    @Override
    public PageResult<Course> page(Integer subject, String name, Integer target, Integer page, Integer pageSize) {
        //1 设置分页参数
        PageHelper.startPage(page, pageSize);
        //2 调用mapper层方法，分页查询课程
        Page<Course> p = (Page<Course>)courseMapper.list(subject,name,target);
        //3 封装PageResult对象并返回
        return new PageResult<>(p.getTotal(), p.getResult());
    }
}
