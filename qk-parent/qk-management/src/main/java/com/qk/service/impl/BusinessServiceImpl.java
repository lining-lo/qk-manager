package com.qk.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import com.qk.domain.PageResult;
import com.qk.dto.BusinessQueryDto;
import com.qk.entity.Business;
import com.qk.entity.BusinessTrackRecord;
import com.qk.mapper.BusinessMapper;
import com.qk.mapper.BusinessTrackRecordMapper;
import com.qk.service.BusinessService;
import com.qk.utils.CurrentUserHoler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BusinessServiceImpl extends ServiceImpl<BusinessMapper, Business> implements BusinessService {

    @Autowired
    private BusinessMapper businessMapper;

    @Autowired
    private BusinessTrackRecordMapper businessTrackRecordMapper;

    /**
     * 新增商机
     * @param business 封装商机信息
     */
    @Override
    public void add(Business business) {
        //1 设置初始状态和基础时间
        business.setStatus(1); //待分配
        business.setCreateTime(LocalDateTime.now());
        business.setUpdateTime(LocalDateTime.now());
        //2 调用mapper层方法，新增商机
        businessMapper.insert(business);
    }

    /**
     * 分配商机
     * @param businessId 商机ID
     * @param userId 用户ID
     */
    @Override
    public void assign(Integer businessId, Integer userId) {
        //1 封装要修改的数据以及条件ID
        Business business = new Business();
        business.setId(businessId);
        business.setUserId(userId);
        business.setStatus(2); //待跟进
        business.setUpdateTime(LocalDateTime.now());
        //2 调用mapper层方法，分配商机
        businessMapper.updateById(business);
    }

    /**
     * 根据ID查询商机详细信息
     * @param id 商机ID
     * @return 商机详细信息
     */
    @Override
    public Business getBusinessById(Integer id) {
        return businessMapper.getById(id);
    }

    /**
     * 跟进商机
     * @param business 商机信息和跟进记录
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void trackBusiness(Business business) {
        //1 更新商机状态为跟进中，并记录更新时间
        LocalDateTime now = LocalDateTime.now();
        business.setStatus(3); //跟进中
        business.setUpdateTime(now);
        businessMapper.updateById(business);

        //2 封装商机跟进记录
        BusinessTrackRecord trackRecord = new BusinessTrackRecord();
        trackRecord.setBusinessId(business.getId());
        trackRecord.setUserId(CurrentUserHoler.getCurrentUser());
        trackRecord.setTrackStatus(business.getTrackStatus());
        trackRecord.setKeyItems(formatKeyItems(business.getKeyItems()));
        trackRecord.setNextTime(business.getNextTime());
        trackRecord.setRecord(business.getRecord());
        trackRecord.setCreateTime(now);

        //3 新增商机跟进记录
        businessTrackRecordMapper.insert(trackRecord);
    }

    private String formatKeyItems(List<String> keyItems) {
        if (keyItems == null) {
            return null;
        }
        return "[" + String.join(", ", keyItems) + "]";
    }

    /**
     * 踢回公海
     * @param id 商机ID
     */
    @Override
    public void backToPool(Integer id) {
        //1 封装要修改的数据以及条件ID
        Business business = new Business();
        business.setId(id);
        business.setStatus(4); //回收
        business.setUpdateTime(LocalDateTime.now());
        //2 调用mapper层方法，将商机踢回公海
        businessMapper.updateById(business);
    }

    /**
     * 将商机转为客户
     * @param id 商机ID
     */
    @Override
    public void convertToCustomer(Integer id) {
        //1 封装要修改的数据以及条件ID
        Business business = new Business();
        business.setId(id);
        business.setStatus(5); //转客户
        business.setUpdateTime(LocalDateTime.now());
        //2 调用mapper层方法，将商机转为客户
        businessMapper.updateById(business);
    }

    /**
     * 根据条件分页查询商机
     * @param businessQueryDto 封装查询条件和分页参数
     * @return 分页结果
     */
    @Override
    public PageResult<Business> pageQuery(BusinessQueryDto businessQueryDto) {
        //1 设置分页参数
        Page<Business> page = new Page<>(businessQueryDto.getPage(), businessQueryDto.getPageSize());
        //2 调用mapper层方法，分页查询
        Page<Business> result = businessMapper.list(page, businessQueryDto);
        //3 封装分页结果
        return new PageResult<>(result.getTotal(), result.getRecords());
    }
}
