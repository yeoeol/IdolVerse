package com.example.idolverse.domain.account.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;

@Hidden
@RestController
public class TestController {
	@GetMapping("/test")
	public String jwtTest() {
		return "jwt test success";
	}
}
