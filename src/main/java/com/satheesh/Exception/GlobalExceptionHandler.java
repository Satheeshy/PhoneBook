package com.satheesh.Exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.satheesh.Domain.ErrorMessage;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(EmailAlreadyExist.class)
	public final ResponseEntity<ErrorMessage> hanlder(Exception ex){
		
		ErrorMessage errorResponse = new ErrorMessage(ex.getMessage(), "Email is already Taken");
		
		return new ResponseEntity<ErrorMessage>(errorResponse,new HttpHeaders(),HttpStatus.CONFLICT);
	}
	
	
	
	public final ResponseEntity<ErrorMessage> EmailNotFoundHanlder(Exception ex){
		
		ErrorMessage errorResponse = new ErrorMessage(ex.getMessage(), "Email doesn't exist");
		
		return new ResponseEntity<ErrorMessage>(errorResponse,new HttpHeaders(),HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorMessage> globalHandler(Exception ex){
		
		ErrorMessage errorResponse = new ErrorMessage(ex.getMessage(), "Something wrong");
		
		return new ResponseEntity<ErrorMessage>(errorResponse,new HttpHeaders(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(PasswordIncorrect.class)
	public final ResponseEntity<ErrorMessage> createGroupError(Exception ex){
		ErrorMessage msg = new ErrorMessage(ex.getMessage(), "Password doesn't match");
		return new ResponseEntity<>(msg,HttpStatus.EXPECTATION_FAILED);
	}
	
}
