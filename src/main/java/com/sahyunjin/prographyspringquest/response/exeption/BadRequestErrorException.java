package com.sahyunjin.prographyspringquest.response.exeption;

import com.sahyunjin.prographyspringquest.response.ResponseCode;
import lombok.Getter;

@Getter
public class BadRequestErrorException extends RuntimeException {

    private ResponseCode responseCode;

    public BadRequestErrorException() {
        this.responseCode = ResponseCode.BAD_REQUEST_ERROR;
    }
}
