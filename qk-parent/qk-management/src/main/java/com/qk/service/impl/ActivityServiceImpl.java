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
import java.util.List;

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

    /**
     * 修改活动
     * @param activity 活动信息
     */
    @Override
    public void update(Activity activity) {
        //1 设置基础属性(更新时间)
        activity.setUpdateTime(LocalDateTime.now());
        //2 调用mapper层方法，修改活动
        activityMapper.updateById(activity);
    }

    /**
     * 删除活动
     * @param id 活动ID
     */
    @Override
    public void delete(Integer id) {
        //调用mapper层方法，根据id删除活动
        activityMapper.deleteById(id);
    }

    /**
     * 查询所有活动信息
     */
    @Override
    public List<Activity> list() {
        //调用mapper层之前写好的list方法
        return activityMapper.list(null);
    }


}
