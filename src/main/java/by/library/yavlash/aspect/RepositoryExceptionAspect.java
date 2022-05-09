package by.library.yavlash.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class RepositoryExceptionAspect {
    private static final String message = "Exception {} in method {}({}) in class {} with cause = {} ";

    @AfterThrowing(pointcut = "execution(* by.library.yavlash.repository.*.*(..))", throwing = "e")
    public void logStatementAfterRepositoryException(JoinPoint joinPoint, Exception e) {
        String clazz = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        String arguments = fromArrayToString(joinPoint.getArgs());
        log.error(message, e, methodName, arguments, clazz, e.getCause() != null ? e.getCause() : null, e);
    }

    private String fromArrayToString(Object[] args) {
        String string = Arrays.toString(args);
        return string.substring(1, string.length() - 1);
    }
}