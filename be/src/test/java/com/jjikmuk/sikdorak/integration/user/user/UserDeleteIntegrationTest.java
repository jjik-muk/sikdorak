package com.jjikmuk.sikdorak.integration.user.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.review.command.domain.Review;
import com.jjikmuk.sikdorak.review.command.domain.ReviewRepository;
import com.jjikmuk.sikdorak.user.auth.api.LoginUser;
import com.jjikmuk.sikdorak.user.user.command.domain.User;
import com.jjikmuk.sikdorak.user.user.exception.NotFoundUserException;
import com.jjikmuk.sikdorak.user.user.command.domain.UserRepository;
import com.jjikmuk.sikdorak.user.user.command.app.UserService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/*
    [] 댓글 기능이 추가 되면 댓글 삭제 검증 추가
 */

@DisplayName("통합 : User 삭제 기능")
class UserDeleteIntegrationTest extends InitIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("올바른 유저 정보가 들어올 시 유저의 정보, 유저가 작성한 리뷰들, 댓글들을 soft delete 한다.")
    void delete_user_success() {
        LoginUser loginUser = LoginUser.user(testData.forky.getId());

        userService.deleteUser(loginUser);

        Optional<User> deletedUser = userRepository.findById(loginUser.getId());
        List<Review> deletedReviews = reviewRepository.findByUserId(loginUser.getId());

        assertThat(deletedUser).isEmpty();

        for (Review deletedReview : deletedReviews) {
            assertThat(deletedReview.isDeleted()).isTrue();
        }
    }

    @Test
    @DisplayName("올바른 유저 정보가 들어올 시 탈퇴한 유저의 팔로워/팔로잉 목록에 있는 유저들의 팔로워/팔로잉 목록에서 탈퇴한 유저의 아이디는 검색되지 않는다.")
    void delete_user_in_followers_followings() {
        LoginUser loginUser = LoginUser.user(testData.forky.getId());

        userService.deleteUser(loginUser);

        List<User> hoiFollowings = userRepository.findFollowingsByUserId(testData.hoi.getId());
        List<User> rumkaFollowers = userRepository.findFollowersByUserId(testData.rumka.getId());

        assertThat(hoiFollowings).isNotEmpty().noneMatch(u -> u.getId().equals(testData.forky.getId()));
        assertThat(rumkaFollowers).isNotEmpty().noneMatch(u -> u.getId().equals(testData.forky.getId()));
    }

    @Test
    @DisplayName("존재하지 않는 유저의 정보가 들어오면 예외를 반환한다.")
    void delete_not_found_user() {
        LoginUser loginUser = LoginUser.user(9887656L);

        assertThatThrownBy(() -> userService.deleteUser(loginUser))
            .isInstanceOf(NotFoundUserException.class);
    }

}
