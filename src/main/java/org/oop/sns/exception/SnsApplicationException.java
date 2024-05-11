package org.oop.sns.exception;

import lombok.AllArgsConstructor;

// TODO : implement
@AllArgsConstructor
public class SnsApplicationException extends RuntimeException {

    private ErrorCode errorCode;
    private String message;

    @Override
    public String getMessage() {
        if (message == null) {
            return errorCode.toString();
        }

        return String.format("%s. %s", errorCode.getMessage(), message);
    }
}
