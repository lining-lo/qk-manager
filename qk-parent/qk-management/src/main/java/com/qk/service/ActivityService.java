package com.qk.service;

import com.qk.domain.PageResult;
import com.qk.dto.ActivityQueryDto;
import com.qk.entity.Activity;

public interface ActivityService {
    /**
     * 根据条件查询活动
     * @param activityQueryDto 查询条件
     */
    PageResult<Activity> page(ActivityQueryDto activityQueryDto);
}
