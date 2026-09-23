package com.qk.controller;
import com.qk.domain.Course;
import com.qk.domain.PageResult;
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

    /**
     * 条件分页查询课程
     * @param subject   课程学科
     * @param name   课程名称
     * @param target   适应人群
     * @param page     页码
     * @param pageSize 每页记录数
     * @return 分页查询结果
     */
    @GetMapping
    public Result page(Integer subject,String name, Integer target,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize) {
        //1 接收请求参数 @RequestParam(defaultValue = "1")指定请求参数名称或者设置默认值
        log.info("条件分页查询课程，参数：subject={},name={},target={},page={},pageSize={}", subject, name, target, page, pageSize);
        //2 调用service，得到分页结果PageResult
        PageResult<Course> pageResult = courseService.page(subject, name, target, page, pageSize);
        //3 响应Result
        return Result.success(pageResult);
    }

    /**
     * 新增课程
     * @param course 封装课程信息
     */
    @PostMapping
    public Result add(@RequestBody Course course) {
        //1 接收请求参数--->(@RequestBody Course course)
        log.info("新增课程：{}", course);
        //2 调用service层方法，新增课程
        courseService.add(course);
        //3 响应Result
        return Result.success();
    }

    /**
     * 修改课程
     * @param course 课程信息
     */
    @PutMapping
    public Result update(@RequestBody Course course) {
        //1 接收请求参数--->(@RequestBody Course course)
        log.info("修改课程：{}", course);
        //2 调用service层方法，修改课程
        courseService.update(course);
        //3 响应Result
        return Result.success();
    }

    /**
     * 根据ID查询课程
     * @param id 课程ID
     * @return 查询结果
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        //1 获取请求参数--->(@PathVariable Integer id)
        log.info("根据ID查询课程，参数：{}", id);
        //2 调用service层方法，根据ID查询课程
        Course course = courseService.getById(id);
        //3 响应Result
        return Result.success(course);
    }

}
