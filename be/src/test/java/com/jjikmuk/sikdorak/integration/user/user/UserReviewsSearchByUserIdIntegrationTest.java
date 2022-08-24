package com.jjikmuk.sikdorak.integration.user.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.user.auth.controller.Authority;
import com.jjikmuk.sikdorak.user.auth.controller.LoginUser;
import com.jjikmuk.sikdorak.user.user.controller.response.UserReviewResponse;
import com.jjikmuk.sikdorak.user.user.exception.NotFoundUserException;
import com.jjikmuk.sikdorak.user.user.service.UserService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("UserReviewsSearch 통합 테스트")
class UserReviewsSearchByUserIdIntegrationTest extends InitIntegrationTest {

	@Autowired
	private UserService userService;

	@Test
	@DisplayName("게스트가 특정 유저의 리뷰를 조회한다면 public 리뷰들만 반환한다.")
	void guest_search_user_reviews_success() {
		LoginUser loginUser = new LoginUser(Authority.ANONYMOUS);

		List<UserReviewResponse> reviews = userService.searchUserReviewsByUserIdAndRelationType(testData.hoi.getId(), loginUser);

		assertThat(reviews).hasSize(1);
	}

	@Test
	@DisplayName("친구가 아닌 유저가 특정 유저의 리뷰를 조회한다면 public 리뷰들만 반환한다.")
	void disconnection_user_search_user_reviews_success() {
		LoginUser loginUser = new LoginUser(testData.jay.getId(), Authority.USER);

		List<UserReviewResponse> reviews = userService.searchUserReviewsByUserIdAndRelationType(testData.hoi.getId(), loginUser);

		assertThat(reviews).hasSize(1);
	}

	@Test
	@DisplayName("친구인 유저가 특정 유저의 리뷰를 조회한다면 public|protected 리뷰들을 반환한다.")
	void connection_user_search_user_reviews_success() {
		LoginUser loginUser = new LoginUser(testData.forky.getId(), Authority.USER);

		List<UserReviewResponse> reviews = userService.searchUserReviewsByUserIdAndRelationType(testData.hoi.getId(), loginUser);

		assertThat(reviews).hasSize(2);
	}

	@Test
	@DisplayName("스스로 자신의 리뷰를 조회한다면 public|protected|private 전체 리뷰를 반환한다.")
	void user_search_user_reviews_success() {
		LoginUser loginUser = new LoginUser(testData.hoi.getId(), Authority.USER);

		List<UserReviewResponse> reviews = userService.searchUserReviewsByUserIdAndRelationType(testData.hoi.getId(), loginUser);

		assertThat(reviews).hasSize(3);
	}

	@Test
	@DisplayName("존재하지 않는 유저의 리뷰를 조회한다면 예외를 발생시킨다.")
	void search_invalid_user_reviews_failed() {
		LoginUser loginUser = new LoginUser(Authority.ANONYMOUS);
		long invalidUserId = Long.MAX_VALUE;

		assertThatThrownBy(() ->
			userService.searchUserReviewsByUserIdAndRelationType(invalidUserId, loginUser))
			.isInstanceOf(NotFoundUserException.class);
	}

	@Test
	@DisplayName("조회할 리뷰가 존재하지 않는다면 빈 리스트를 반환한다.")
	void search_empty_user_reviews_failed() {
		LoginUser loginUser = new LoginUser(Authority.ANONYMOUS);

		List<UserReviewResponse> reviews = userService.searchUserReviewsByUserIdAndRelationType(
			testData.jay.getId(), loginUser);

		assertThat(reviews).isEmpty();
	}

}


