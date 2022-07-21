package com.jjikmuk.sikdorak.common;

import lombok.Getter;

@Getter
public class BaseResponse<T> {

	private final int code;
	private final String message;
	private final T data;

	public BaseResponse(int code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}
}
