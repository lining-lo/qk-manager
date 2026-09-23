package com.qk.mapper;
import com.qk.entity.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleMapper {
    /**
     * 根据条件查询角色
     * @param name     角色名称
     */
    List<Role> list(String name);

    /**
     * 新增角色
     * @param role 封装角色信息
     */
    @Insert("INSERT INTO role(name, label, remark, create_time, update_time) VALUES(#{name}, #{label},#{remark},  #{createTime}, #{updateTime})")
    void insert(Role role);

    /**
     * 根据ID查询角色
     * @param id 角色ID
     * @return 角色实体
     */
    @Select("SELECT id, name, label, remark, create_time , update_time FROM role WHERE id = #{id}")
    Role getById(Integer id);

    /**
     * 更新角色信息
     * @param role 角色实体
     */
    void updateById(Role role);
}
