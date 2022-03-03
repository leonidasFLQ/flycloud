package com.flycloud.fly.common.feign.sentinel.handle;

import com.flycloud.fly.common.core.constant.enums.ArgumentResponseEnum;
import com.flycloud.fly.common.core.constant.enums.CommonResponseEnum;
import com.flycloud.fly.common.core.exception.handler.GlobalExceptionHandler;
import com.flycloud.fly.common.core.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 模块异常处理器
 * @author fy
 * @date 2022/3/3
 **/

@Component
@ControllerAdvice
@Slf4j
public class FeignExceptionHandler extends GlobalExceptionHandler {

    /**
     * 处理业务校验过程中碰到的非法参数异常 该异常基本由{@link org.springframework.util.Assert}抛出
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public R<CommonResponseEnum> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("非法参数,exception = {}", e.getMessage(), e);
        return R.exception(ArgumentResponseEnum.ILLEGAL_ARGS_ERROR.getCode(), e.getLocalizedMessage());
    }

    /**
     * AccessDeniedException
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public R handleAccessDeniedException(AccessDeniedException e) {
        String msg = SpringSecurityMessageSource.getAccessor().getMessage("AbstractAccessDecisionManager.accessDenied",
                e.getMessage());
        log.error("拒绝授权异常信息 ex={}", msg, e);
        return R.exception(ArgumentResponseEnum.ILLEGAL_ARGS_ERROR.getCode(), e.getLocalizedMessage());
    }

}
