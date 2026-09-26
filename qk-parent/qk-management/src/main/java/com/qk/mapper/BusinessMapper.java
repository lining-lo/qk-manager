package com.qk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qk.entity.Business;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商机管理Mapper
 */
@Mapper
public interface BusinessMapper extends BaseMapper<Business> {
}