package com.jjikmuk.sikdorak.common.aop;

import com.jjikmuk.sikdorak.user.auth.api.LoginUser;
import com.jjikmuk.sikdorak.user.auth.exception.NeedLoginException;
import com.jjikmuk.sikdorak.user.user.command.domain.Authority;
import com.jjikmuk.sikdorak.user.user.command.domain.User;
import com.jjikmuk.sikdorak.user.user.command.domain.UserRepository;
import com.jjikmuk.sikdorak.user.user.exception.NotFoundUserException;
import com.jjikmuk.sikdorak.user.user.exception.UnauthorizedUserException;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class ValidateUserLoginAspect {

    private final UserRepository userRepository;

    @Before("@annotation(com.jjikmuk.sikdorak.common.aop.UserOnly) || @within(com.jjikmuk.sikdorak.common.aop.UserOnly)")
    public void validateUserOnly(JoinPoint joinPoint) {
        LoginUser loginUser = findLoginUser(joinPoint.getArgs());
        loginUser.ifAnonymousThrowException();
    }

    @Before("@annotation(com.jjikmuk.sikdorak.common.aop.AdminOnly) || @within(com.jjikmuk.sikdorak.common.aop.AdminOnly)")
    public void validateAdminOnly(JoinPoint joinPoint) {
        LoginUser loginUser = findLoginUser(joinPoint.getArgs());
        validateAdmin(loginUser);
    }

    private LoginUser findLoginUser(Object... args) {
        return Arrays.stream(args)
            .filter(LoginUser.class::isInstance)
            .map(LoginUser.class::cast)
            .findFirst()
            .orElseThrow(NeedLoginException::new);
    }

    private void validateAdmin(LoginUser loginUser) {
        loginUser.ifAnonymousThrowException();

        User user = userRepository.findById(loginUser.getId())
            .orElseThrow(NotFoundUserException::new);

        if (!Authority.ADMIN.equals(user.getAuthority())) {
            throw new UnauthorizedUserException();
        }
    }
}
