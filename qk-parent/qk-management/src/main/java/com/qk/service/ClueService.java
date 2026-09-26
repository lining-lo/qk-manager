package com.qk.service;

import com.baomidou.mybatisplus.spring.service.IService;
import com.qk.domain.PageResult;
import com.qk.dto.ClueQueryDto;
import com.qk.entity.Clue;

public interface ClueService extends IService<Clue> {

    /**
     * 根据条件分页查询
     * @param clueQueryDto 封装分页条件
     */
    PageResult<Clue> pageQuery(ClueQueryDto clueQueryDto);


}
