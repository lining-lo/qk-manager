package com.qk.mapper;

import com.qk.dto.UserQueryDto;
import com.qk.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户数据访问接口
 */
@Mapper
public interface UserMapper {

    /**
     带条件分页查询
     @param queryDto 封装查询条件、当前页码、每页条数
     */
    List<User> list(UserQueryDto queryDto);

    /**
     新增用户
     @param user 封装用户信息
     */
    @Insert("INSERT INTO user(username, password, name, phone, email, gender, status, dept_id, role_id, image, remark, create_time, update_time) VALUES(#{username}, #{password}, #{name}, #{phone}, #{email}, #{gender}, #{status}, #{deptId}, #{roleId}, #{image}, #{remark}, #{createTime}, #{updateTime})")
    void insert(User user);

    /**
     * 批量删除用户
     * @param ids 用户ID数组
     */
    void deleteByIds(List<Integer> ids);

}