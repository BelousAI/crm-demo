package com.belous.crmdemo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class LoggingAspect {

    // setup logger
    private final Logger myLogger = Logger.getLogger(getClass().getName());

    // setup pointcut declarations

    @Pointcut("execution(* com.belous.crmdemo.controller.*.*(..))")
    private void forControllerPackage() {}

    @Pointcut("execution(* com.belous.crmdemo.service.*.*(..))")
    private void forServicePackage() {}

    @Pointcut("execution(* com.belous.crmdemo.dao.*.*(..))")
    private void forDaoPackage() {}

    @Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
    private void forAppFlow() {}


}
