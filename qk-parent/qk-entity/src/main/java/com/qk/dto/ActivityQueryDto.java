package com.qk.dto;
import lombok.Data;

/**
 * /activities?channel=1&type=1&status=1&page=1&pageSize=5
 * 封装查询参数
 */
@Data
public class ActivityQueryDto {
    private Integer channel; // 渠道来源
    private Integer type; // 活动类型(1: 课程折扣, 2: 代金券)
    private Integer status; // 活动状态
    private Integer page = 1; // 页码
    private Integer pageSize = 10; // 每页条数
}