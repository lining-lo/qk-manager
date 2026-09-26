package com.qk.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.qk.domain.PageResult;
import com.qk.dto.ClueQueryDto;
import com.qk.entity.Clue;
import com.qk.entity.ClueTrackRecord;
import com.qk.mapper.ClueMapper;
import com.qk.mapper.ClueTrackRecordMapper;
import com.qk.service.ClueService;
import com.qk.utils.CurrentUserHoler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ClueServiceImpl extends ServiceImpl<ClueMapper, Clue> implements ClueService {

    @Autowired
    private ClueMapper clueMapper;

    @Autowired
    private ClueTrackRecordMapper clueTrackRecordMapper;

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

    /**
     * 根据ID查询线索详细信息(包含线索跟进列表)
     * @param id 线索ID
     * @return 线索详细信息
     */
    @Override
    public Clue getClueById(Integer id) {
        return clueMapper.getById(id);
    }

    /**
     * 跟进线索
     * @param clue 线索信息
     */
    @Transactional(rollbackFor = Exception.class) //指定要回滚的异常类型
    @Override
    public void trackClue(Clue clue) {
        //1 调用ClueMapper更新线索状态为"跟进中"（跟进中状态值为3）
        clue.setStatus(3); // 跟进中
        clue.setUpdateTime(LocalDateTime.now()); // 更新时间
        this.updateById(clue);

        //人为制造异常模拟
        //int i = 1/0;

        //2 封装跟进记录
        ClueTrackRecord trackRecord = new ClueTrackRecord();
        trackRecord.setClueId(clue.getId());
        trackRecord.setUserId(CurrentUserHoler.getCurrentUser()); //当前登录用户ID
        trackRecord.setSubject(clue.getSubject());
        trackRecord.setLevel(clue.getLevel());
        trackRecord.setRecord(clue.getRecord());
        trackRecord.setNextTime(clue.getNextTime());
        trackRecord.setType(1); // 正常跟进
        trackRecord.setCreateTime(LocalDateTime.now());
        //3 调用ClueTrackRecordMapper层方法，保存更加记录
        clueTrackRecordMapper.insert(trackRecord);
    }

}