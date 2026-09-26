package com.qk.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.qk.domain.PageResult;
import com.qk.dto.ClueQueryDto;
import com.qk.entity.Clue;
import com.qk.mapper.ClueMapper;
import com.qk.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClueServiceImpl extends ServiceImpl<ClueMapper, Clue> implements ClueService {

    @Autowired
    private ClueMapper clueMapper;

    /**
     * 根据条件分页查询
     * @param clueQueryDto 封装分页条件
     */
    @Override
    public PageResult<Clue> pageQuery(ClueQueryDto clueQueryDto) {
        //1 设置分页参数
        Page<Clue> page = new Page<>(clueQueryDto.getPage(), clueQueryDto.getPageSize());
        //2 调用mapper层方法，分页查询
        Page<Clue> p=clueMapper.list(page, clueQueryDto);
        //3 封装分页结果
        return new PageResult<Clue>(p.getTotal(), p.getRecords());
    }

}