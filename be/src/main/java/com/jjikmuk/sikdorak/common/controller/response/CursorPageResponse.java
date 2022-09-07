package com.jjikmuk.sikdorak.common.controller.response;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CursorPageResponse {

	@NotNull
	@Min(0)
	@Max(20)
	int size;

	@NotNull
	@Min(0)
	@Max(Long.MAX_VALUE)
	long prev;

	@NotNull
	@Min(0)
	@Max(Long.MAX_VALUE)
	long next;

	@NotNull
	boolean isLast;

	public CursorPageResponse(int size, long prev, long next) {
		this.size = size;
		this.prev = prev;
		this.next = next;
		this.isLast = false;
	}

	public CursorPageResponse(int size, long prev, long next, boolean isLast) {
		this.size = size;
		this.prev = prev;
		this.next = next;
		this.isLast = isLast;
	}

	public int size() {
		return size;
	}

	public long prev() {
		return prev;
	}

	public long next() {
		return next;
	}

	public boolean isLast() {
		return isLast;
	}
}
