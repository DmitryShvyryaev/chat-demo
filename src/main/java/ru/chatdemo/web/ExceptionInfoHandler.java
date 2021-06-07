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
import ru.chatdemo.util.ValidationUtil;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
@AllArgsConstructor
public class ExceptionInfoHandler {

    private final MessageSourceAccessor sourceAccessor;

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorJson> bindValidationError(HttpServletRequest request, BindException e) {
        log.error("Exception {} at request {}", ValidationUtil.getRootCause(e).toString(), request.getRequestURL());
        String[] details = e.getBindingResult().getFieldErrors().stream()
                .map(sourceAccessor::getMessage)
                .toArray(String[]::new);
        return ResponseEntity.status(ErrorType.VALIDATION_ERROR.getStatus())
                .body(new ErrorJson(request.getRequestURL(), sourceAccessor.getMessage(ErrorType.VALIDATION_ERROR.getErrorCode()), details));
    }
}
