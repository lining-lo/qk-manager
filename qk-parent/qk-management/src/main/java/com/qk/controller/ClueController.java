package com.qk.controller;

import com.qk.domain.Result;
import com.qk.entity.Clue;
import com.qk.service.ClueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
