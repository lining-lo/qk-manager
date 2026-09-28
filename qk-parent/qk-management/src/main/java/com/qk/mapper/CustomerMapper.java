package com.qk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qk.dto.CustomerQueryDto;
import com.qk.entity.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 客户管理Mapper
 */
@Mapper
public interface CustomerMapper extends BaseMapper<Customer> {

    /**
     * 根据条件分页查询客户
     */
    Page<Customer> list(Page<Customer> page, @Param("customerQueryDto") CustomerQueryDto customerQueryDto);
}
