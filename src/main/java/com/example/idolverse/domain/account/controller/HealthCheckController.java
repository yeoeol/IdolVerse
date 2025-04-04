package com.example.idolverse.domain.account.controller;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@RestController
public class HealthCheckController {

	@Value("${server.env}")
	private String env;
	@Value("${server.port}")
	private String serverPort;
	@Value("${server.serverAddress}")
	private String serverAddress;
	@Value("${serverName}")
	private String severName;

	@GetMapping("/hc")
	public ResponseEntity<?> healthCheck() {
		Map<String, String> responseData = new TreeMap<>();
		responseData.put("severName", severName);
		responseData.put("serverAddress", serverAddress);
		responseData.put("serverPort", serverPort);
		responseData.put("env", env);

		return ResponseEntity.ok(responseData);
	}

	@GetMapping("/env")
	public ResponseEntity<?> getEnv() {
		return ResponseEntity.ok(env);
	}
}
