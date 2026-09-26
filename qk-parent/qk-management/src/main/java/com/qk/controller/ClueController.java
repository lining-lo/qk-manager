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
}
