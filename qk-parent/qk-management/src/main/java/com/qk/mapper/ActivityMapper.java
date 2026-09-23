package com.qk.mapper;
import com.qk.dto.ActivityQueryDto;
import com.qk.entity.Activity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ActivityMapper {
    /**
     * 根据条件查询活动
     * @param activityQueryDto     查询条件
     */
    List<Activity> list(ActivityQueryDto activityQueryDto);

    /**
     * 新增活动
     * @param activity 封装活动信息
     */
    @Insert("INSERT INTO activity(channel, name, start_time, end_time, description, type, discount, voucher, create_time, update_time) VALUES(#{channel}, #{name}, #{startTime}, #{endTime}, #{description}, #{type}, #{discount}, #{voucher}, #{createTime}, #{updateTime})")
    void insert(Activity activity);

    /**
     * 根据ID查询活动
     * @param id 活动ID
     * @return 活动实体
     */
    @Select("SELECT id, channel, name, start_time, end_time, description, type, discount, voucher, create_time, update_time FROM activity WHERE id = #{id}")
    Activity getById(Integer id);
}
