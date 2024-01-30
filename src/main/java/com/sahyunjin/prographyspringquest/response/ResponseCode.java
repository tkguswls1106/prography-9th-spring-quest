package com.sahyunjin.prographyspringquest.response;

import com.sahyunjin.prographyspringquest.response.responseitem.MessageItem;
import com.sahyunjin.prographyspringquest.response.responseitem.StatusItem;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCode {

    SUCCESS(StatusItem.OK, MessageItem.SUCCESS),
    BAD_REQUEST_ERROR(StatusItem.BAD_REQUEST, MessageItem.BAD_REQUEST_ERROR),
    SERVER_ERROR(StatusItem.ERROR, MessageItem.SERVER_ERROR),
    ;

    private Integer httpStatus;
    private String message;
}
