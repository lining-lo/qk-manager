package com.qk.dto;

import lombok.Data;

/**
 * 商机查询参数封装类
 */
@Data
public class BusinessQueryDto {
    private Integer businessId; //商机ID
    private String name;        //客户姓名
    private String phone;       //手机号
    private Integer status;     //商机状态
    private String assignName;  //归属人姓名
    private Integer page = 1;   //分页查询的页码，如果未指定，默认为1
    private Integer pageSize = 10; //分页查询的每页记录数，如果未指定，默认为10
}
