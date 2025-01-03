import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Define a Pointcut for methods in BookRegistrationServlet
    @Pointcut("execution(* BookRegistrationServlet.*(..))")
    public void bookRegistrationMethods() {
    }

    // Before method execution
    @Before("bookRegistrationMethods()")
    public void logBeforeMethodExecution(JoinPoint joinPoint) {
        logger.info("Executing method: " + joinPoint.getSignature().getName());
        logger.info("With arguments: ");
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            logger.info("  - " + arg);
        }
    }

    // Log after the method successfully returns
    @AfterReturning(pointcut = "bookRegistrationMethods()", returning = "result")
    public void logAfterMethodExecution(JoinPoint joinPoint, Object result) {
        logger.info("Method executed successfully: " + joinPoint.getSignature().getName());
        if (result != null) {
            logger.info("Return value: " + result.toString());
        }
    }
}