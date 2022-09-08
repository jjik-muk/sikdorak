package com.jjikmuk.sikdorak.integration.user.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.user.user.domain.User;
import com.jjikmuk.sikdorak.user.user.domain.UserRepository;
import com.jjikmuk.sikdorak.user.user.exception.DuplicateUserException;
import com.jjikmuk.sikdorak.user.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("통합 : 사용자 생성 기능")
public class UserCreateIntegrationTest extends InitIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @Test
    @DisplayName("동일한 유저가 존재하지 않는다면 유저를 저장소에 저장한다.")
    void save() {
        User user = new User(232323L, "Forky-Ham", "https://profile-img.com", "forky@sikdorak.com");

        Long id = userService.createUser(user);
        User findUser = userRepository.findById(id).get();

        assertThat(user.getUniqueId()).isEqualTo(findUser.getUniqueId());
        assertThat(user.getNickname()).isEqualTo(findUser.getNickname());
        assertThat(user.getProfileImage()).isEqualTo(findUser.getProfileImage());
    }

    @Test
    @DisplayName("유저의 고유Id가 중복된다면 예외를 발생시킨다.")
    void duplicatedUserException() {
        User user2 = new User(testData.jay.getUniqueId(), "test-user", "https://profile.com", "sikdorak@gmail.com");

        assertThatThrownBy(() -> userService.createUser(user2))
                .isInstanceOf(DuplicateUserException.class);
    }
}
