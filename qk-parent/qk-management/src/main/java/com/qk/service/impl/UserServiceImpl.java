package com.qk.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qk.domain.PageResult;
import com.qk.dto.UserQueryDto;
import com.qk.entity.User;
import com.qk.mapper.UserMapper;
import com.qk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     带条件分页查询
     @param queryDto 封装查询条件、当前页码、每页条数
     @return 分页结果
     */
    @Override
    public PageResult<User> page(UserQueryDto queryDto) {
        //1 设置分页参数(当前页码和每页条数)
        PageHelper.startPage(queryDto.getPage(),queryDto.getPageSize());
        //2 调用Mapper层方法，分页查询
        Page<User> page = (Page<User>)userMapper.list(queryDto);
        //3 获取分页结果封装成PageResult对象返回
        return new PageResult<>(page.getTotal(),page.getResult());
    }

    /**
     * 新增用户
     * @param user 用户信息
     */
    @Override
    public void add(User user) {
        //1 设置默认密码：用户名+123
        //user.setPassword(user.getUsername()+"123");
        //使用MD5算法对密码进行加密
        user.setPassword(DigestUtils.md5DigestAsHex((user.getUsername()+"123").getBytes()));
        //2 设置创建时间和更新时间为当前时间
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        //3 调用mapper层方法，保存用户
        userMapper.insert(user);
    }

    /**
     * 批量删除用户
     * @param ids 用户ID数组
     */
    @Override
    public void delete(List<Integer> ids) {
        //直接调用mapper层方法，批量删除用户
        userMapper.deleteByIds(ids);
    }
}
