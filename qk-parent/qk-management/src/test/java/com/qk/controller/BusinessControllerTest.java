package com.qk.controller;

import com.qk.domain.PageResult;
import com.qk.dto.BusinessQueryDto;
import com.qk.entity.Business;
import com.qk.service.BusinessService;
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

class BusinessControllerTest {

    private BusinessService businessService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        businessService = mock(BusinessService.class);
        BusinessController businessController = new BusinessController();
        ReflectionTestUtils.setField(businessController, "businessService", businessService);
        mockMvc = MockMvcBuilders.standaloneSetup(businessController).build();
    }

    @Test
    void pagePassesAllQueryParametersAndReturnsPageResult() throws Exception {
        Business business = new Business();
        business.setId(21);
        business.setName("李明");
        business.setAssignName("张三");
        when(businessService.pageQuery(any(BusinessQueryDto.class)))
                .thenReturn(new PageResult<>(1L, List.of(business)));

        mockMvc.perform(get("/businesses")
                        .param("businessId", "21")
                        .param("name", "李")
                        .param("phone", "138012")
                        .param("status", "1")
                        .param("assignName", "张三")
                        .param("page", "1")
                        .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.data.total").value(1))
                .andExpect(jsonPath("$.data.rows[0].id").value(21))
                .andExpect(jsonPath("$.data.rows[0].assignName").value("张三"));

        ArgumentCaptor<BusinessQueryDto> queryCaptor = ArgumentCaptor.forClass(BusinessQueryDto.class);
        verify(businessService).pageQuery(queryCaptor.capture());
        BusinessQueryDto query = queryCaptor.getValue();
        assertThat(query.getBusinessId()).isEqualTo(21);
        assertThat(query.getName()).isEqualTo("李");
        assertThat(query.getPhone()).isEqualTo("138012");
        assertThat(query.getStatus()).isEqualTo(1);
        assertThat(query.getAssignName()).isEqualTo("张三");
        assertThat(query.getPage()).isEqualTo(1);
        assertThat(query.getPageSize()).isEqualTo(10);
    }

    @Test
    void addReceivesRequestBodyAndReturnsSuccess() throws Exception {
        String requestBody = """
                {
                    "phone": "13909018929",
                    "channel": 2,
                    "name": "承娟",
                    "gender": 2,
                    "age": 19,
                    "wechat": "cj2839232323",
                    "qq": "2595964758",
                    "subject": 1,
                    "remark": "无",
                    "degree": 4,
                    "jobStatus": 1,
                    "courseId": 1
                }
                """;

        mockMvc.perform(post("/businesses")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.msg").value("success"));

        ArgumentCaptor<Business> businessCaptor = ArgumentCaptor.forClass(Business.class);
        verify(businessService).add(businessCaptor.capture());
        Business business = businessCaptor.getValue();
        assertThat(business.getPhone()).isEqualTo("13909018929");
        assertThat(business.getChannel()).isEqualTo(2);
        assertThat(business.getName()).isEqualTo("承娟");
        assertThat(business.getGender()).isEqualTo(2);
        assertThat(business.getAge()).isEqualTo(19);
        assertThat(business.getWechat()).isEqualTo("cj2839232323");
        assertThat(business.getQq()).isEqualTo("2595964758");
        assertThat(business.getSubject()).isEqualTo(1);
        assertThat(business.getRemark()).isEqualTo("无");
        assertThat(business.getDegree()).isEqualTo(4);
        assertThat(business.getJobStatus()).isEqualTo(1);
        assertThat(business.getCourseId()).isEqualTo(1);
    }
}
