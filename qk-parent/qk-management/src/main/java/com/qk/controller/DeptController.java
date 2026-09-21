package com.qk.controller;

import com.qk.domain.Result;
import com.qk.entity.Dept;
import com.qk.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class DeptController {
    @Autowired
    private DeptService deptService;

    /**
     新增部门
     * @param dept 封装要新增部门信息(只有name和status)
     * @RequestBody :将请求体中的json数据转换(封装)成Java对象，要求json的key要和对象的属性名一样。适用于POST、PUT请求
     */
    @PostMapping("/depts")
    public Result add(@RequestBody Dept dept){
        //1 接收请求参数-->(@RequestBody Dept dept)
        log.info("新增部门，dept={}",dept);
        //2 调用service，新增部门
        deptService.add(dept);
        //3 响应Result结果
        return Result.success();
    }
}
