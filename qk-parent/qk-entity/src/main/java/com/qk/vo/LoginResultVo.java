package com.qk.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录结果实体类
 * 用于封装登录后返回给前端的结果
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResultVo {
    private Integer id; //用户ID
    private String username; //用户名
    private String name; //姓名
    private String image; //头像URL
    private String roleLabel; //角色标签
    private String token; //访问令牌
}
