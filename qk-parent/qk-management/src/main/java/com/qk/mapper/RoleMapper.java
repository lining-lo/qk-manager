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


}
