package com.qk.service;

import com.qk.domain.PageResult;
import com.qk.dto.ActivityQueryDto;
import com.qk.entity.Activity;

import java.util.List;

public interface ActivityService {
    /**
     * 根据条件查询活动
     * @param activityQueryDto 查询条件
     */
    PageResult<Activity> page(ActivityQueryDto activityQueryDto);

    /**
     * 新增活动
     * @param activity 封装活动信息
     */
    void add(Activity activity);

    /**
     * 根据ID查询活动
     * @param id 活动ID
     * @return 查询结果
     */
    Activity getById(Integer id);

    /**
     * 修改活动
     * @param activity 活动信息
     */
    void update(Activity activity);

    /**
     * 删除活动
     * @param id 活动ID
     */
    void delete(Integer id);

    /**
     * 查询所有活动信息
     */
    List<Activity> list();
}
