package com.jjikmuk.sikdorak.common.controller.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class CursorPageRequest {

	private static final long DEFAULT_CURSOR = 1;
	private static final int DEFAULT_SIZE = 10;
	private static final boolean DEFAULT_IS_AFTER = true;


	@NotNull
	@Min(0)
	@Max(Long.MAX_VALUE)
	private long before;

	@NotNull
	@Min(0)
	@Max(Long.MAX_VALUE)
	private long after;

	@NotNull
	@Min(10)
	@Max(20)
	private int size;

	private boolean isAfter;

	public CursorPageRequest(long before, long after, int size, boolean isAfter) {
		this.before = before;
		this.after = after;
		this.size = size;
		this.isAfter = isAfter;
	}
}
