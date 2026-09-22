package com.qk.service;

import com.qk.domain.PageResult;
import com.qk.dto.UserQueryDto;
import com.qk.entity.User;

/**
 * 用户管理Service接口
 */
public interface UserService {

    /**
     带条件分页查询
     * @param queryDto 封装查询条件、当前页码、每页条数
     * @return 分页结果
     */
    PageResult<User> page(UserQueryDto queryDto);

    /**
     新增用户
     * @param user 封装用户信息(不包含password、createTime、updateTime)
     */
    void add(User user);
}