package com.qk.dto;

import lombok.Data;

/**
 * 客户查询参数封装类
 */
@Data
public class CustomerQueryDto {
    private String phone; //手机号
    private String name; //客户姓名
    private Integer channel; //渠道来源
    private Integer subject; //意向学科
    private Integer page = 1; //分页查询的页码，如果未指定，默认为1
    private Integer pageSize = 10; //分页查询的每页记录数，如果未指定，默认为10
}
