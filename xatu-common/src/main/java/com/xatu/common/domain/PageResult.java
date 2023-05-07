package com.xatu.common.domain;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xatu.common.constant.CodeConstants;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 通用列表返回结构
 */
@Getter
@Setter
public class PageResult<T> {
    private String code;
    private String msg;
    private List<T> data;

    private Long total;
    private Long current;

    public PageResult() {
    }

    public PageResult(String code, String msg, List<T> data, long total, long current) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.total = total;
        this.current = current;
    }

    public PageResult(List<T> data) {
        this(CodeConstants.NONE, "无状态", data, data.size(), 1);
    }

    public static <T> PageResult<T> success(List<T> data) {
        PageResult<T> result = new PageResult<>(data);
        result.setCode(CodeConstants.OK);
        result.setMsg("成功");
        return result;
    }

    public static <T> PageResult<T> success(IPage<T> data) {
        return success(data.getRecords(), data.getTotal(), data.getCurrent());
    }

    public static <T> PageResult<T> success(List<T> data, long total, long current) {
        return new PageResult<>(CodeConstants.OK, "成功", data, total, current);
    }

    public static <T> PageResult<T> success(List<T> data, String msg) {
        PageResult<T> result = new PageResult<>(data);
        result.setCode(CodeConstants.OK);
        result.setMsg(msg);
        return result;
    }

    public static PageResult error(String code, String msg) {
        PageResult result = new PageResult<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
