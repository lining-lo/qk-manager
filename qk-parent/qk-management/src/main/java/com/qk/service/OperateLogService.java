package com.qk.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.qk.domain.PageResult;
import com.qk.dto.OperateLogQueryDto;
import com.qk.entity.OperateLog;

public interface OperateLogService extends IService<OperateLog> {

    /**
     * 根据条件分页查询
     * @param operateLogQueryDto 封装分页条件
     */
    PageResult<OperateLog> pageQuery(OperateLogQueryDto operateLogQueryDto);
}