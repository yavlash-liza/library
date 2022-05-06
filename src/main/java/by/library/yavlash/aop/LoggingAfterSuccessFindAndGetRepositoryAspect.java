package by.library.yavlash.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Aspect
@Configuration
public class LoggingAfterSuccessFindAndGetRepositoryAspect {

    @AfterReturning(value = "execution(* by.library.yavlash.repository..*find*(..))"
            + "|| execution(* by.library.yavlash.repository..*get*(..))")
    public void logStatementAfter(JoinPoint joinPoint) {
        log.info("Target method successfully completed: {}", joinPoint.getStaticPart().getSignature());
    }
}