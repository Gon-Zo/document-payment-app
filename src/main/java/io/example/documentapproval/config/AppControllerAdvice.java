package io.example.documentapproval.config;

import io.example.documentapproval.enums.ErrorCode;
import io.example.documentapproval.service.dto.ErrorCodeDTO;
import io.example.documentapproval.service.excpetion.AppException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.thymeleaf.exceptions.TemplateInputException;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class AppControllerAdvice {

    @ExceptionHandler(TemplateInputException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exceptionHandler(TemplateInputException e, Model model) {

        e.printStackTrace();

        return "error";
    }

    @ExceptionHandler(AppException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exceptionHandler(AppException e, Model model) {

        ErrorCode errorCode = e.getErrorCode();

        ErrorCodeDTO errorData = ErrorCodeDTO.builder()
                .code(errorCode.getCode())
                .message(errorCode.getValue())
                .build();

        e.printStackTrace();

        model.addAttribute("error", errorData);

        return "error_page";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFoundHandler(NoHandlerFoundException e) {
        return "error404_page";
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String accessDeniedHandler(AccessDeniedException e) {
        return "error403_page";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exceptionHandler(Exception e, Model model) {

        ErrorCode errorCode = ErrorCode.SERVER_ERROR;

        ErrorCodeDTO errorData = ErrorCodeDTO.builder()
                .code(errorCode.getCode())
                .message(errorCode.getValue())
                .build();

        e.printStackTrace();

        model.addAttribute("error", errorData);

        return "error_page";
    }

}
