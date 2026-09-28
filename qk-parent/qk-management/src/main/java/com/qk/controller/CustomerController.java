package com.qk.controller;

import com.qk.anno.Log;
import com.qk.domain.PageResult;
import com.qk.domain.Result;
import com.qk.dto.CustomerQueryDto;
import com.qk.entity.Customer;
import com.qk.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * 根据条件分页查询客户
     * @param customerQueryDto 封装查询条件和分页参数
     */
    @GetMapping
    public Result page(CustomerQueryDto customerQueryDto) {
        //1 接收请求参数
        log.info("客户查询参数: {}", customerQueryDto);
        //2 调用service层方法，分页查询
        PageResult<Customer> pageResult = customerService.pageQuery(customerQueryDto);
        //3 响应Result
        return Result.success(pageResult);
    }

    /**
     * 添加客户
     * @param customer 封装客户信息
     */
    @Log
    @PostMapping
    public Result add(@RequestBody Customer customer) {
        //1 接收请求参数
        log.info("新增客户: {}", customer);
        //2 调用service层方法，新增客户
        customerService.add(customer);
        //3 响应Result
        return Result.success();
    }

    /**
     * 根据ID查询客户
     * @param id 客户ID
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        //1 接收请求参数
        log.info("根据ID查询客户: id={}", id);
        //2 调用service层方法，查询客户
        Customer customer = customerService.getCustomerById(id);
        //3 响应Result
        return Result.success(customer);
    }
}
