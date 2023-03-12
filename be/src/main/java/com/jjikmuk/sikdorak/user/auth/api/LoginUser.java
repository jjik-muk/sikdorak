package com.jjikmuk.sikdorak.user.auth.api;

import com.jjikmuk.sikdorak.user.auth.exception.NeedLoginException;
import com.jjikmuk.sikdorak.user.user.command.domain.Authority;
import com.jjikmuk.sikdorak.user.user.exception.UnauthorizedUserException;
import lombok.Getter;

@Getter
public class LoginUser {

	private final Long id;

	private final Authority authority;

	private LoginUser(Authority authority) {
		this(null, authority);
	}

	private LoginUser(Long id, Authority authority) {
		this.id = id;
		this.authority = authority;
	}

	public static LoginUser anonymous() {
		return new LoginUser(Authority.ANONYMOUS);
	}

	public static LoginUser user(Long id) {
		return new LoginUser(id, Authority.USER);
	}

	public static LoginUser admin(Long id) {
		return new LoginUser(id, Authority.ADMIN);
	}

	public boolean isAnonymous() {
		return authority.equals(Authority.ANONYMOUS);
	}

	public void ifAnonymousThrowException() {
		if (authority.equals(Authority.ANONYMOUS)) {
			throw new NeedLoginException();
		}
	}

	public void ifNotAdminThrowException() {
		if (!authority.equals(Authority.ADMIN)) {
			throw new UnauthorizedUserException();
		}
	}
}
