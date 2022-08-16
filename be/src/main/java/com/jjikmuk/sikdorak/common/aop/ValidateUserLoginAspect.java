package com.jjikmuk.sikdorak.common.aop;

import com.jjikmuk.sikdorak.user.auth.exception.NeedLoginException;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class ValidateUserLoginAspect {

    @Before("@annotation(com.jjikmuk.sikdorak.common.aop.UserOnly)")
    public void validateLogin() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String authorization = request.getHeader("Authorization");

        if (authorization == null || authorization.isEmpty()) {
            throw new NeedLoginException();
        }
    }
}
