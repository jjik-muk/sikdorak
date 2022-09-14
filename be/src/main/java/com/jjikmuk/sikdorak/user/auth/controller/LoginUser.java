package com.jjikmuk.sikdorak.user.auth.controller;

import com.jjikmuk.sikdorak.user.auth.exception.NeedLoginException;
import com.jjikmuk.sikdorak.user.user.domain.Authority;
import lombok.Getter;

@Getter
public class LoginUser {

	private final Long id;

	private final Authority authority;

	public LoginUser(Long id, Authority authority) {
		this.id = id;
		this.authority = authority;
	}

	public LoginUser(Authority authority) {
		this(null, authority);
	}

	public boolean isAnonymous() {
		return authority.equals(Authority.ANONYMOUS);
	}

	public void ifAnonymousThrowException() {
		if (authority.equals(Authority.ANONYMOUS)) {
			throw new NeedLoginException();
		}
	}
}
