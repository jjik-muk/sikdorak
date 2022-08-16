package com.jjikmuk.sikdorak.common.aop;

import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Slf4j
public class LogAspect {

    @Before("bean(*Controller)")
    public void logRequestStart() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        log.info("------------------------------ Start Request : {} ------------------------------", request.getRequestURI());
    }

    @After("bean(*Controller)")
    public void logRequestEnd() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        log.info("------------------------------ End Request : {} ------------------------------", request.getRequestURI());
    }

}
