package com.jjikmuk.sikdorak.auth.controller;

import com.jjikmuk.sikdorak.auth.domain.AuthenticatedUser;
import com.jjikmuk.sikdorak.user.domain.UserRespository;
import com.jjikmuk.sikdorak.user.exception.UserNotFoundException;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class OAuthUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final UserRespository userRespository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(AuthenticatedUser.class) != null &&
            parameter.getParameterType().equals(Long.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        Long userId = (Long) request.getAttribute("userId");

        return userRespository.findById(userId)
            .orElseThrow(UserNotFoundException::new);
    }
}
