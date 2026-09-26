package com.qk.dto;

import lombok.Data;

/**
 * 线索查询参数封装类
 */
@Data
public class ClueQueryDto {
    private Integer clueId; //线索ID
    private String phone;   //手机号
    private Integer status; //线索状态，1:待分配, 2:跟进中, 3:已关闭, 4:伪线索
    private Integer channel;    //线索来源，1:线上活动, 2:推广介绍
    private String assignName;  //线索归属人
    private Integer page = 1;   //分页查询的页码，如果未指定，默认为1
    private Integer pageSize = 10;  //分页查询的每页记录数，如果未指定，默认为10
}