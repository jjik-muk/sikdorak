package com.jjikmuk.sikdorak.integration.user.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jjikmuk.sikdorak.common.exception.SikdorakRuntimeException;
import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.user.auth.controller.Authority;
import com.jjikmuk.sikdorak.user.auth.controller.LoginUser;
import com.jjikmuk.sikdorak.user.user.controller.request.UserModifyRequest;
import com.jjikmuk.sikdorak.user.user.domain.User;
import com.jjikmuk.sikdorak.user.user.domain.UserRespository;
import com.jjikmuk.sikdorak.user.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class UserModifyIntegrationTest extends InitIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRespository userRespository;

    @Test
    @DisplayName("올바른 입력값이 들어오면 유저의 정보를 수정한다.")
    void user_modify_success() {

        LoginUser loginUser = new LoginUser(testData.user1.getId(), Authority.USER);
        UserModifyRequest userModifyRequest = new UserModifyRequest(
            "포키",
            "forkyy@gmail.com",
            "https://s3-asjkdah8d2.com"
        );

        Long id = userService.modifyUser(loginUser, userModifyRequest);
        User user = userRespository.findById(id).orElseThrow();

        assertThat(user.getNickname()).isEqualTo(userModifyRequest.getNickname());
        assertThat(user.getEmail()).isEqualTo(userModifyRequest.getEmail());
        assertThat(user.getProfileImage()).isEqualTo(userModifyRequest.getProfileImage());
    }

    @Test
    @DisplayName("올바르지 않은 닉네임이 들어오면 예외가 발생한다.")
    void modify_profile_with_wrong_data() {

        LoginUser loginUser = new LoginUser(testData.user1.getId(), Authority.USER);
        UserModifyRequest userModifyRequest = new UserModifyRequest(
            "",
            "forkyy@gmail.com",
            "https://s3-asjkdah8d2.com"
        );

        assertThatThrownBy(() -> userService.modifyUser(loginUser, userModifyRequest))
            .isInstanceOf(SikdorakRuntimeException.class);
    }
}
