package by.library.yavlash.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class ExceptionInRepositoryAspect {
    private static final String message = "Exception {} ";

    @AfterThrowing(value = "execution(* by.library.yavlash.repository.*.*(..))")
    public Object logStatementAfterExceptionInRepository(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Exception exception) {
            log.error(message, exception.getMessage());
            throw new Exception(exception.getMessage());
        }
    }
}