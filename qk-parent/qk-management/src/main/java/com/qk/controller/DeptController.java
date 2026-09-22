package com.qk.controller;

import com.qk.domain.PageResult;
import com.qk.domain.Result;
import com.qk.entity.Dept;
import com.qk.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/depts") //给该类中所有的方法定义一个公共访问路径，访问方法时都需要带上这个路径
public class DeptController {
    @Autowired
    private DeptService deptService;

    /**
     根据id查询部门
     * @param id 要查询的部门id
     * @PathVariable：获取路径参数(/{id})。当{}中占位符的名称和形参变量名不一样时，需要在@PathVariable中指定占位符的名称。例如：(@PathVariable("id") Integer deptId)。如果一样就不用写占位符的名称。例如：(@PathVariable Integer id)
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        //1 获取请求参数--->(@PathVariable Integer id)
        log.info("根据ID查询部门，参数：{}", id);
        //2 调用service层方法，根据ID查询部门
        Dept dept = deptService.getById(id);
        //3 响应Result
        return Result.success(dept);
    }

    /**
     新增部门
     * @param dept 封装要新增部门信息(只有name和status)
     * @RequestBody :将请求体中的json数据转换(封装)成Java对象，要求json的key要和对象的属性名一样。适用于POST、PUT请求
     */
    @PostMapping
    public Result add(@RequestBody Dept dept){
        //1 接收请求参数-->(@RequestBody Dept dept)
        log.info("新增部门，dept={}",dept);
        //2 调用service，新增部门
        deptService.add(dept);
        //3 响应Result结果
        return Result.success();
    }

    /**
     带条件分分页查询
     * @param name 部门名称
     * @param status 部门状态
     * @param page 当前页码
     * @param pageSize 每页条数
     * @return 分页结果
    获取查询参数：url?name=value&name=value格式的参数：保证请求参数名称和方法形参变量名一样，springboot就能自动接收请求参数，如果不一样，就需要使用@RequestParam注解指定请求参数的名称。
     * @RequestParam :
     *  1、当请求参数名称和形参变量名不一致时，可以使用@RequestParam指定请求参数的名称赋值给形参变量
     *  2、可以设置前端必须传递某个请求参数，使用@RequestParam的required属性设置为true（默认值），如果不是必须就设置为false。
     *  3、可以给形参设置默认值,使用@RequestParam的defaultValue属性
     */
    @GetMapping
    public Result page(String name, Integer status,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize){
        //1 接收请求参数-->(String name,Integer status,Integer page, Integer pageSize)
        log.info("分页查询部门，参数：name={},status={},page={},pageSize={}",name,status,page,pageSize);
        //2 调用service分页查询，获取分页结果PageResult
        PageResult<Dept> pageResult = deptService.page(name, status, page, pageSize);
        //3 响应Result结果
        return Result.success(pageResult);
    }

    /**
     * 修改部门
     * @param dept 部门信息
     */
    @PutMapping
    public Result update(@RequestBody Dept dept) {
        //1 接收请求参数--->(@RequestBody Dept dept)
        log.info("修改部门：{}", dept);
        //2 调用service层方法，修改部门
        deptService.update(dept);
        //3 响应Result
        return Result.success();
    }

    /**
     * 删除部门
     * @param id 部门ID
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        //1 接收请求参数--->(@PathVariable Dept dept)
        log.info("删除部门，id：{}", id);
        //2 调用service层方法，删除部门
        deptService.delete(id);
        //3 响应Result
        return Result.success();
    }
}
