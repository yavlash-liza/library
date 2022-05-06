package by.library.yavlash.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Aspect
@Configuration
public class LoggingExceptionInRepositoryAspect {

    @Around(value = "execution(* by.library.yavlash.repository.*.*(..))")
    public Object logStatementAfter(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Exception exception) {
            log.error("Exception {} ", exception.getMessage());
            throw new Exception(exception.getMessage());
        }
    }
}