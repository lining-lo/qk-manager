package com.qk.dto;

import lombok.Data;

/**
 * 操作日志查询参数封装类
 */
@Data
public class OperateLogQueryDto {
    private String operateUserName;  //操作人
    private Integer page = 1;   //分页查询的页码，如果未指定，默认为1
    private Integer pageSize = 10;  //分页查询的每页记录数，如果未指定，默认为10
}
