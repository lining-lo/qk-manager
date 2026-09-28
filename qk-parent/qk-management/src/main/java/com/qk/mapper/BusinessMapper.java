package com.qk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qk.dto.BusinessQueryDto;
import com.qk.entity.Business;
import com.qk.vo.OverviewVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 商机管理Mapper
 */
@Mapper
public interface BusinessMapper extends BaseMapper<Business> {
    /**
     * 根据条件分页查询商机
     */
    Page<Business> list(Page<Business> page, @Param("businessQueryDto") BusinessQueryDto businessQueryDto);

    /**
     * 根据ID查询商机详细信息(包含商机跟进列表)
     * @param id 商机ID
     * @return 商机详细信息
     */
    Business getById(Integer id);

    /**
     * 获取商机概览数据
     */
    OverviewVO getBusinessOverviewData();
}
