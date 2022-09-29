package com.jjikmuk.sikdorak.review.service.response.reviewdetail;

import com.jjikmuk.sikdorak.user.user.domain.User;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public record ReviewDetailUserResponse(
	@NotNull
	long userId,

	@NotNull
	@Size(min = 1, max = 30)
	String userNickname,

	@URL
	String userProfileImage
) {
	public ReviewDetailUserResponse(User user) {
		this(user.getId(),
			user.getNickname(),
			user.getProfileImage());
	}
}
