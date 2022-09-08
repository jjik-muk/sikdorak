package com.jjikmuk.sikdorak.integration.user.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.user.auth.controller.Authority;
import com.jjikmuk.sikdorak.user.auth.controller.LoginUser;
import com.jjikmuk.sikdorak.user.user.controller.request.UserModifyRequest;
import com.jjikmuk.sikdorak.user.user.domain.User;
import com.jjikmuk.sikdorak.user.user.domain.UserRepository;
import com.jjikmuk.sikdorak.user.user.exception.NotFoundUserException;
import com.jjikmuk.sikdorak.user.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("통합 : User 수정 기능")
class UserModifyIntegrationTest extends InitIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("올바른 입력값이 들어오면 유저의 정보를 수정한다.")
    void user_modify_success() {

        LoginUser loginUser = new LoginUser(testData.kukim.getId(), Authority.USER);
        UserModifyRequest userModifyRequest = new UserModifyRequest(
            "포키",
            "forkyy@gmail.com",
            "https://s3-asjkdah8d2.com"
        );

        Long id = userService.modifyUser(loginUser, userModifyRequest);
        User user = userRepository.findById(id).orElseThrow();

        assertThat(user.getNickname()).isEqualTo(userModifyRequest.getNickname());
        assertThat(user.getEmail()).isEqualTo(userModifyRequest.getEmail());
        assertThat(user.getProfileImage()).isEqualTo(userModifyRequest.getProfileImage());
    }

    @Test
    @DisplayName("존재하지 않은 유저의 요청이 들어오면 예외가 발생한다.")
    void user_modify_with_wrong_id() {

        LoginUser loginUser = new LoginUser(987654321L, Authority.USER);
        UserModifyRequest userModifyRequest = new UserModifyRequest(
            "",
            "forkyy@gmail.com",
            "https://s3-asjkdah8d2.com"
        );

        assertThatThrownBy(() -> userService.modifyUser(loginUser, userModifyRequest))
            .isInstanceOf(NotFoundUserException.class);
    }
}
