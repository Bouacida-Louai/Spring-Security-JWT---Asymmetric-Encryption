package org.springsec.springsecurityjwt.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter

public enum ErrorCode {
    USER_NOT_FOUND("USER_NOT_FOUND","User not found with id %s",NOT_FOUND),
    CHANGE_PASSWORD_MISMATCH("PASSWORD_MISMATCH","Current Password and the new Password are not the same",HttpStatus.BAD_REQUEST),
    INVALID_CURRENT_PASSWORD ("INVALID_CURRENT_PASSWORD","current Password is invalid",HttpStatus.BAD_REQUEST),
    ACCOUNT_ALREADY_DEACTIVATED("ACCOUNT_ALREADY_DEACTIVATED","Account is already deactivated",HttpStatus.BAD_REQUEST),
    ACCOUNT_ALREADY_ACTIVATED ("ACCOUNT_ALREADY_ACTIVATED","Account is already activated",HttpStatus.BAD_REQUEST),
    EMAIL_ALREADY_EXISTS ("EMAIL_ALREADY_EXISTS","Email already exists",HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_ALREADY_EXISTS ("PHONE_NUMBER_ALREADY_EXISTS","Phone number already exists",HttpStatus.BAD_REQUEST),
    PASSWORD_MISMATCH("PASSWORD_MISMATCH","password do not match",HttpStatus.BAD_REQUEST),;
    private final String code;
    private final  String defaultMessage;
    private final HttpStatus status;


    ErrorCode(String code,
              String defaultMessaege,
              HttpStatus status) {
        this.code = code;
        this.defaultMessage = defaultMessaege;
        this.status = status;
    }
}
