package com.vlpz.exception;

import com.vlpz.model.enums.ErrorCode;
import com.vlpz.model.enums.ErrorType;

public class UserNotFoundException extends com.vlpz.exception.AbstractException {

  public UserNotFoundException(String message) {
    super(message);
  }

  @Override
  public ErrorCode getErrorCode() {
    return ErrorCode.APPLICATION_ERROR_CODE;
  }

  @Override
  public ErrorType getErrorType() {
    return ErrorType.PROCESSING_ERROR_TYPE;
  }

}
