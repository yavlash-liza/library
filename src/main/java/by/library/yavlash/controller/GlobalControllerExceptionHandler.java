package by.library.yavlash.controller;

import by.library.yavlash.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiCallError handleExceptionServerError(
            HttpServletRequest request,
            Exception e
    ) {
        log.error("handleExceptionServerError {}\n", request.getRequestURI(), e);
        return new ApiCallError(e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiCallError handleAccessDeniedException(
            HttpServletRequest request,
            AccessDeniedException e
    ) {
        log.error("handleAccessDeniedException {}\n", request.getRequestURI(), e);
        return new ApiCallError("Access denied!");
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiCallError handleUnauthorizedException(
            HttpServletRequest request,
            BadCredentialsException e
    ) {
        log.error("handleUnauthorizedException {}\n", request.getRequestURI(), e);
        return new ApiCallError("Incorrect username or password!");
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiCallDetailedError<?> handleServiceExceptionServerError(
            HttpServletRequest request,
            ServiceException e
    ) {
        log.error("handleServiceExceptionServerError {}\n", request.getRequestURI(), e);
        List<Map<String, String>> details = new ArrayList<>();
        Map<String, String> detail = new HashMap<>();
        detail.put("info", e.getMessage());
        details.add(detail);
        return new ApiCallDetailedError<>("Parameters is not valid", details);
    }

    @Data
    @AllArgsConstructor
    public static class ApiCallDetailedError<T> {
        private String message;
        private List<T> details;
    }

    @Data
    @AllArgsConstructor
    public static class ApiCallError {
        private String message;
    }
}