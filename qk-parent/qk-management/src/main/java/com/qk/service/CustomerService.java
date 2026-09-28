package com.qk.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.qk.domain.PageResult;
import com.qk.dto.CustomerQueryDto;
import com.qk.entity.Customer;

public interface CustomerService extends IService<Customer> {

    /**
     * 新增客户
     * @param customer 封装客户信息
     */
    void add(Customer customer);

    /**
     * 根据条件分页查询客户
     * @param customerQueryDto 封装查询条件和分页参数
     * @return 分页结果
     */
    PageResult<Customer> pageQuery(CustomerQueryDto customerQueryDto);
}
