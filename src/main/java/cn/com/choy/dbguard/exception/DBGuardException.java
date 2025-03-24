package cn.com.choy.dbguard.exception;

import cn.com.choy.dbguard.enums.ErrCodeEnums;
import lombok.Getter;

/**
 * 通用异常
 *
 * @author choyrunyu
 * @since 2025/03/17
 */
@Getter
public class DBGuardException extends RuntimeException {

    int code;

    String message;

    public DBGuardException() {
    }

    public DBGuardException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;

    }

    public DBGuardException(ErrCodeEnums errCodeEnums) {
        super(errCodeEnums.getMessage());
        this.code = errCodeEnums.getCode();
        this.message = errCodeEnums.getMessage();
    }

    public DBGuardException(String message) {
        super(message);
        this.code = ErrCodeEnums.SYSTEM_ERROR.getCode();
        this.message = message;
    }

}
