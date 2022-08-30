package com.jjikmuk.sikdorak.common.controller.response;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public record CursorPageResponse(

	@NotNull
	@Min(10)
	@Max(20)
	int size,

	@NotNull
	@Min(0)
	@Max(Long.MAX_VALUE)
	long prev,

	@NotNull
	@Min(0)
	@Max(Long.MAX_VALUE)
	long next

) {

}
