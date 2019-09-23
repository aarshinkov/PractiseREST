package com.safb.practise.exceptions;

import com.safb.practise.errors.*;
import java.util.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.*;

@ControllerAdvice
public class AppExceptionHandler
{
  @ExceptionHandler(value =
  {
    Exception.class
  })
  public ResponseEntity<Object> handleOtherExceptions(Exception ex, WebRequest request)
  {
    ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());

    return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
