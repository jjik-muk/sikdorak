package com.jjikmuk.sikdorak.tool;

import com.jjikmuk.sikdorak.comment.domain.Comment;
import com.jjikmuk.sikdorak.comment.repository.CommentRepository;
import com.jjikmuk.sikdorak.review.domain.Review;
import com.jjikmuk.sikdorak.review.domain.ReviewVisibility;
import com.jjikmuk.sikdorak.review.repository.ReviewRepository;
import com.jjikmuk.sikdorak.store.domain.Address;
import com.jjikmuk.sikdorak.store.domain.Store;
import com.jjikmuk.sikdorak.store.repository.StoreRepository;
import com.jjikmuk.sikdorak.user.auth.controller.LoginUser;
import com.jjikmuk.sikdorak.user.auth.domain.JwtProvider;
import com.jjikmuk.sikdorak.user.user.domain.Authority;
import com.jjikmuk.sikdorak.user.user.domain.User;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataGenerator {

	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private StoreRepository storeRepository;

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

	public Review review(User user, ReviewVisibility visibility) {
		return review(user, visibility, 1L, 3.0f);
	}

	public Review review(User user, ReviewVisibility visibility, long storeId, float reviewScore) {
		return reviewRepository.save(new Review(user.getId(),
			storeId,
			String.format("%s's %s review content", user.getNickname(), visibility.name()),
			reviewScore,
			visibility.name(),
			LocalDate.of(2022, 1, 1),
			List.of("tag1", "tag2"),
			List.of("https://s3.ap-northeast-2.amazonaws.com/sikdorak/test.jpg")));
	}

	public Store store() {
		return storeRepository.save(new Store(
			"가게 이름",
			"02-000-0000",
			Address.requiredFieldBuilder("지번", "도로명").build(),
			123.111,
			37.111
		));
	}

	public LoginUser createLoginUserWithUserId(long userId) {
		return new LoginUser(userId, Authority.USER);
	}
}
