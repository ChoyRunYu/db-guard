package cn.com.choy.dbguard.entity.dto;

import cn.com.choy.dbguard.enums.ErrCodeEnums;
import lombok.Data;

/**
 * 返回实体类
 *
 * @param <T> 数据类型
 * @author choyrunyu
 * @since 2025/03/17
 */
@Data
public class Result<T> {


    /**
     * 错误码
     */
    private int code;

    /**
     * 错误信息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    public Result() {

    }

    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(ErrCodeEnums errCodeEnums) {
        this.code = errCodeEnums.getCode();
        this.message = errCodeEnums.getMessage();
    }

    public Result(ErrCodeEnums errCodeEnums, T data) {
        this.code = errCodeEnums.getCode();
        this.message = errCodeEnums.getMessage();
        this.data = data;
    }


    public static <T> Result<T> success(T data) {
        return new Result<>(ErrCodeEnums.SUCCESS, data);
    }

    public static <T> Result<T> success(String msg, T data) {
        return new Result<>(ErrCodeEnums.SUCCESS.getCode(), msg, null);
    }

    public static <T> Result<T> fail() {
        return new Result<>(ErrCodeEnums.FAIL, null);
    }

    public static <T> Result<T> fail(String msg) {
        return new Result<>(ErrCodeEnums.FAIL.getCode(), msg, null);
    }

    public static <T> Result<T> fail(String msg, T data) {
        return new Result<>(ErrCodeEnums.FAIL.getCode(), msg, data);
    }

    public static <T> Result<T> fail(ErrCodeEnums errCodeEnums) {
        return new Result<>(errCodeEnums);
    }

    public static <T> Result<T> fail(int code, String msg) {
        return new Result<>(code, msg, null);
    }

    public static <T> Result<T> fail(ErrCodeEnums errCodeEnums, T data) {
        return new Result<>(errCodeEnums, data);
    }


}
