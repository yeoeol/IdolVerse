package com.example.idolverse.global.exception.code;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

	// Common Errors
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON000", "서버 에러"),

	// Account Errors
	DUPLICATE_EMAIL(HttpStatus.CONFLICT, "ACCOUNT001", "이미 사용 중인 이메일입니다."),
	LOGIN_FAILED(HttpStatus.BAD_REQUEST, "ACCOUNT002", "이메일 또는 비밀번호가 틀렸습니다."),

	// Member Errors
	MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER001", "Member를 찾을 수 없습니다."),

	// Community Errors
	COMMUNITY_NOT_FOUND(HttpStatus.BAD_REQUEST, "COMMUNITY001", "Community를 찾을 수 없습니다."),

	// CommunityMember Errors
	COMMUNITY_MEMBER_NOT_REGISTER(HttpStatus.BAD_REQUEST, "COMMUNITYMEMBER001", "해당 Community에 가입되어 있지 않은 Member입니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
