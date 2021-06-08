package ru.chatdemo.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.chatdemo.exception.ErrorJson;
import ru.chatdemo.exception.ErrorType;

import javax.servlet.http.HttpServletRequest;

import static ru.chatdemo.util.ValidationUtil.getMessage;
import static ru.chatdemo.util.ValidationUtil.getRootCause;

@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
@AllArgsConstructor
public class ExceptionInfoHandler {

    private final MessageSourceAccessor sourceAccessor;

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorJson> bindValidationError(HttpServletRequest request, BindException e) {
        String[] details = e.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + sourceAccessor.getMessage(fe))
                .toArray(String[]::new);
        return logAndGetErrorJson(request, e, ErrorType.VALIDATION_ERROR, details);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorJson> defaultException(HttpServletRequest request, Exception e) {
        return logAndGetErrorJson(request, e, ErrorType.APP_ERROR);
    }

    private ResponseEntity<ErrorJson> logAndGetErrorJson(HttpServletRequest request, Throwable e, ErrorType type, String... details) {
        Throwable rootCause = getRootCause(e);
        log.error("Exception {} at request {}", rootCause.toString(), request.getRequestURL());
        return ResponseEntity.status(type.getStatus())
                .body(new ErrorJson(request.getRequestURL(),
                        sourceAccessor.getMessage(type.getErrorCode()),
                        details.length == 0 ? new String[]{getMessage(rootCause)} : details));
    }
}
