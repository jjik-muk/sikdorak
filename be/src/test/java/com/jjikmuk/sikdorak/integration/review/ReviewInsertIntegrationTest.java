package com.jjikmuk.sikdorak.integration.review;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.review.controller.request.ReviewCreateRequest;
import com.jjikmuk.sikdorak.review.domain.Review;
import com.jjikmuk.sikdorak.review.service.ReviewService;
import com.jjikmuk.sikdorak.store.exception.NotFoundStoreException;
import com.jjikmuk.sikdorak.user.auth.controller.LoginUser;
import com.jjikmuk.sikdorak.user.user.domain.Authority;
import com.jjikmuk.sikdorak.user.user.exception.NotFoundUserException;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * TODO
 * - [x] 유저 도메인 객체 생성 후 통합 테스트 추가
 * - [ ] Image 추가 통합 테스트
 */
@DisplayName("통합 : Review 생성")
class ReviewInsertIntegrationTest extends InitIntegrationTest {

	@Autowired
	ReviewService reviewService;

	@Test
	@DisplayName("만약 회원이 정상적인 리뷰 생성 요청이 주어진다면 리뷰를 등록할 수 있다.")
	void create_review_valid_user_store() {
		LoginUser loginUser = new LoginUser(testData.kukim.getId(), Authority.USER);
		ReviewCreateRequest reviewCreateRequest = new ReviewCreateRequest(
			"Test review contents",
			testData.store.getId(),
			3.f,
			"public",
			LocalDate.of(2022, 1, 1),
			List.of("tag1", "tag2"),
			List.of("https://s3.ap-northeast-2.amazonaws.com/sikdorak/test.jpg"));

		Review saveReview = reviewService.createReview(loginUser, reviewCreateRequest);

		assertThat(saveReview.getId()).isNotNull();
		assertThat(saveReview.getUserId()).isEqualTo(testData.kukim.getId());
	}

	@Test
	@DisplayName("만약 회원이 존재하지 않은 상점 id의 리뷰 생성 요청이 주어진다면 예외를 발생시킨다.")
	void create_review_valid_user_invalid_store() {
		LoginUser loginUser = new LoginUser(testData.kukim.getId(), Authority.USER);
		Long invalidStoreId = Long.MAX_VALUE;
		ReviewCreateRequest reviewCreateRequest = new ReviewCreateRequest(
			"Test review contents",
			invalidStoreId,
			3.f,
			"public",
			LocalDate.of(2022, 1, 1),
			List.of("tag1", "tag2"),
			List.of("https://s3.ap-northeast-2.amazonaws.com/sikdorak/test.jpg"));

		assertThatThrownBy(() -> reviewService.createReview(loginUser, reviewCreateRequest))
			.isInstanceOf(NotFoundStoreException.class);
	}

	@Test
	@DisplayName("만약 비회원이 정상적인 리뷰 생성 요청을 한다면 예외를 발생시킨다.")
	void create_review_invalid_user_valid_store() {
		LoginUser loginUser = new LoginUser(Long.MAX_VALUE, Authority.USER);
		ReviewCreateRequest reviewCreateRequest = new ReviewCreateRequest(
			"Test review contents",
			testData.store.getId(),
			3.f,
			"public",
			LocalDate.of(2022, 1, 1),
			List.of("tag1", "tag2"),
			List.of("https://s3.ap-northeast-2.amazonaws.com/sikdorak/test.jpg"));

		assertThatThrownBy(() -> reviewService.createReview(loginUser, reviewCreateRequest))
			.isInstanceOf(NotFoundUserException.class);
	}
}

