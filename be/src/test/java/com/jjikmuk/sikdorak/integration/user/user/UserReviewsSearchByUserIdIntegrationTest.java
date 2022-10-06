package com.jjikmuk.sikdorak.integration.user.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jjikmuk.sikdorak.common.controller.request.CursorPageRequest;
import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.review.query.ReviewDao;
import com.jjikmuk.sikdorak.review.query.response.ReviewListResponse;
import com.jjikmuk.sikdorak.user.auth.controller.LoginUser;
import com.jjikmuk.sikdorak.user.user.domain.Authority;
import com.jjikmuk.sikdorak.user.user.exception.NotFoundUserException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("통합 : User 리뷰 목록 조회")
class UserReviewsSearchByUserIdIntegrationTest extends InitIntegrationTest {

	@Autowired
	private ReviewDao reviewDao;

	@Test
	@DisplayName("게스트가 특정 유저의 리뷰를 조회한다면 public 리뷰들만 반환한다.")
	void guest_search_user_reviews_success() {
		long cursorPage = 10;
		int size = 5;
		CursorPageRequest cursorPageRequest = new CursorPageRequest(0L, cursorPage, size, true);
		LoginUser loginUser = new LoginUser(Authority.ANONYMOUS);

		ReviewListResponse reviews = reviewDao.searchUserReviewsByUserIdAndRelationType(testData.hoi.getId(), loginUser, cursorPageRequest);

		assertThat(reviews.reviews()).hasSize(1);
	}

	@Test
	@DisplayName("친구가 아닌 유저가 특정 유저의 리뷰를 조회한다면 public 리뷰들만 반환한다.")
	void disconnection_user_search_user_reviews_success() {
		long cursorPage = 10;
		int size = 5;
		CursorPageRequest cursorPageRequest = new CursorPageRequest(0L, cursorPage, size, true);
		LoginUser loginUser = new LoginUser(testData.jay.getId(), Authority.USER);

		ReviewListResponse reviews = reviewDao.searchUserReviewsByUserIdAndRelationType(testData.hoi.getId(), loginUser, cursorPageRequest);

		assertThat(reviews.reviews()).hasSize(1);
	}

	@Test
	@DisplayName("친구인 유저가 특정 유저의 리뷰를 조회한다면 public|protected 리뷰들을 반환한다.")
	void connection_user_search_user_reviews_success() {
		long cursorPage = 0;
		int size = 5;
		CursorPageRequest cursorPageRequest = new CursorPageRequest(0L, cursorPage, size, true);
		LoginUser loginUser = new LoginUser(testData.forky.getId(), Authority.USER);

		ReviewListResponse reviews = reviewDao.searchUserReviewsByUserIdAndRelationType(testData.hoi.getId(), loginUser, cursorPageRequest);

		assertThat(reviews.reviews()).hasSize(2);
	}

	@Test
	@DisplayName("스스로 자신의 리뷰를 조회한다면 public|protected|private 전체 리뷰를 반환한다.")
	void user_search_user_reviews_success() {
		long cursorPage = 10;
		int size = 5;
		CursorPageRequest cursorPageRequest = new CursorPageRequest(0L, cursorPage, size, true);
		LoginUser loginUser = new LoginUser(testData.hoi.getId(), Authority.USER);

		ReviewListResponse reviews = reviewDao.searchUserReviewsByUserIdAndRelationType(testData.hoi.getId(), loginUser, cursorPageRequest);

		assertThat(reviews.reviews()).hasSize(3);
	}

	@Test
	@DisplayName("존재하지 않는 유저의 리뷰를 조회한다면 예외를 발생시킨다.")
	void search_invalid_user_reviews_failed() {
		long cursorPage = 10;
		int size = 5;
		CursorPageRequest cursorPageRequest = new CursorPageRequest(0L, cursorPage, size, true);
		LoginUser loginUser = new LoginUser(Authority.ANONYMOUS);
		long invalidUserId = Long.MAX_VALUE;

		assertThatThrownBy(() ->
			reviewDao.searchUserReviewsByUserIdAndRelationType(invalidUserId, loginUser, cursorPageRequest))
			.isInstanceOf(NotFoundUserException.class);
	}

	@Test
	@DisplayName("조회할 리뷰가 존재하지 않는다면 빈 리스트를 반환한다.")
	void search_empty_user_reviews_failed() {
		long cursorPage = 10;
		int size = 5;
		CursorPageRequest cursorPageRequest = new CursorPageRequest(0L, cursorPage, size, true);
		LoginUser loginUser = new LoginUser(Authority.ANONYMOUS);

		ReviewListResponse reviews = reviewDao.searchUserReviewsByUserIdAndRelationType(
			testData.jay.getId(), loginUser,cursorPageRequest);

		assertThat(reviews.reviews()).isEmpty();
	}

	@Test
	@DisplayName("유저에 대한 리뷰조회 요청시 결과가 페이징 되어서 나온다.")
	void search_user_reviews_by_paging() {
		long cursorPage = 15;
		int size = 5;
		CursorPageRequest cursorPageRequest = new CursorPageRequest(0L, cursorPage, size, true);
		LoginUser loginUser = new LoginUser(Authority.ANONYMOUS);

		ReviewListResponse reviews = reviewDao.searchUserReviewsByUserIdAndRelationType(
			testData.forky.getId(), loginUser,cursorPageRequest);

		assertThat(reviews.reviews()).hasSize(5);
	}

}


