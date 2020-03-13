package com.openpayd.exchange.component;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;

@Aspect
@Component
public class LoggingHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("within(@org.springframework.stereotype.Controller *)*")
    public void controller() {
    }

    @Before(value = "controller() && args(.., request)")
    public void logBefore(JoinPoint joinPoint, HttpServletRequest request) {
        logger.debug("ClassName : " + joinPoint.getSignature().getDeclaringTypeName());
        logger.debug("Method : " + joinPoint.getSignature().getName());
        logger.debug("Arguments : " + Arrays.toString(joinPoint.getArgs()));

        logger.debug("Request Url : " + request.getRequestURI());
        logger.debug("Request Method Type : " + request.getMethod());
        logger.debug("Request Headers : [");
        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String headerName = headers.nextElement();
            String headerValue = request.getHeader(headerName);
            logger.debug("Name : " + headerName + " Value : " + headerValue);
        }
        logger.debug("]");

        logger.debug("Request Parameters : [");
        Enumeration<String> parameters = request.getParameterNames();
        while (parameters.hasMoreElements()) {
            String parameterName = parameters.nextElement();
            String[] parametersValue = request.getParameterValues(parameterName);
            logger.debug("Name : " + parameterName + " Value : " + Arrays.toString(parametersValue));
        }
        logger.debug("]");
    }

    @AfterReturning(pointcut = "controller()", returning = "response")
    public void logAfter(JoinPoint joinPoint, Object response) {
        logger.debug("ClassName : " + joinPoint.getSignature().getDeclaringTypeName());
        logger.debug("Method : " + joinPoint.getSignature().getName());
        logger.debug("Arguments : " + Arrays.toString(joinPoint.getArgs()));

        logger.debug("Response : " + response.toString());
    }

    @AfterThrowing(pointcut = "controller()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        logger.error("An exception has been thrown in " + joinPoint.getSignature().getName() + " ()");
        logger.error("Cause : " + exception.getCause());
    }
}
