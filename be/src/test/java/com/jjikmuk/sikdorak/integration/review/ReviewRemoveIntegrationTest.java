package com.jjikmuk.sikdorak.integration.review;

import static org.assertj.core.api.Assertions.assertThat;

import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.review.domain.Review;
import com.jjikmuk.sikdorak.review.service.ReviewService;
import com.jjikmuk.sikdorak.user.auth.controller.Authority;
import com.jjikmuk.sikdorak.user.auth.controller.LoginUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("ReviewRemove 통합 테스트")
class ReviewRemoveIntegrationTest extends InitIntegrationTest {

	@Autowired
	ReviewService reviewService;

	@Test
	@DisplayName("만약 유저가 본인 리뷰에 대한 삭제 요청이 주어진다면 리뷰가 Soft 삭제된다.")
	void remove_review_valid_() {
		LoginUser loginUser = new LoginUser(testData.user1.getId(), Authority.USER);

		Review removeReview = reviewService.removeReview(loginUser, testData.review.getId());

		assertThat(removeReview.isDeleted()).isTrue();
	}


}
