package by.library.yavlash.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class RepositoryExceptionAspect {
    private static final String message = "Exception in {}.{}() with cause = {} : {}";

    @AfterThrowing(pointcut = "execution(* by.library.yavlash.repository.*.*(..))", throwing = "e")
    public void logStatementAfterRepositoryException(JoinPoint joinPoint, Exception e) throws Exception {
        String clazz = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        log.error(message, clazz, methodName, e.getCause() != null ? e.getCause() : "NULL", e);
    }
}