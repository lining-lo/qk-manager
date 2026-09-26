package com.qk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qk.dto.ClueQueryDto;
import com.qk.entity.Clue;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ClueMapper extends BaseMapper<Clue> {
    /**
     * 线索列表
     */
    Page<Clue> list(Page<Clue> page, ClueQueryDto clueQueryDto);

    /**
     * 根据ID查询线索详细信息(包含线索跟进列表)
     * @param id 线索ID
     * @return 线索详细信息
     */
    Clue getById(Integer id);
}
