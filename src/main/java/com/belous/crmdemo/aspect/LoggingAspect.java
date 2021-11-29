package com.belous.crmdemo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class LoggingAspect {

    private final Logger myLogger = Logger.getLogger(getClass().getName());
}
