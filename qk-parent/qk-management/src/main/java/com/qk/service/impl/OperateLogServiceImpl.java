package com.qk.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.qk.domain.PageResult;
import com.qk.dto.OperateLogQueryDto;
import com.qk.entity.Clue;
import com.qk.entity.OperateLog;
import com.qk.mapper.OperateLogMapper;
import com.qk.service.OperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperateLogServiceImpl extends ServiceImpl<OperateLogMapper, OperateLog> implements OperateLogService {

    @Autowired
    private OperateLogMapper operateLogMapper;

    /**
     * 根据条件分页查询
     * @param operateLogQueryDto 封装分页条件
     */
    @Override
    public PageResult<OperateLog> pageQuery(OperateLogQueryDto operateLogQueryDto) {
        //1 设置分页参数
        Page<OperateLog> page = new Page<>(operateLogQueryDto.getPage(), operateLogQueryDto.getPageSize());
        //2 调用mapper层方法，分页查询
        Page<OperateLog> p=operateLogMapper.list(page, operateLogQueryDto);
        //3 封装分页结果
        return new PageResult<OperateLog>(p.getTotal(), p.getRecords());
    }
}
