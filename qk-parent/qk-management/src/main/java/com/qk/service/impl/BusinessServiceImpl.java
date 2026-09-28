package com.qk.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.qk.domain.PageResult;
import com.qk.dto.BusinessQueryDto;
import com.qk.entity.Business;
import com.qk.mapper.BusinessMapper;
import com.qk.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessServiceImpl extends ServiceImpl<BusinessMapper, Business> implements BusinessService {

    @Autowired
    private BusinessMapper businessMapper;

    /**
     * 根据条件分页查询商机
     * @param businessQueryDto 封装查询条件和分页参数
     * @return 分页结果
     */
    @Override
    public PageResult<Business> pageQuery(BusinessQueryDto businessQueryDto) {
        //1 设置分页参数
        Page<Business> page = new Page<>(businessQueryDto.getPage(), businessQueryDto.getPageSize());
        //2 调用mapper层方法，分页查询
        Page<Business> result = businessMapper.list(page, businessQueryDto);
        //3 封装分页结果
        return new PageResult<>(result.getTotal(), result.getRecords());
    }
}
