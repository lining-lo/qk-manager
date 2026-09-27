package com.qk.service.impl;

import com.qk.mapper.BusinessMapper;
import com.qk.mapper.ClueMapper;
import com.qk.service.ReportService;
import com.qk.vo.OverviewVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ClueMapper clueMapper;
    @Autowired
    private BusinessMapper businessMapper;

    @Override
    public OverviewVO getOverview() {
        //1. 获取线索概览数据
        OverviewVO clueOverviewVO = clueMapper.getClueOverviewData();

        //2. 获取商机概览数据
        OverviewVO businessOverviewVO = businessMapper.getBusinessOverviewData();

        //3. 合并数据返回，将一个对象身上的属性值合并到另一个对象，为null的就不要覆盖。
        BeanUtils.copyProperties(businessOverviewVO, clueOverviewVO, "clueTotal", "clueWaitAllot", "clueWaitFollow", "clueFollowing", "clueFalse", "clueConvertBusiness");
        return clueOverviewVO;
    }
}