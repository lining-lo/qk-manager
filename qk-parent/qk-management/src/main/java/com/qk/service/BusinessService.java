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
     * 分配商机
     * @param businessId 商机ID
     * @param userId 用户ID
     */
    void assign(Integer businessId, Integer userId);

    /**
     * 根据ID查询商机详细信息
     * @param id 商机ID
     * @return 商机详细信息
     */
    Business getBusinessById(Integer id);

    /**
     * 根据条件分页查询商机
     * @param businessQueryDto 封装查询条件和分页参数
     * @return 分页结果
     */
    PageResult<Business> pageQuery(BusinessQueryDto businessQueryDto);
}
