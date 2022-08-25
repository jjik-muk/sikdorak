package com.jjikmuk.sikdorak.common.controller;

import static com.jjikmuk.sikdorak.common.ResponseCodeAndMessages.SYSTEMINFO_SEARCH_API_DOCS_INFO;

import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import com.jjikmuk.sikdorak.common.controller.response.CodeAndMessageResponse;
import com.jjikmuk.sikdorak.common.exception.ExceptionCodeAndMessages;
import com.jjikmuk.sikdorak.common.response.CommonResponseEntity;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommonInfoController {

	@GetMapping("/api/docs/info")
	public CommonResponseEntity<List<CodeAndMessageResponse>> searchAPIDocsInfo() {
		List<CodeAndMessageResponse> codeAndMessageResponses = Arrays.stream(
				ResponseCodeAndMessages.values())
			.map(e -> new CodeAndMessageResponse(e.getCode(), e.getMessage()))
			.collect(Collectors.toList());

		List<CodeAndMessageResponse> exceptionCodeAndMessageResponses = Arrays.stream(
				ExceptionCodeAndMessages.values())
			.map(e -> new CodeAndMessageResponse(e.getCode(), e.getMessage()))
			.toList();

		codeAndMessageResponses.addAll(exceptionCodeAndMessageResponses);

		return new CommonResponseEntity<>(
			SYSTEMINFO_SEARCH_API_DOCS_INFO,
			codeAndMessageResponses,
			HttpStatus.OK);
	}
}
