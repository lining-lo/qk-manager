package com.qk.controller;

import com.qk.anno.Log;
import com.qk.domain.PageResult;
import com.qk.domain.Result;
import com.qk.dto.BusinessQueryDto;
import com.qk.entity.Business;
import com.qk.service.BusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/businesses")
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    /**
     * 根据条件分页查询商机
     * @param businessQueryDto 封装查询条件和分页参数
     */
    @GetMapping
    public Result page(BusinessQueryDto businessQueryDto) {
        //1 接收请求参数
        log.info("商机查询参数: {}", businessQueryDto);
        //2 调用service层方法，分页查询
        PageResult<Business> pageResult = businessService.pageQuery(businessQueryDto);
        //3 响应Result
        return Result.success(pageResult);
    }

    /**
     * 添加商机
     * @param business 封装商机信息
     */
    @Log
    @PostMapping
    public Result add(@RequestBody Business business) {
        //1 接收请求参数
        log.info("新增商机: {}", business);
        //2 调用service层方法，新增商机
        businessService.add(business);
        //3 响应Result
        return Result.success();
    }
}
