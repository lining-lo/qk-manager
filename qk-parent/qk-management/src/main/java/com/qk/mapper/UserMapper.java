package com.qk.mapper;

import com.qk.dto.UserQueryDto;
import com.qk.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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

    /**
     * 根据ID查询用户信息
     * @param id 用户ID
     * @return 用户实体
     */
    @Select("SELECT id, username, password, name, phone, email, gender, status, dept_id, role_id, image, remark, create_time, update_time FROM user WHERE id = #{id}")
    User getById(Integer id);

    /**
     * 更新用户信息（动态SQL）
     * @param user 用户实体
     */
    void updateById(User user);

    /**
     根据用户名查询用户信息
     * @param username 用户名
     */
    @Select("select u.*, r.label as role_label from user u left join role r on u.role_id = r.id where u.username = #{username}")
    User getByUsername(String username);
}