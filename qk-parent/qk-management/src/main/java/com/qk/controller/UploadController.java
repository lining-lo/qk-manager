package com.qk.controller;

import com.qk.domain.Result;
import com.qk.utils.AliyunOSSOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
public class UploadController {

    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;

    /**
     * 文件上传
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile image) throws Exception {
        log.info("文件上传开始：{}", image.getOriginalFilename());
        //1 获取文件名
        String filename = image.getOriginalFilename(); //a.jpg  b.jpg...

        //2 将文件上传到阿里云oss对象存储服务器并获取图片的访问地址
        String url = aliyunOSSOperator.upload(image.getBytes(), filename);

        //3 返回图片的访问路径
        return Result.success(url);
    }
}