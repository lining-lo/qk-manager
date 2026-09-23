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

    /**
     * 根据ID查询活动
     * @param id 活动ID
     * @return 查询结果
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        //1 获取请求参数--->(@PathVariable Integer id)
        log.info("根据ID查询活动，id={}", id);
        //2 调用service层方法，根据ID查询活动
        Activity activity = activityService.getById(id);
        //3 响应Result
        return Result.success(activity);
    }

    /**
     * 修改活动
     * @param activity 活动信息
     */
    @PutMapping
    public Result update(@RequestBody Activity activity) {
        //1 接收请求参数--->(@RequestBody Activity activity)
        log.info("修改活动：activity={}", activity);
        //2 调用service层方法，修改活动
        activityService.update(activity);
        //3 响应Result
        return Result.success();
    }

    /**
     * 删除活动
     * @param id 活动ID
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        //1 接收请求参数--->(@PathVariable Activity activity)
        log.info("删除活动，id={}", id);
        //2 调用service层方法，删除活动
        activityService.delete(id);
        //3 响应Result
        return Result.success();
    }
}
