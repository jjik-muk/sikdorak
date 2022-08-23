package com.jjikmuk.sikdorak.common.aop;

import com.jjikmuk.sikdorak.user.auth.controller.LoginUser;
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
    public void validateLogin(JoinPoint joinPoint) {
        LoginUser loginUser = findLoginUser(joinPoint.getArgs());
        loginUser.ifAnonymousThrowException();
    }

    private LoginUser findLoginUser(Object[] args) {
        return Arrays.stream(args)
            .filter(LoginUser.class::isInstance)
            .map(LoginUser.class::cast)
            .findFirst()
            .orElseThrow(NeedLoginException::new);
    }
}
