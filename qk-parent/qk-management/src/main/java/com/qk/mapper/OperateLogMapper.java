package com.qk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qk.dto.OperateLogQueryDto;
import com.qk.entity.OperateLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 操作日志管理Mapper
 */
@Mapper
public interface OperateLogMapper extends BaseMapper<OperateLog> {

    /**
     * 操作日志列表
     */
    Page<OperateLog> list(Page<OperateLog> page, OperateLogQueryDto operateLogQueryDto);
}