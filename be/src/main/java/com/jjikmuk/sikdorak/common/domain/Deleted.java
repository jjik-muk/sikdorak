package com.jjikmuk.sikdorak.common.domain;

import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Deleted {

	public static final Deleted TRUE = new Deleted(true);
	public static final Deleted FALSE = new Deleted(false);

	private boolean deleted;

	public Deleted(boolean deleted) {
		this.deleted = deleted;
	}

	public void delete() {
		deleted = TRUE.deleted;
	}
}
