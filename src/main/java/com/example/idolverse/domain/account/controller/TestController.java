package com.example.idolverse.domain.account.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	@GetMapping("/test")
	public String jwtTest() {
		return "jwt test success";
	}
}
