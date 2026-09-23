package com.qk.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户实体类
 */
@Data
public class User {
    private Integer id; //主键，唯一标识
    private String username;    //用户名，唯一
    private String password;    //密码
    private String name;    //姓名
    private String phone;   //手机号，唯一
    private String email;   //邮箱，唯一
    private Integer gender; //性别，1: 男，2: 女
    private Integer status; //状态，1: 正常，0: 停用
    private Integer deptId; //部门id，关联部门表主键
    private Integer roleId; //角色id，关联角色表主键
    private String image;   //头像url路径
    private String remark;  //备注，50字以内
    private LocalDateTime createTime;   //创建时间
    private LocalDateTime updateTime;   //修改时间

    //扩展属性
    private String deptName; //部门名称
    private String roleName; //角色名称
    private String roleLabel; //部门标签(登录)
}
