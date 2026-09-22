package com.qk.mapper;

import com.qk.dto.UserQueryDto;
import com.qk.entity.User;
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

}