package cn.com.choy.dbguard.handler;

import cn.com.choy.dbguard.entity.dto.Result;
import cn.com.choy.dbguard.enums.ErrCodeEnums;
import cn.com.choy.dbguard.exception.DBGuardException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理
 *
 * @author choyrunyu
 * @since 2025/03/17
 */
@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionGlobal {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionGlobal.class);

    @ExceptionHandler(value = DBGuardException.class)
    public Result<String> DBGuardExceptionHandler(DBGuardException e) {
        log.error(e.getMessage(), e);
        return Result.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHandler(Exception e) {
        log.error(e.getMessage(), e);
        return Result.fail(ErrCodeEnums.SYSTEM_ERROR.getCode(), e.getMessage());
    }

}
