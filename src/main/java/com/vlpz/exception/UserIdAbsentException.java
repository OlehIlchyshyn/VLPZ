package com.vlpz.exception;

import com.vlpz.model.enums.ErrorCode;
import com.vlpz.model.enums.ErrorType;

public class UserIdAbsentException extends com.vlpz.exception.AbstractException {

  public UserIdAbsentException(String message) {
    super(message);
  }

  @Override
  public ErrorCode getErrorCode() {
    return ErrorCode.VALIDATION_ERROR_CODE;
  }

  @Override
  public ErrorType getErrorType() {
    return ErrorType.PROCESSING_ERROR_TYPE;
  }

}
