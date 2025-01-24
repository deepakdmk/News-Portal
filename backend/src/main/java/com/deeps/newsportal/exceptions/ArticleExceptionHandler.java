package com.deeps.newsportal.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.deeps.newsportal.dto.ResponseDto;

@ControllerAdvice
public class ArticleExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<ResponseDto> handleException(ArticleException exc) {

		ResponseDto error = new ResponseDto();
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setMessage(exc.getMessage());
		error.setTimeStamp(System.currentTimeMillis());

		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
}
