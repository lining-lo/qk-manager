package com.qk.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qk.domain.PageResult;
import com.qk.dto.BusinessQueryDto;
import com.qk.entity.Business;
import com.qk.entity.BusinessTrackRecord;
import com.qk.mapper.BusinessTrackRecordMapper;
import com.qk.mapper.BusinessMapper;
import com.qk.utils.CurrentUserHoler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BusinessServiceImplTest {

    @Mock
    private BusinessMapper businessMapper;

    @Mock
    private BusinessTrackRecordMapper businessTrackRecordMapper;

    @InjectMocks
    private BusinessServiceImpl businessService;

    @AfterEach
    void clearCurrentUser() {
        CurrentUserHoler.removeCurrentUser();
    }

    @Test
    void pageQueryReturnsMybatisPlusPageData() {
        BusinessQueryDto query = new BusinessQueryDto();
        query.setBusinessId(21);
        query.setName("李");
        query.setPhone("138012");
        query.setStatus(1);
        query.setAssignName("张三");
        query.setPage(1);
        query.setPageSize(10);

        Business business = new Business();
        business.setId(21);
        business.setAssignName("张三");
        Page<Business> page = new Page<>(1, 10);
        page.setTotal(1);
        page.setRecords(List.of(business));
        when(businessMapper.list(any(Page.class), same(query))).thenReturn(page);

        PageResult<Business> result = businessService.pageQuery(query);

        assertThat(result.getTotal()).isEqualTo(1);
        assertThat(result.getRows()).containsExactly(business);

        ArgumentCaptor<Page<Business>> pageCaptor = ArgumentCaptor.forClass(Page.class);
        verify(businessMapper).list(pageCaptor.capture(), same(query));
        assertThat(pageCaptor.getValue().getCurrent()).isEqualTo(1);
        assertThat(pageCaptor.getValue().getSize()).isEqualTo(10);
    }

    @Test
    void addSetsInitialValuesAndInsertsBusiness() {
        Business business = new Business();
        business.setName("承娟");
        business.setPhone("13909018929");

        businessService.add(business);

        ArgumentCaptor<Business> businessCaptor = ArgumentCaptor.forClass(Business.class);
        verify(businessMapper).insert(businessCaptor.capture());
        Business savedBusiness = businessCaptor.getValue();
        assertThat(savedBusiness.getStatus()).isEqualTo(1);
        assertThat(savedBusiness.getCreateTime()).isNotNull();
        assertThat(savedBusiness.getUpdateTime()).isNotNull();
    }

    @Test
    void assignSetsOwnerAndStatusThenUpdatesBusiness() {
        businessService.assign(30, 22);

        ArgumentCaptor<Business> businessCaptor = ArgumentCaptor.forClass(Business.class);
        verify(businessMapper).updateById(businessCaptor.capture());
        Business business = businessCaptor.getValue();
        assertThat(business.getId()).isEqualTo(30);
        assertThat(business.getUserId()).isEqualTo(22);
        assertThat(business.getStatus()).isEqualTo(2);
        assertThat(business.getUpdateTime()).isNotNull();
    }

    @Test
    void getBusinessByIdReturnsMapperResult() {
        Business business = new Business();
        business.setId(15);
        when(businessMapper.getById(15)).thenReturn(business);

        Business result = businessService.getBusinessById(15);

        assertThat(result).isSameAs(business);
        verify(businessMapper).getById(15);
    }

    @Test
    void trackBusinessUpdatesStatusAndInsertsTrackRecord() {
        CurrentUserHoler.setCurrentUser(22);
        Business business = new Business();
        business.setId(14);
        business.setStatus(2);
        business.setUserId(22);
        business.setNextTime(LocalDateTime.of(2025, 6, 23, 10, 0));
        business.setKeyItems(List.of("课程", "时间"));
        business.setTrackStatus(1);
        business.setRecord("了解了课程及上课时间");

        businessService.trackBusiness(business);

        ArgumentCaptor<Business> businessCaptor = ArgumentCaptor.forClass(Business.class);
        verify(businessMapper).updateById(businessCaptor.capture());
        Business updatedBusiness = businessCaptor.getValue();
        assertThat(updatedBusiness.getId()).isEqualTo(14);
        assertThat(updatedBusiness.getStatus()).isEqualTo(3);
        assertThat(updatedBusiness.getUpdateTime()).isNotNull();

        ArgumentCaptor<BusinessTrackRecord> recordCaptor = ArgumentCaptor.forClass(BusinessTrackRecord.class);
        verify(businessTrackRecordMapper).insert(recordCaptor.capture());
        BusinessTrackRecord trackRecord = recordCaptor.getValue();
        assertThat(trackRecord.getBusinessId()).isEqualTo(14);
        assertThat(trackRecord.getUserId()).isEqualTo(22);
        assertThat(trackRecord.getTrackStatus()).isEqualTo(1);
        assertThat(trackRecord.getKeyItems()).isEqualTo("[课程, 时间]");
        assertThat(trackRecord.getNextTime()).isEqualTo(LocalDateTime.of(2025, 6, 23, 10, 0));
        assertThat(trackRecord.getRecord()).isEqualTo("了解了课程及上课时间");
        assertThat(trackRecord.getCreateTime()).isNotNull();
    }

    @Test
    void backToPoolUpdatesStatusToReclaimed() {
        businessService.backToPool(30);

        ArgumentCaptor<Business> businessCaptor = ArgumentCaptor.forClass(Business.class);
        verify(businessMapper).updateById(businessCaptor.capture());
        Business business = businessCaptor.getValue();
        assertThat(business.getId()).isEqualTo(30);
        assertThat(business.getStatus()).isEqualTo(4);
        assertThat(business.getUserId()).isNull();
        assertThat(business.getUpdateTime()).isNotNull();
    }

    @Test
    void convertToCustomerUpdatesStatusToCustomer() {
        businessService.convertToCustomer(30);

        ArgumentCaptor<Business> businessCaptor = ArgumentCaptor.forClass(Business.class);
        verify(businessMapper).updateById(businessCaptor.capture());
        Business business = businessCaptor.getValue();
        assertThat(business.getId()).isEqualTo(30);
        assertThat(business.getStatus()).isEqualTo(5);
        assertThat(business.getUpdateTime()).isNotNull();
    }
}
