package by.library.yavlash.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class AfterSuccessFindAndGetRepositoryAspect {
    private static final String message = "Method {}({}) in class {} successfully completed: {}";

    @AfterReturning(
            pointcut = "execution(public * by.library.yavlash.repository.*.find*(..)) || " +
                    "execution(public * by.library.yavlash.repository.*.get*(..))",
            returning = "object")
    public void logStatementAfterSuccessRepositoryFindOrGetMethod(JoinPoint joinPoint, Object object) {
        String clazz = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        String arguments = fromArrayToString(joinPoint.getArgs());
        log.info(message,  methodName, arguments, clazz, object);
    }

    private String fromArrayToString(Object[] args) {
        String string = Arrays.toString(args);
        return string.substring(1, string.length() - 1);
    }
}