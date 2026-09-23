package com.qk.mapper;
import com.qk.dto.ActivityQueryDto;
import com.qk.entity.Activity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ActivityMapper {
    /**
     * 根据条件查询活动
     * @param activityQueryDto     查询条件
     */
    List<Activity> list(ActivityQueryDto activityQueryDto);
}
