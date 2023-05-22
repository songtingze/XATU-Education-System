package com.xatu.gateway.domain;

import com.xatu.gateway.constant.CodeConstants;
import lombok.Data;

/**
 * 通用返回结构
 * @author Lenovo
 */
@Data
public class Result<T> {
    private String code;
    private String msg;
    private T data;

    public Result() {
    }

    public Result(T data) {
        this.data = data;
    }

    public static Result success() {
        Result result = new Result<>();
        result.setCode(CodeConstants.OK);
        result.setMsg("成功");
        return result;
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>(data);
        result.setCode(CodeConstants.OK);
        result.setMsg("成功");
        return result;
    }

    public static <T> Result<T> success(T data, String msg) {
        Result<T> result = new Result<>(data);
        result.setCode(CodeConstants.OK);
        result.setMsg(msg);
        return result;
    }

    public static Result error(String code, String msg) {
        Result result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
