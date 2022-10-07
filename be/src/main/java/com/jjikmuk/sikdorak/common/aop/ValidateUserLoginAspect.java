package com.jjikmuk.sikdorak.common.aop;

import com.jjikmuk.sikdorak.user.auth.api.LoginUser;
import com.jjikmuk.sikdorak.user.auth.exception.NeedLoginException;
import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ValidateUserLoginAspect {

    @Before("@annotation(com.jjikmuk.sikdorak.common.aop.UserOnly) || @within(com.jjikmuk.sikdorak.common.aop.UserOnly)")
    public void validateUserOnly(JoinPoint joinPoint) {
        LoginUser loginUser = findLoginUser(joinPoint.getArgs());
        loginUser.ifAnonymousThrowException();
    }

    @Before("@annotation(com.jjikmuk.sikdorak.common.aop.AdminOnly) || @within(com.jjikmuk.sikdorak.common.aop.AdminOnly)")
    public void validateAdminOnly(JoinPoint joinPoint) {
        LoginUser loginUser = findLoginUser(joinPoint.getArgs());
        loginUser.ifNotAdminThrowException();
    }

    private LoginUser findLoginUser(Object... args) {
        return Arrays.stream(args)
            .filter(LoginUser.class::isInstance)
            .map(LoginUser.class::cast)
            .findFirst()
            .orElseThrow(NeedLoginException::new);
    }
}
