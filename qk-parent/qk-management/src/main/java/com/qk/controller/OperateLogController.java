package com.qk.controller;

import com.qk.domain.PageResult;
import com.qk.domain.Result;
import com.qk.dto.OperateLogQueryDto;
import com.qk.entity.OperateLog;
import com.qk.service.OperateLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/logs")
public class OperateLogController {

    @Autowired
    private OperateLogService operateLogService;

    /**
     * 根据条件分页查询
     * @param operateLogQueryDto 封装分页条件
     */
    @GetMapping
    public Result page(OperateLogQueryDto operateLogQueryDto) {
        //1 接收请求参数--->(OperateLogQueryDto operateLogQueryDto)
        log.info("查询参数: {}", operateLogQueryDto);
        //2 调用service层方法，分页查询
        PageResult<OperateLog> pageResult = operateLogService.pageQuery(operateLogQueryDto);
        //3 响应Result
        return Result.success(pageResult);
    }
}
