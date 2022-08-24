package com.jjikmuk.sikdorak.review.controller.response.reviewdetail;

import com.jjikmuk.sikdorak.user.user.domain.User;

public record ReviewDetailUserResponse(
	long userId,
	String userNickname,
	String userProfileImage
) {
	public ReviewDetailUserResponse(User user) {
		this(user.getId(),
			user.getNickname(),
			user.getProfileImage());
	}
}
