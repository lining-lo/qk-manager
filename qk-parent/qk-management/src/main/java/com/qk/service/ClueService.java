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

    /**
     * 根据ID查询线索详细信息(包含线索跟进列表)
     * @param id 线索ID
     * @return 线索详细信息
     */
    Clue getClueById(Integer id);

    /**
     * 跟进线索
     *
     * @param clue 线索信息
     */
    void trackClue(Clue clue);

    /**
     * 将线索转为商机
     * @param id 线索ID
     */
    void convertToBusiness(Integer id);
}
