package com.example.idolverse.global.response;

import com.example.idolverse.global.exception.code.ErrorCode;

import lombok.Getter;

@Getter
public class ApiResponseDto<T> {

	private static final String SUCCESS_STATUS = "success";
	private static final String FAIL_STATUS = "fail";
	private static final String ERROR_STATUS = "error";

	private final String status;
	private final String message;
	private final T data;

	public ApiResponseDto(String status, String message, T data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public static <T> ApiResponseDto<T> success(T data) {
		return new ApiResponseDto<>(SUCCESS_STATUS, null, data);
	}

	public static <T> ApiResponseDto<T> success() {
		return success(null);
	}

	public static <T> ApiResponseDto<T> error(ErrorCode error) {
		return new ApiResponseDto<>(ERROR_STATUS, error.getMessage(), null);
	}
}
