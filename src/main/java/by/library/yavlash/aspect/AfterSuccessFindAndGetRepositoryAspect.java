package by.library.yavlash.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class AfterSuccessFindAndGetRepositoryAspect {
    private static final String message = "Target method successfully completed: {}";

    @AfterReturning(value = "execution(* by.library.yavlash.repository.*find*(..))"
            + "|| execution(* by.library.yavlash.repository.*get*(..))")
    public void logStatementAfterSuccessRepositoryFindOrGetMethod(JoinPoint joinPoint) {
        log.info(message, joinPoint.getStaticPart().getSignature());
    }
}