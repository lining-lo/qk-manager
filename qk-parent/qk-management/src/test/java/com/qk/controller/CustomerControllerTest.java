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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Test
    void addReceivesRequestBodyAndReturnsSuccess() throws Exception {
        String requestBody = """
                {
                    "phone": "13567210011",
                    "channel": 1,
                    "name": "库家明",
                    "gender": 1,
                    "age": 22,
                    "wechat": "kujiaming1121",
                    "qq": "3353439142",
                    "degree": 4,
                    "jobStatus": 1,
                    "subject": 1,
                    "courseId": 1
                }
                """;

        mockMvc.perform(post("/customers")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.msg").value("success"));

        ArgumentCaptor<Customer> customerCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerService).add(customerCaptor.capture());
        Customer customer = customerCaptor.getValue();
        assertThat(customer.getPhone()).isEqualTo("13567210011");
        assertThat(customer.getChannel()).isEqualTo(1);
        assertThat(customer.getName()).isEqualTo("库家明");
        assertThat(customer.getGender()).isEqualTo(1);
        assertThat(customer.getAge()).isEqualTo(22);
        assertThat(customer.getWechat()).isEqualTo("kujiaming1121");
        assertThat(customer.getQq()).isEqualTo("3353439142");
        assertThat(customer.getDegree()).isEqualTo(4);
        assertThat(customer.getJobStatus()).isEqualTo(1);
        assertThat(customer.getSubject()).isEqualTo(1);
        assertThat(customer.getCourseId()).isEqualTo(1);
    }

    @Test
    void getByIdReturnsCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setId(21);
        customer.setPhone("13567210011");
        customer.setName("库家明");
        when(customerService.getCustomerById(21)).thenReturn(customer);

        mockMvc.perform(get("/customers/21"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.data.id").value(21))
                .andExpect(jsonPath("$.data.phone").value("13567210011"))
                .andExpect(jsonPath("$.data.name").value("库家明"));

        verify(customerService).getCustomerById(21);
    }
}
