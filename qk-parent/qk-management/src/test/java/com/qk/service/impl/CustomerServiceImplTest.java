package com.qk.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qk.domain.PageResult;
import com.qk.dto.CustomerQueryDto;
import com.qk.entity.Customer;
import com.qk.mapper.CustomerMapper;
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
class CustomerServiceImplTest {

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    void pageQueryReturnsMybatisPlusPageData() {
        CustomerQueryDto query = new CustomerQueryDto();
        query.setPhone("13309091111");
        query.setName("赵");
        query.setChannel(1);
        query.setSubject(1);
        query.setPage(1);
        query.setPageSize(10);

        Customer customer = new Customer();
        customer.setId(19);
        customer.setCourseName("Java核心与AI开发基础");
        Page<Customer> page = new Page<>(1, 10);
        page.setTotal(1);
        page.setRecords(List.of(customer));
        when(customerMapper.list(any(Page.class), same(query))).thenReturn(page);

        PageResult<Customer> result = customerService.pageQuery(query);

        assertThat(result.getTotal()).isEqualTo(1);
        assertThat(result.getRows()).containsExactly(customer);

        ArgumentCaptor<Page<Customer>> pageCaptor = ArgumentCaptor.forClass(Page.class);
        verify(customerMapper).list(pageCaptor.capture(), same(query));
        assertThat(pageCaptor.getValue().getCurrent()).isEqualTo(1);
        assertThat(pageCaptor.getValue().getSize()).isEqualTo(10);
    }

    @Test
    void addSetsTimesAndInsertsCustomer() {
        Customer customer = new Customer();
        customer.setPhone("13567210011");
        customer.setName("库家明");

        customerService.add(customer);

        ArgumentCaptor<Customer> customerCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerMapper).insert(customerCaptor.capture());
        Customer savedCustomer = customerCaptor.getValue();
        assertThat(savedCustomer.getCreateTime()).isNotNull();
        assertThat(savedCustomer.getUpdateTime()).isNotNull();
    }

    @Test
    void getCustomerByIdReturnsMapperResult() {
        Customer customer = new Customer();
        customer.setId(21);
        when(customerMapper.selectById(21)).thenReturn(customer);

        Customer result = customerService.getCustomerById(21);

        assertThat(result).isSameAs(customer);
        verify(customerMapper).selectById(21);
    }

    @Test
    void updateRefreshesUpdateTimeAndDoesNotChangeCreateTime() {
        Customer customer = new Customer();
        customer.setId(21);
        customer.setName("库明");
        customer.setCreateTime(LocalDateTime.of(2025, 6, 1, 21, 55, 50));

        customerService.updateCustomer(customer);

        ArgumentCaptor<Customer> customerCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerMapper).updateById(customerCaptor.capture());
        Customer savedCustomer = customerCaptor.getValue();
        assertThat(savedCustomer.getId()).isEqualTo(21);
        assertThat(savedCustomer.getCreateTime()).isNull();
        assertThat(savedCustomer.getUpdateTime()).isNotNull();
    }
}
