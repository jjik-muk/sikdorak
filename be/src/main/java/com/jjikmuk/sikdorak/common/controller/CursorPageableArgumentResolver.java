package com.jjikmuk.sikdorak.common.controller;

import com.jjikmuk.sikdorak.common.controller.request.CursorPageRequest;
import com.jjikmuk.sikdorak.common.exception.InvalidPageParameterException;
import com.jjikmuk.sikdorak.common.exception.UnsupportedParameterTypeException;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class CursorPageableArgumentResolver implements HandlerMethodArgumentResolver {

	private static final String BEFORE_PARAMETER = "before";
	private static final String AFTER_PARAMETER = "after";
	private static final String SIZE_PARAMETER = "size";

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(CursorPageable.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

		// CursorPaging 어노테이션 붙어있는지 확인
		validateParameterType(parameter);

		String beforeParam = webRequest.getParameter(BEFORE_PARAMETER);
		String afterParam = webRequest.getParameter(AFTER_PARAMETER);

		// before & after 둘다 없거나, 둘다 있으면 예외
		validateBeforeAfterParameter(beforeParam, afterParam);

		// before, after, size 파싱
		return new CursorPageRequest(
			getCursorOrNull(beforeParam),
			getCursorOrNull(afterParam),
			getSizeOrDefault(webRequest, parameter.getParameterAnnotation(CursorPageable.class)),
			(afterParam != null)
		);
	}

	private void validateParameterType(MethodParameter parameter) {
		if (parameter.getParameterType().equals(CursorPageable.class)) {
			 throw new UnsupportedParameterTypeException();
		}
	}

	private void validateBeforeAfterParameter(String before, String after) {
		if ((before == null && after == null) || (before != null && after != null)) {
			 throw new InvalidPageParameterException();
		}
	}

	private int getSizeOrDefault(NativeWebRequest webRequest, CursorPageable cursorPageable) {
		String sizeParam = webRequest.getParameter(SIZE_PARAMETER);

		if (sizeParam == null) {
			return cursorPageable.sizeDefaultValue();
		}

		try {
			return Integer.parseInt(sizeParam);
		} catch (NumberFormatException e) {
			throw new InvalidPageParameterException(e);
		}
	}

	private Long getCursorOrNull(String cursorParam) {
		if (cursorParam == null) return null;

		try {
			return Long.parseLong(cursorParam);
		} catch (NumberFormatException e) {
			 throw new InvalidPageParameterException(e);
		}
	}
}
