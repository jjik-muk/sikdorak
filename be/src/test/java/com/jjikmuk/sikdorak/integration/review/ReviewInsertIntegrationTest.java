package com.jjikmuk.sikdorak.integration.review;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.review.controller.request.ReviewInsertRequest;
import com.jjikmuk.sikdorak.review.service.ReviewService;
import com.jjikmuk.sikdorak.store.exception.StoreNotFoundException;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * TODO
 * - [ ] 유저 도메인 객체 생성 후 통합 테스트 추가
 */
@DisplayName("ReviewInsert 통합테스트")
public class ReviewInsertIntegrationTest extends InitIntegrationTest {

	@Autowired
	ReviewService reviewService;

	@Test
	@DisplayName("만약 정상적인 리뷰 요청이 주어진다면 리뷰를 등록할 수 있다.")
	void create_review_Success() {
		ReviewInsertRequest reviewInsertRequest = new ReviewInsertRequest(
			"Test review contents",
			data.testStore.getId(),
			3.f,
			"public",
			LocalDate.of(2022, 1, 1),
			List.of("tag1", "tag2"),
			List.of("https://s3.ap-northeast-2.amazonaws.com/sikdorak/test.jpg"));

		Long saveId = reviewService.insertReview(reviewInsertRequest);

		assertThat(saveId).isNotNull();
	}

	@Test
	@DisplayName("만약 존재하지 않은 상점 id의 리뷰 요청이 주어진다면 예외를 발생시킨다.")
	void create_review_failed() {
		Long invalidStoreId = Long.MAX_VALUE;
		ReviewInsertRequest reviewInsertRequest = new ReviewInsertRequest(
			"Test review contents",
			invalidStoreId,
			3.f,
			"public",
			LocalDate.of(2022, 1, 1),
			List.of("tag1", "tag2"),
			List.of("https://s3.ap-northeast-2.amazonaws.com/sikdorak/test.jpg"));

		assertThatThrownBy(() -> reviewService.insertReview(reviewInsertRequest))
			.isInstanceOf(StoreNotFoundException.class);
	}

}

