package ru.chatdemo.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import ru.chatdemo.exception.ErrorType;
import ru.chatdemo.util.ValidationUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest request, Exception e) {
        log.error("Exception at request " + request.getRequestURL(), e);
        Throwable rootCause = ValidationUtil.getRootCause(e);
        ModelAndView mav = new ModelAndView("exception");
        mav.addObject("status", "STATUS");
//                Map.of("exception", rootCause,
//                        "message", ValidationUtil.getMessage(rootCause),
//                        "errorCode", ErrorType.APP_ERROR.getErrorCode(),
//                        "errorStatus", ErrorType.APP_ERROR.getStatus()));
        mav.setStatus(ErrorType.APP_ERROR.getStatus());
        return mav;
    }


}
