package com.jjikmuk.sikdorak.common;

import lombok.Getter;

@Getter
public class BaseResponse<T> {

	private final String code;
	private final String message;
	private final T data;

	public BaseResponse(String code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}
}
