package br.com.estevam.ecommerce.produtos.config.exceptionhandler;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GeneralExceptionHandler {

    @ResponseBody
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public GeneralExceptionMessageDto entityNotFoundException(EntityNotFoundException exception) {
        return new GeneralExceptionMessageDto(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public GeneralExceptionMessageDto httpMessageNotReadableException(HttpMessageNotReadableException exception) {
        return new GeneralExceptionMessageDto(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public GeneralExceptionMessageDto httpMessageNotReadableException(DataIntegrityViolationException exception) {
        return new GeneralExceptionMessageDto(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

}
