package com.qk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 封装登录请求参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDto {
    private String username; // 用户名
    private String password; // 密码
}