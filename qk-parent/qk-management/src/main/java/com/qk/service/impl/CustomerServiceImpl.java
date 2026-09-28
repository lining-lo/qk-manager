package com.qk.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.qk.domain.PageResult;
import com.qk.dto.CustomerQueryDto;
import com.qk.entity.Customer;
import com.qk.mapper.CustomerMapper;
import com.qk.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    /**
     * 新增客户
     * @param customer 封装客户信息
     */
    @Override
    public void add(Customer customer) {
        //1 设置基础时间
        customer.setCreateTime(LocalDateTime.now());
        customer.setUpdateTime(LocalDateTime.now());
        //2 调用mapper层方法，新增客户
        customerMapper.insert(customer);
    }

    /**
     * 根据ID查询客户
     * @param id 客户ID
     * @return 客户信息
     */
    @Override
    public Customer getCustomerById(Integer id) {
        return customerMapper.selectById(id);
    }

    /**
     * 根据条件分页查询客户
     * @param customerQueryDto 封装查询条件和分页参数
     * @return 分页结果
     */
    @Override
    public PageResult<Customer> pageQuery(CustomerQueryDto customerQueryDto) {
        //1 设置分页参数
        Page<Customer> page = new Page<>(customerQueryDto.getPage(), customerQueryDto.getPageSize());
        //2 调用mapper层方法，分页查询
        Page<Customer> result = customerMapper.list(page, customerQueryDto);
        //3 封装分页结果
        return new PageResult<>(result.getTotal(), result.getRecords());
    }
}
