package com.qk.controller;

import com.qk.domain.PageResult;
import com.qk.domain.Result;
import com.qk.dto.CustomerQueryDto;
import com.qk.entity.Customer;
import com.qk.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
}
