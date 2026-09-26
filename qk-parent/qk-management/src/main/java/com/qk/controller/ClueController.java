package com.qk.controller;

import com.qk.domain.PageResult;
import com.qk.domain.Result;
import com.qk.dto.ClueQueryDto;
import com.qk.entity.Clue;
import com.qk.service.ClueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/clues")
public class ClueController {
    @Autowired
    private ClueService clueService;

    /**
     新增线索
     * @param clue 封装线索信息
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Clue clue){
        //1 接收请求参数
        log.info("新增线索：{}", clue);
        //2 调用service，新增线索
        //clue.setStatus(1);  //不设置，表中该字段由默认值
        clueService.save(clue);
        //3 响应Result
        return Result.success();
    }


    /**
     * 根据条件分页查询
     * @param clueQueryDto 封装分页条件
     */
    @GetMapping
    public Result page(ClueQueryDto clueQueryDto) {
        //1 接收请求参数--->(ClueQueryDto clueQueryDto)
        log.info("查询参数: {}", clueQueryDto);
        //2 调用service层方法，分页查询
        PageResult<Clue> pageResult = clueService.pageQuery(clueQueryDto);
        //3 响应Result
        return Result.success(pageResult);
    }

    /**
     分配线索
     * @param clueId 线索id
     * @param userId 用户id
     */
    @PutMapping("/assign/{clueId}/{userId}")
    public Result assign(@PathVariable Integer clueId,@PathVariable Integer userId){
        //1 接收请求参数-->(@PathVariable Integer clueId,@PathVariable Integer userId)
        log.info("分配线索：clueId={}，userId={}", clueId, userId);
        //2 调用service，修改线索（分配线索）
        Clue clue = new Clue();  //封装要修改的数据以及条件id
        clue.setId(clueId);
        clue.setUserId(userId);
        clue.setStatus(2);
        clueService.updateById(clue); //update clue set user_id= ?,status=2 where id=?
        //3 响应Result
        return Result.success();
    }

    /**
     * 根据ID查询线索详细信息(包含线索跟进列表)
     * @param id 线索ID
     * @return 线索详细信息
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        //1 接收请求参数--->(@PathVariable Integer id)
        log.info("根据ID查询线索详细信息, id: {}", id);
        //2 调用service层方法，查询数据
        Clue clue = clueService.getClueById(id);
        //3 响应Result
        return Result.success(clue);
    }
}
