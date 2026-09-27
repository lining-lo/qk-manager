package com.qk.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 线索和商机统计信息实体类
 */
@Data
public class OverviewVO implements Serializable {
    private Integer clueTotal; // 线索总数
    private Integer clueWaitAllot; // 线索待分配数量
    private Integer clueWaitFollow; // 线索待跟进数量
    private Integer clueFollowing; // 线索跟进中数量
    private Integer clueFalse; // 线索伪线索数量
    private Integer clueConvertBusiness; // 线索转为商机数量

    private Integer businessTotal; // 商机总数
    private Integer businessWaitAllot; // 商机待分配数量
    private Integer businessWaitFollow; // 商机待跟进数量
    private Integer businessFollowing; // 商机跟进中数量
    private Integer businessFalse; // 商机伪线索数量
    private Integer businessConvertCustomer; // 商机转客户数量
}