package com.jjikmuk.sikdorak.common.config;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

@RestController
public class CorsTestController {

	public static final String REQUEST_URL = "/test/cors/success";

	@GetMapping(REQUEST_URL)
	@ResponseStatus(HttpStatus.OK)
	public void getSuccess() { }

	@PostMapping(REQUEST_URL)
	@ResponseStatus(HttpStatus.OK)
	public void postSuccess() { }

	@PutMapping(REQUEST_URL)
	@ResponseStatus(HttpStatus.OK)
	public void putSuccess() { }

	@DeleteMapping(REQUEST_URL)
	@ResponseStatus(HttpStatus.OK)
	public void deleteSuccess() { }

	@PatchMapping(REQUEST_URL)
	@ResponseStatus(HttpStatus.OK)
	public void patchSuccess() { }
}
