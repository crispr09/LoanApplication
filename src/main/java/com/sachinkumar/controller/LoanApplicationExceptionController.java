package com.sachinkumar.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.sachinkumar.exception.ChecklistItemNotCompleteException;
import com.sachinkumar.exception.LoanApplicationException;
import com.sachinkumar.exception.UserNotAuthorizedException;

@ControllerAdvice
public class LoanApplicationExceptionController {
	
   @ExceptionHandler(value = LoanApplicationException.class)
   public ResponseEntity<Object> exception(LoanApplicationException exception) {
      return new ResponseEntity<>(exception.getMsg() , HttpStatus.BAD_REQUEST);
   }
   
   @ExceptionHandler(value = UserNotAuthorizedException.class)
   public ResponseEntity<Object> exception(UserNotAuthorizedException exception) {
      return new ResponseEntity<>(exception.getMsg(), HttpStatus.UNAUTHORIZED);
   }
   
   @ExceptionHandler(value = ChecklistItemNotCompleteException.class)
   public ResponseEntity<Object> exception(ChecklistItemNotCompleteException exception) {
      return new ResponseEntity<>(exception.getMsg(), HttpStatus.BAD_REQUEST);
   }
   
   
}