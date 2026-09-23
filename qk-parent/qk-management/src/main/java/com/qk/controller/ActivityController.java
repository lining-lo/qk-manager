package com.qk.controller;

import com.qk.domain.PageResult;
import com.qk.entity.Activity;
import com.qk.domain.Result;
import com.qk.dto.ActivityQueryDto;
import com.qk.service.ActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/activities")
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    /**
     * 根据条件查询活动
     * @param activityQueryDto 查询条件
     */
    @GetMapping
    public Result page(ActivityQueryDto activityQueryDto) {
        //1 接收请求参数
        log.info("查询活动，activityDto={}", activityQueryDto);
        //2 调用service，得到分页结果PageResult
        PageResult<Activity> pageResult = activityService.page(activityQueryDto);
        //3 响应Result
        return Result.success(pageResult);
    }

    /**
     * 新增活动
     * @param activity 封装活动信息(包括活动名称、状态)
     */
    @PostMapping
    public Result add(@RequestBody Activity activity) {
        //1 接收请求参数--->(@RequestBody Activity activity)
        log.info("新增活动：activity={}", activity);
        //2 调用service层方法，新增活动
        activityService.add(activity);
        //3 响应Result
        return Result.success();
    }
}
