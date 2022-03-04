package com.flycloud.fly.common.security.enums;

import com.flycloud.fly.common.core.exception.assertion.CommonExceptionAssert;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author fly
 * @date 2022/3/4
 **/

@Getter
@AllArgsConstructor
public enum AuthResponseEnum implements CommonExceptionAssert {

    AUTH_FAILED_ERROR(-101,"授权失败，禁止访问"),

    UNAUTHORIZED_ERROR(-102,"未认证异常"),
    ;

    /**
     * 异常错误码
     */
    private int code;
    /**
     * 异常错误信息
     */
    private String msg;
}
