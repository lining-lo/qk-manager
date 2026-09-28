package com.qk.controller;

import com.qk.domain.PageResult;
import com.qk.dto.ClueQueryDto;
import com.qk.entity.Clue;
import com.qk.service.ClueService;
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

class ClueControllerTest {

    private ClueService clueService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        clueService = mock(ClueService.class);
        ClueController clueController = new ClueController();
        ReflectionTestUtils.setField(clueController, "clueService", clueService);
        mockMvc = MockMvcBuilders.standaloneSetup(clueController).build();
    }

    @Test
    void poolPassesQueryParametersAndReturnsPageResult() throws Exception {
        Clue clue = new Clue();
        clue.setId(36);
        clue.setActivityName("Java课程折扣活动");
        when(clueService.pageQuery(any(ClueQueryDto.class)))
                .thenReturn(new PageResult<>(1L, List.of(clue)));

        mockMvc.perform(get("/clues/pool")
                        .param("clueId", "36")
                        .param("phone", "15809090000")
                        .param("channel", "1")
                        .param("page", "1")
                        .param("pageSize", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.data.total").value(1))
                .andExpect(jsonPath("$.data.rows[0].id").value(36))
                .andExpect(jsonPath("$.data.rows[0].activityName").value("Java课程折扣活动"));

        ArgumentCaptor<ClueQueryDto> queryCaptor = ArgumentCaptor.forClass(ClueQueryDto.class);
        verify(clueService).pageQuery(queryCaptor.capture());
        ClueQueryDto query = queryCaptor.getValue();
        assertThat(query.getClueId()).isEqualTo(36);
        assertThat(query.getPhone()).isEqualTo("15809090000");
        assertThat(query.getChannel()).isEqualTo(1);
        assertThat(query.getPage()).isEqualTo(1);
        assertThat(query.getPageSize()).isEqualTo(5);
    }
}
