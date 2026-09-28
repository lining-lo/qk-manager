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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
     * 公海池列表查询
     * @param businessQueryDto 封装查询条件和分页参数
     */
    @GetMapping("/pool")
    public Result pool(BusinessQueryDto businessQueryDto) {
        //1 接收请求参数，并固定查询已回收商机
        businessQueryDto.setStatus(4); //回收
        businessQueryDto.setAssignName(null);
        log.info("商机公海池查询参数: {}", businessQueryDto);
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

    /**
     * 分配商机
     * @param businessId 商机ID
     * @param userId 用户ID
     */
    @Log
    @PutMapping("/assign/{businessId}/{userId}")
    public Result assign(@PathVariable Integer businessId, @PathVariable Integer userId) {
        //1 接收请求参数
        log.info("分配商机: businessId={}, userId={}", businessId, userId);
        //2 调用service层方法，分配商机
        businessService.assign(businessId, userId);
        //3 响应Result
        return Result.success();
    }

    /**
     * 根据ID查询商机详细信息
     * @param id 商机ID
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        //1 接收请求参数
        log.info("根据ID查询商机详细信息: id={}", id);
        //2 调用service层方法，查询商机详细信息
        Business business = businessService.getBusinessById(id);
        //3 响应Result
        return Result.success(business);
    }

    /**
     * 跟进商机
     * @param business 商机信息和跟进记录
     */
    @Log
    @PutMapping
    public Result trackBusiness(@RequestBody Business business) {
        //1 接收请求参数
        log.info("跟进商机: {}", business);
        //2 调用service层方法，跟进商机
        businessService.trackBusiness(business);
        //3 响应Result
        return Result.success();
    }

    /**
     * 踢回公海
     * @param id 商机ID
     */
    @Log
    @PutMapping("/back/{id}")
    public Result backToPool(@PathVariable Integer id) {
        //1 接收请求参数
        log.info("踢回公海: id={}", id);
        //2 调用service层方法，将商机踢回公海
        businessService.backToPool(id);
        //3 响应Result
        return Result.success();
    }

    /**
     * 将商机转为客户
     * @param id 商机ID
     */
    @Log
    @PostMapping("/toCustomer/{id}")
    public Result convertToCustomer(@PathVariable Integer id) {
        //1 接收请求参数
        log.info("将商机转为客户: id={}", id);
        //2 调用service层方法，将商机转为客户
        businessService.convertToCustomer(id);
        //3 响应Result
        return Result.success();
    }
}
