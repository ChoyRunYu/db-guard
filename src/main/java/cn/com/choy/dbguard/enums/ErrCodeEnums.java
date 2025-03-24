package cn.com.choy.dbguard.enums;

import lombok.Getter;

/**
 * 错误码枚举类
 *
 * @author choyrunyu
 * @since 2025/03/17
 */
@Getter
public enum ErrCodeEnums {


    SUCCESS(0, "请求成功"),
    FAIL(-1, "请求失败"),
    SYSTEM_ERROR(500, "系统错误"),
    PARAM_ERROR(400, "参数错误"),
    NOT_FOUND(404, "找不到资源"),

    ;

    private final int code;
    private final String message;

    ErrCodeEnums(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
