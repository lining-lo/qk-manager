package com.qk.controller;
import com.qk.domain.Course;
import com.qk.domain.Result;
import com.qk.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    /**
     * 查询所有课程信息
     */
    @GetMapping("/list")
    public Result list(){
        //1 调用service层方法，查询所有课程信息
        List<Course> courseList = courseService.list();
        //2 响应Result
        return Result.success(courseList);
    }

}
