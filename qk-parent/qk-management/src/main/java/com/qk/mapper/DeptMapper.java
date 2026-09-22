package com.qk.mapper;

import com.qk.entity.Dept;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DeptMapper {
    /**
     新增部门
     @param dept 封装要新增部门信息
     */
    @Insert("insert into dept(name,status,create_time,update_time) values(#{name},#{status},#{createTime},#{updateTime})")
    void insert(Dept dept);

    /**
     * 根据条件查询部门
     * @param name     部门名称
     * @param status   状态
     */
    List<Dept> list(String name, Integer status);

    /**
     根据id查询部门
     @param id 要查询的部门id
     @return 部门对象
     */
    @Select("select id, name, status, create_time, update_time from dept where id=#{id}")
    Dept getById(Integer id);
}
