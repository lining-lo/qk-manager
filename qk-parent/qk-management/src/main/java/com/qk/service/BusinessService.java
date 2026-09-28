package com.qk.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.qk.domain.PageResult;
import com.qk.dto.BusinessQueryDto;
import com.qk.entity.Business;

public interface BusinessService extends IService<Business> {

    /**
     * 新增商机
     * @param business 封装商机信息
     */
    void add(Business business);

    /**
     * 根据条件分页查询商机
     * @param businessQueryDto 封装查询条件和分页参数
     * @return 分页结果
     */
    PageResult<Business> pageQuery(BusinessQueryDto businessQueryDto);
}
