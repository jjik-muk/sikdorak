package com.jjikmuk.sikdorak.user.service;

import com.jjikmuk.sikdorak.user.domain.User;
import com.jjikmuk.sikdorak.user.domain.UserRespository;
import com.jjikmuk.sikdorak.user.exception.DuplicateUserException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRespository userRespository;


    @Nested
    @DisplayName("유저 생성 메서드는")
    class Describe_createUser {

        @Nested
        @DisplayName("동일한 유저가 존재하지 않는다면")
        class Context_valid_user {

            @Test
            @DisplayName("유저를 저장소에 저장한다.")
            void save() {
                User user = new User(232323L, "Forky-Ham", "https://profile-img.com");

                Long id = userService.createUser(user);
                User findUser = userRespository.findById(id).get();

                assertThat(user.getUniqueId()).isEqualTo(findUser.getUniqueId());
                assertThat(user.getNickname()).isEqualTo(findUser.getNickname());
                assertThat(user.getProfileImage()).isEqualTo(findUser.getProfileImage());
            }
        }

        @Nested
        @DisplayName("중복된 유저라면")
        class Context_duplicated_user {

            @Test
            @DisplayName("예외를 발생시킨다.")
            void duplicatedException() {
                User user1 = new User(232323L, "Forky-Ham", "https://profile-img.com");
                User user2 = new User(232323L, "Forky-Ham-2", "https://profile-img-2.com");

                userService.createUser(user1);

                assertThatThrownBy(() -> userService.createUser(user2))
                        .isInstanceOf(DuplicateUserException.class);
            }
        }
    }
}
