package com.portal.admin.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.admin.portal.exceptions.MovieNotFounfException;
import com.admin.portal.exceptions.UserNotFoundException;
@ControllerAdvice
public class MovieNotFoundAdvice {
	
	@ResponseBody
	@ExceptionHandler(MovieNotFounfException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String userNotFoundHandler(MovieNotFounfException ex) {
		return ex.getMessage();
	}
}
