package com.example.idolverse.global.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.idolverse.global.exception.GeneralException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(GeneralException.class)
	protected ResponseEntity<String> handleGeneralException(GeneralException ex) {
		log.warn("[WARNING] {} : {}", ex.getClass(), ex.getMessage());
		return ResponseEntity
			.status(ex.getErrorCode().getHttpStatus())
			.body(ex.getErrorCode().getMessage());
	}
}
