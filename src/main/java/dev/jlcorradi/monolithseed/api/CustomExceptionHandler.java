package dev.jlcorradi.monolithseed.api;

import dev.jlcorradi.monolithseed.common.CustomException;
import dev.jlcorradi.monolithseed.common.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<Void> handleCustomException(CustomException e) {
    if (e instanceof EntityNotFoundException entityNotFoundException) {
      HttpUtils.addMessageHeader(MessageType.ERROR, entityNotFoundException.getMessage());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    HttpUtils.addMessageHeader(MessageType.ERROR, e.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
  }

}
