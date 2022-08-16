package com.jjikmuk.sikdorak.integration.review;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.review.controller.request.ReviewInsertRequest;
import com.jjikmuk.sikdorak.review.domain.Review;
import com.jjikmuk.sikdorak.review.service.ReviewService;
import com.jjikmuk.sikdorak.store.exception.StoreNotFoundException;
import com.jjikmuk.sikdorak.user.auth.controller.Authority;
import com.jjikmuk.sikdorak.user.auth.controller.LoginUser;
import com.jjikmuk.sikdorak.user.user.exception.UserNotFoundException;
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
@DisplayName("ReviewInsert 통합테스트")
public class ReviewInsertIntegrationTest extends InitIntegrationTest {

	@Autowired
	ReviewService reviewService;

	@Test
	@DisplayName("만약 회원이 정상적인 리뷰 생성 요청이 주어진다면 리뷰를 등록할 수 있다.")
	void create_review_valid_user_store() {
		LoginUser loginUser = new LoginUser(testData.user.getId(), Authority.USER);
		ReviewInsertRequest reviewInsertRequest = new ReviewInsertRequest(
			"Test review contents",
			testData.store.getId(),
			3.f,
			"public",
			LocalDate.of(2022, 1, 1),
			List.of("tag1", "tag2"),
			List.of("https://s3.ap-northeast-2.amazonaws.com/sikdorak/test.jpg"));

		Review saveReview = reviewService.insertReview(loginUser, reviewInsertRequest);

		assertThat(saveReview.getId()).isNotNull();
		assertThat(saveReview.getUserId()).isEqualTo(testData.user.getId());
	}

	@Test
	@DisplayName("만약 회원이 존재하지 않은 상점 id의 리뷰 생성 요청이 주어진다면 예외를 발생시킨다.")
	void create_review_valid_user_invalid_store() {
		LoginUser loginUser = new LoginUser(testData.user.getId(), Authority.USER);
		Long invalidStoreId = Long.MAX_VALUE;
		ReviewInsertRequest reviewInsertRequest = new ReviewInsertRequest(
			"Test review contents",
			invalidStoreId,
			3.f,
			"public",
			LocalDate.of(2022, 1, 1),
			List.of("tag1", "tag2"),
			List.of("https://s3.ap-northeast-2.amazonaws.com/sikdorak/test.jpg"));

		assertThatThrownBy(() -> reviewService.insertReview(loginUser, reviewInsertRequest))
			.isInstanceOf(StoreNotFoundException.class);
	}

	@Test
	@DisplayName("만약 비회원이 정상적인 리뷰 생성 요청을 한다면 예외를 발생시킨다.")
	void create_review_invalid_user_valid_store() {
		LoginUser loginUser = new LoginUser(Long.MAX_VALUE, Authority.USER);
		ReviewInsertRequest reviewInsertRequest = new ReviewInsertRequest(
			"Test review contents",
			testData.store.getId(),
			3.f,
			"public",
			LocalDate.of(2022, 1, 1),
			List.of("tag1", "tag2"),
			List.of("https://s3.ap-northeast-2.amazonaws.com/sikdorak/test.jpg"));

		assertThatThrownBy(() -> reviewService.insertReview(loginUser, reviewInsertRequest))
			.isInstanceOf(UserNotFoundException.class);
	}
}

