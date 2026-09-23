package com.qk.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qk.domain.PageResult;
import com.qk.dto.ActivityQueryDto;
import com.qk.mapper.ActivityMapper;
import com.qk.entity.Activity;
import com.qk.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    /**
     * 根据条件查询活动
     * @param activityQueryDto 查询条件
     */
    @Override
    public PageResult<Activity> page(ActivityQueryDto activityQueryDto) {
        //1 设置分页参数
        PageHelper.startPage(activityQueryDto.getPage(), activityQueryDto.getPageSize());
        //2 调用mapper层方法，分页查询活动
        Page<Activity> p = (Page<Activity>)activityMapper.list(activityQueryDto);
        //3 封装PageResult对象并返回
        return new PageResult<>(p.getTotal(), p.getResult());
    }

    /**
     * 新增活动
     * @param activity 封装活动信息
     */
    @Override
    public void add(Activity activity) {
        //1 设置基础属性
        activity.setCreateTime(LocalDateTime.now());
        activity.setUpdateTime(LocalDateTime.now());
        //2 调用mapper层方法，新增活动
        activityMapper.insert(activity);
    }

    /**
     * 根据ID查询活动
     * @param id 活动ID
     */
    @Override
    public Activity getById(Integer id) {
        //直接调用mapper层方法，根据id查询活动
        return activityMapper.getById(id);
    }
}
