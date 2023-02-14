package org.leon.todoapp.exceptions;

import org.leon.todoapp.dto.MessageDto;
import org.leon.todoapp.utils.Operations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<MessageDto> throwNotFoundException(ResourceNotFoundException e){
        return new ResponseEntity<>(new MessageDto(HttpStatus.NOT_FOUND, e.getMessage()) , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AttributeException.class)
    public ResponseEntity<MessageDto> throwAttributeException(AttributeException e){
        return new ResponseEntity<>(new MessageDto(HttpStatus.BAD_REQUEST, e.getMessage()) , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageDto> generalException(Exception e){
        return new ResponseEntity<>(new MessageDto(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()) , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MessageDto> validationException(MethodArgumentNotValidException e){
        List<String> errors = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach((err)->{
            errors.add(err.getDefaultMessage());
        });
        return new ResponseEntity<>(new MessageDto(HttpStatus.INTERNAL_SERVER_ERROR, Operations.trimBrackets(errors.toString())) ,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
