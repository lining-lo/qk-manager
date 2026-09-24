package com.qk.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.qk.domain.PageResult;
import com.qk.dto.UserLoginDto;
import com.qk.dto.UserQueryDto;
import com.qk.entity.User;
import com.qk.exception.BusinessException;
import com.qk.mapper.UserMapper;
import com.qk.service.UserService;
import com.qk.utils.JwtUtils;
import com.qk.vo.LoginResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 根据ID查询用户信息
     * @param id 用户ID
     * @return 用户实体
     */
    @Override
    public User getById(Integer id) {
        //直接调用mapper层方法，根据id查询用户信息
        return userMapper.getById(id);
    }

    /**
     * 修改用户信息
     * @param user 用户信息
     */
    @Override
    public void update(User user) {
        //1 设置更新时间为当前时间
        user.setUpdateTime(LocalDateTime.now());
        //2 密码加密处理
        user.setPassword(DigestUtils.md5DigestAsHex((user.getPassword()+"123").getBytes()));
        //3 调用mapper层方法，修改用户信息
        userMapper.updateById(user);
    }

    /**
     * 用户登录
     * @param userLoginDto 封装用户名和密码
     */
    @Override
    public LoginResultVo login(UserLoginDto userLoginDto) {
        // 查询用户信息
        User user = userMapper.getByUsername(userLoginDto.getUsername());
        // 判断用户是否存在
        if (user == null) {
            throw new BusinessException("用户名不存在"); // 用户不存在
        }
        //获取登录密码并加密处理。
        String password = DigestUtils.md5DigestAsHex(userLoginDto.getPassword().getBytes());
        if (!user.getPassword().equals(password )) {
            throw new BusinessException("密码错误"); // 密码错误
        }

        // 校验用户状态
        if (user.getStatus()==0) { // 0 表示停用状态
            throw new BusinessException("对不起, 您的账号已停用"); // 状态异常，不允许登录
        }

        // 构造登录结果
        LoginResultVo loginResultVo= new LoginResultVo();
        loginResultVo.setId(user.getId());
        loginResultVo.setUsername(user.getUsername());
        loginResultVo.setName(user.getName());
        loginResultVo.setImage(user.getImage());
        loginResultVo.setRoleLabel(user.getRoleLabel());

        //需求：生成令牌字符串，保存到loginResultVo中响应给客户端
        //①、创建map集合，设置令牌要存的数据，后期谁登录成功就保存谁的信息
        Map<String, Object> claims = new HashMap<>();
        claims.put("id",user.getId());
        claims.put("username",user.getUsername());
        //②、调用JwtUtils工具类方法，生成令牌字符串
        String jwt = JwtUtils.generateToken(claims);
        //③、封装令牌字符串到loginResultVo中响应给客户端
        loginResultVo.setToken(jwt); //令牌字符串，作为登录的凭证
        return loginResultVo;
    }
}
