package com.sahyunjin.prographyspringquest.response;

import com.sahyunjin.prographyspringquest.response.exeption.BadRequestErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception ex) {
        return ApiResponseData.toResponseEntity(ResponseCode.SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestErrorException.class)
    public ResponseEntity handleBadRequestErrorException(Exception ex) {
        return ApiResponseData.toResponseEntity(ResponseCode.BAD_REQUEST_ERROR);
    }
}
