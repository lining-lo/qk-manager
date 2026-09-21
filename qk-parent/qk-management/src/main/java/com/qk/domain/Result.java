package com.qk.domain;

import lombok.Data;

/**
 * 后端统一返回结果
 */
@Data
public class Result {

    private Integer code; //编码：1成功，0为失败
    private String msg; //错误信息
    private Object data; //数据

    //封装增删改成功的结果
    public static Result success() {
        Result result = new Result();
        result.code = 1;
        result.msg = "success";
        return result;
    }

    //封装查询成功的结果
    public static Result success(Object data) {
        Result result = new Result();
        result.data = data;
        result.code = 1;
        result.msg = "success";
        return result;
    }

    //封装失败的结果
    public static Result error(String msg) {
        Result result = new Result();
        result.msg = msg;
        result.code = 0;
        return result;
    }

}
