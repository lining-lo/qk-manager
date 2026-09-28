package com.qk.controller;

import com.qk.domain.PageResult;
import com.qk.dto.CustomerQueryDto;
import com.qk.entity.Customer;
import com.qk.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomerControllerTest {

    private CustomerService customerService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        customerService = mock(CustomerService.class);
        CustomerController customerController = new CustomerController();
        ReflectionTestUtils.setField(customerController, "customerService", customerService);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void pagePassesAllQueryParametersAndReturnsPageResult() throws Exception {
        Customer customer = new Customer();
        customer.setId(19);
        customer.setPhone("13800138010");
        customer.setName("王芳");
        customer.setCourseName("Java核心与AI开发基础");
        when(customerService.pageQuery(any(CustomerQueryDto.class)))
                .thenReturn(new PageResult<>(1L, List.of(customer)));

        mockMvc.perform(get("/customers")
                        .param("phone", "13309091111")
                        .param("name", "赵")
                        .param("channel", "1")
                        .param("subject", "1")
                        .param("page", "1")
                        .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.data.total").value(1))
                .andExpect(jsonPath("$.data.rows[0].id").value(19))
                .andExpect(jsonPath("$.data.rows[0].courseName").value("Java核心与AI开发基础"));

        ArgumentCaptor<CustomerQueryDto> queryCaptor = ArgumentCaptor.forClass(CustomerQueryDto.class);
        verify(customerService).pageQuery(queryCaptor.capture());
        CustomerQueryDto query = queryCaptor.getValue();
        assertThat(query.getPhone()).isEqualTo("13309091111");
        assertThat(query.getName()).isEqualTo("赵");
        assertThat(query.getChannel()).isEqualTo(1);
        assertThat(query.getSubject()).isEqualTo(1);
        assertThat(query.getPage()).isEqualTo(1);
        assertThat(query.getPageSize()).isEqualTo(10);
    }
}
