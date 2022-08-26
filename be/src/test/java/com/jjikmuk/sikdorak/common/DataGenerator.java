package com.jjikmuk.sikdorak.common;

import com.jjikmuk.sikdorak.comment.domain.Comment;
import com.jjikmuk.sikdorak.comment.repository.CommentRepository;
import com.jjikmuk.sikdorak.review.domain.Review;
import com.jjikmuk.sikdorak.user.auth.domain.JwtProvider;
import com.jjikmuk.sikdorak.user.user.domain.User;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataGenerator {

	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private CommentRepository commentRepository;

	public String validAuthorizationHeader(User user) {
		String userPayload = String.valueOf(user.getId());

		Date now = new Date();
		Date expiredTime = new Date(now.getTime() + 1800000);

		return "Bearer " + jwtProvider.createAccessToken(userPayload, expiredTime);
	}

	public String validAuthorizationHeader(User user, Date expiredTime) {
		String userPayload = String.valueOf(user.getId());

		return "Bearer " + jwtProvider.createAccessToken(userPayload, expiredTime);
	}

	public String refreshToken(User user, Date expiredTime) {
		String userPayload = String.valueOf(user.getId());

		return jwtProvider.createRefreshToken(userPayload, expiredTime);
	}

	public Comment comment(Review review, User user, String content) {
		return commentRepository.save(new Comment(review.getId(), user.getId(), content));
	}
}
